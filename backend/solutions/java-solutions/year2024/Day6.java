package year2024;

import year2024.utils.Direction;
import year2024.utils.Position;
import year2024.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class Day6 {
    private record Move(Position position, Direction direction) {}

    public static void main(String[] args) {
        //Part one
        char[][] map = Utils.readLines("06.txt").stream().map(String::toCharArray).toArray(char[][]::new);

        int height = map.length;
        int width = map[0].length;
        Position position = getPosition(height, width, map);
        Direction direction = switch (map[position.y()][position.x()]) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> Direction.RIGHT;
        };

        Set<Position> visited = move(height, width, map, position, direction);
        System.out.println(visited.size());


        //Part two
        position = getPosition(height, width, map);
        direction = switch (map[position.y()][position.x()]) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> Direction.RIGHT;
        };

        int possibleSolutions = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (map[i][j] == '.') {
                    map[i][j] = '#';

                    if (causeLoop(height, width, map, position, direction)) {
                        possibleSolutions++;
                    }

                    map[i][j] = '.';
                }
            }
        }

        System.out.println(possibleSolutions);
    }

    private static Position getPosition(int height, int width, char[][] map) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char object = map[i][j];

                if (object == '^' || object == 'v' || object == '<' || object == '>') {
                    return new Position(j, i);
                }
            }
        }

        return new Position(-1, -1);
    }

    private static Set<Position> move(int height, int width, char[][] map, Position position, Direction direction) {
        Set<Position> visited = new HashSet<>();
        while (true) {
            visited.add(position);

            Position target = new Position(-1, -1);
            switch (direction) {
                case UP -> target = new Position(position.x(), position.y() - 1);
                case DOWN -> target = new Position(position.x(), position.y() + 1);
                case LEFT -> target = new Position(position.x() - 1, position.y());
                case RIGHT -> target = new Position(position.x() + 1, position.y());
            }

            if (target.x() < 0 || target.y() < 0 || target.x() >= width || target.y() >= height) {
                break;
            } else if (map[target.y()][target.x()] == '#') {
                direction = direction.next();
            } else {
                position = target;
            }
        }

        return visited;
    }

    private static boolean causeLoop(int height, int width, char[][] map, Position position, Direction direction) {
        Set<Move> visited = new HashSet<>();
        while (true) {
            if (visited.contains(new Move(position, direction))) {
                return true;
            }

            visited.add(new Move(position, direction));

            Position target = new Position(-1, -1);
            switch (direction) {
                case UP -> target = new Position(position.x(), position.y() - 1);
                case DOWN -> target = new Position(position.x(), position.y() + 1);
                case LEFT -> target = new Position(position.x() - 1, position.y());
                case RIGHT -> target = new Position(position.x() + 1, position.y());
            }

            if (target.x() < 0 || target.y() < 0 || target.x() >= width || target.y() >= height) {
                return false;
            } else if (map[target.y()][target.x()] == '#') {
                direction = direction.next();
            } else {
                position = target;
            }
        }
    }
}
