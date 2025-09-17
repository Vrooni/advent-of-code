package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day24 {
    record Result(Map<Position, List<Element>> map, int time) {}
    record Position(int x, int y) {}
    record TimeStep(Position direction, int time) { }
    enum Element {
        WALL, RIGHT, LEFT, UP, DOWN;

        public Position getNewDirection(Map<Position, List<Element>> map, Position position, int maxWidth, int maxHeight) {
            Position newPosition = switch (this) {
                case WALL -> position;
                case RIGHT -> new Position(position.x+1, position.y);
                case LEFT -> new Position(position.x-1, position.y);
                case UP -> new Position(position.x, position.y-1);
                case DOWN -> new Position(position.x, position.y+1);
            };

            if (map.get(newPosition).contains(WALL)) {
                return switch (this) {
                    case WALL -> position;
                    case RIGHT -> new Position(1, position.y);
                    case LEFT -> new Position(maxWidth-2, position.y);
                    case UP -> new Position(position.x, maxHeight-2);
                    case DOWN -> new Position(position.x, 1);
                };
            }

            return newPosition;
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/24.txt"));
        Map<Position, List<Element>> map = new HashMap<>();
        final int maxHeight = lines.size();
        final int maxWidth = lines.get(0).length();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                Element element =
                switch (line.charAt(x)) {
                    case '#' -> Element.WALL;
                    case '.' -> null;
                    case '<' -> Element.LEFT;
                    case '>' -> Element.RIGHT;
                    case '^' -> Element.UP;
                    case 'v' -> Element.DOWN;
                    default -> throw new RuntimeException("Fehler");
                };

                if (element == null) {
                    map.put(new Position(x, y), new ArrayList<>());
                } else {
                    map.put(new Position(x, y), new ArrayList<>(List.of(element)));
                }
            }
        }

        Position start = getStartPosition(map);
        Position end = getEndPosition(map, lines.size() - 1);

        Result result1 = moveThrowMap(map, start, end, maxWidth, maxHeight);
        Result result2 = moveThrowMap(result1.map, end, start, maxWidth, maxHeight);
        Result result3 = moveThrowMap(result2.map, start, end, maxWidth, maxHeight);

        System.out.println(result1.time);

        //Part two
        System.out.println(result1.time + result2.time+1 + result3.time+1);
    }

    private static Position getStartPosition(Map<Position, List<Element>> map) {
        int x = 0;
        while (true) {
            Position currentPosition = new Position(x, 0);
            if (map.get(currentPosition).isEmpty()) {
                return currentPosition;
            }

            x++;
        }
    }

    private static Position getEndPosition(Map<Position, List<Element>> map, int y) {
        int x = 0;
        while (true) {
            Position currentPosition = new Position(x, y);
            if (map.get(currentPosition).isEmpty()) {
                return currentPosition;
            }

            x++;
        }
    }

    private static List<Position> possibleDirections(Map<Position, List<Element>> map, Position currentPosition) {
        Position left = new Position(currentPosition.x-1, currentPosition.y);
        Position right = new Position(currentPosition.x+1, currentPosition.y);
        Position up = new Position(currentPosition.x, currentPosition.y-1);
        Position down = new Position(currentPosition.x, currentPosition.y+1);

        List<Position> positions = new ArrayList<>();

        if (map.containsKey(currentPosition) && map.get(currentPosition).isEmpty()) {
            positions.add(currentPosition);
        }
        if (map.containsKey(left) && map.get(left).isEmpty()) {
            positions.add(left);
        }
        if (map.containsKey(right) && map.get(right).isEmpty()) {
            positions.add(right);
        }
        if (map.containsKey(up) && map.get(up).isEmpty()) {
            positions.add(up);
        }
        if (map.containsKey(down) && map.get(down).isEmpty()) {
            positions.add(down);
        }

        return positions;
    }

    private static Map<Position, List<Element>> moveMap(Map<Position, List<Element>> map, int maxWidth, int maxHeight) {
        Map<Position, List<Element>> newMap = new HashMap<>();

        for (Map.Entry<Position, List<Element>> mapEntry: map.entrySet()) {
            newMap.computeIfAbsent(mapEntry.getKey(), k->new ArrayList<>());

            for (Element element : mapEntry.getValue()) {
                Position newPosition = element.getNewDirection(map, mapEntry.getKey(), maxWidth, maxHeight);
                newMap.computeIfAbsent(newPosition, k->new ArrayList<>()).add(element);
            }
        }

        return newMap;
    }

    private static Result moveThrowMap(Map<Position, List<Element>> map, Position start,  Position end, int maxWidth, int maxHeight) {
        map = moveMap(map, maxWidth, maxHeight);
        Queue<TimeStep> directions = new ArrayDeque<>(List.of(new TimeStep(start, 0)));

        Map<Integer, Map<Position, List<Element>>> mapSteps = new HashMap<>();
        mapSteps.put(0, map);

        Set<TimeStep> seen = new HashSet<>();
        while (!directions.isEmpty()) {
            TimeStep currentStep = directions.poll();

            if (currentStep.direction.equals(end)) {
                return new Result(map, currentStep.time);
            }

            map = mapSteps.computeIfAbsent(currentStep.time(),
                    k -> moveMap(mapSteps.get(currentStep.time() - 1), maxWidth, maxHeight)
            );

            for (Position direction : possibleDirections(map, currentStep.direction)) {
                TimeStep newStep = new TimeStep(direction, currentStep.time() + 1);
                if (!seen.add(newStep)) {
                    continue;
                }
                directions.add(newStep);
            }
        }

        throw new RuntimeException("No end");
    }
}
