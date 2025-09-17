package year2024;

import year2024.utils.Position;
import year2024.utils.Utils;

import java.util.*;

public class Day15 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("15.txt");
        char[][] map = readMap(input);

        Position position = getStartPosition(map);

        for (String moves : input.subList(map.length + 1, input.size())) {
            for (char move : moves.toCharArray()) {
                switch (move) {
                    case '^' -> position = moveUp(position, map);
                    case 'v' -> position = moveDown(position, map);
                    case '<' -> position = moveLeft(position, map);
                    case '>' -> position = moveRight(position, map);
                }
            }
        }

        int result = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'O') {
                    result += 100 * y + x;
                }
            }
        }

        System.out.println(result);


        //Part two
        map = readMap(input);
        map = extendMap(map);

        position = getStartPosition(map);

        for (String moves : input.subList(map.length + 1, input.size())) {
            for (char move : moves.toCharArray()) {
                switch (move) {
                    case '^' -> position = moveUp2(position, map);
                    case 'v' -> position = moveDown2(position, map);
                    case '<' -> position = moveLeft2(position, map);
                    case '>' -> position = moveRight2(position, map);
                }
            }
        }

        result = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == '[') {
                    result += 100 * y + x;
                }
            }
        }

        System.out.println(result);
    }

    private static Position moveUp(Position position, char[][] map) {
        return switch (map[position.y()-1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()-1][position.x()] = '@';
                yield new Position(position.x(), position.y() - 1);
            }
            case 'O' -> pushUp(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveDown(Position position, char[][] map) {
        return switch (map[position.y()+1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()+1][position.x()] = '@';
                yield new Position(position.x(), position.y()+1);
            }
            case 'O' -> pushDown(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveLeft(Position position, char[][] map) {
        return switch (map[position.y()][position.x()-1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()-1] = '@';
                yield new Position(position.x()-1, position.y());
            }
            case 'O' -> pushLeft(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position moveRight(Position position, char[][] map) {
        return switch (map[position.y()][position.x()+1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()+1] = '@';
                yield new Position(position.x()+1, position.y());
            }
            case 'O' -> pushRight(position, map);
            default -> new Position(position.x(), position.y());
        };
    }

    private static Position pushUp(Position position, char[][] map) {
        int target = -1;
        for (int y = position.y()-1; y > 0; y--) {
            if (map[y][position.x()] == '.') {
                target = y;
                break;
            } else if (map[y][position.x()] == '#') {
                return new Position(position.x(), position.y());
            }
        }

        if (target >= 0) {
            for (int y = target; y < position.y() - 1; y++) {
                map[y][position.x()] = 'O';
            }

            map[position.y()-1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()-1);
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushDown(Position position, char[][] map) {
        int target = -1;
        for (int y = position.y()+1; y < map.length; y++) {
            if (map[y][position.x()] == '.') {
                target = y;
                break;
            } else if (map[y][position.x()] == '#') {
                return new Position(position.x(), position.y());
            }
        }

        if (target >= 0) {
            for (int y = target; y >= position.y() + 2; y--) {
                map[y][position.x()] = 'O';
            }

            map[position.y()+1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()+1);
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushLeft(Position position, char[][] map) {
        int target = -1;
        for (int x = position.x()-1; x > 0; x--) {
            if (map[position.y()][x] == '.') {
                target = x;
                break;
            } else if (map[position.y()][x] == '#') {
                return new Position(position.x(), position.y());
            }
        }

        if (target >= 0) {
            for (int x = target; x < position.x() - 1; x++) {
                map[position.y()][x] = 'O';
            }

            map[position.y()][position.x()-1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()-1, position.y());
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushRight(Position position, char[][] map) {
        int target = -1;
        for (int x = position.x()+1; x < map[0].length; x++) {
            if (map[position.y()][x] == '.') {
                target = x;
                break;
            } else if (map[position.y()][x] == '#') {
                return new Position(position.x(), position.y());
            }
        }

        if (target >= 0) {
            for (int x = target; x >= position.x() + 2; x--) {
                map[position.y()][x] = 'O';
            }

            map[position.y()][position.x()+1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()+1, position.y());
        }

        return new Position(position.x(), position.y());
    }

    private static char[][] readMap(List<String> input) {
        int width = input.get(0).length();
        int height = input.lastIndexOf(String.join("", Collections.nCopies(width, "#"))) + 1;

        char[][] map = new char[height][width];

        for (int i = 0; i < height; i++) {
            map[i] = input.get(i).toCharArray();
        }

        return map;
    }

    private static Position getStartPosition(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == '@') {
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0);
    }

    private static Position moveUp2(Position position, char[][] map) {
        return switch (map[position.y()-1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()-1][position.x()] = '@';
                yield new Position(position.x(), position.y() - 1);
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushUp2(position, map);
        };
    }

    private static Position moveDown2(Position position, char[][] map) {
        return switch (map[position.y()+1][position.x()]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()+1][position.x()] = '@';
                yield new Position(position.x(), position.y()+1);
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushDown2(position, map);
        };
    }

    private static Position moveLeft2(Position position, char[][] map) {
        return switch (map[position.y()][position.x()-1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()-1] = '@';
                yield new Position(position.x()-1, position.y());
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushLeft2(position, map);
        };
    }

    private static Position moveRight2(Position position, char[][] map) {
        return switch (map[position.y()][position.x()+1]) {
            case '.' -> {
                map[position.y()][position.x()] = '.';
                map[position.y()][position.x()+1] = '@';
                yield new Position(position.x()+1, position.y());
            }
            case '#' -> new Position(position.x(), position.y());
            default -> pushRight2(position, map);
        };
    }

    private static Position pushUp2(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        toCheck.add(new Position(position.x(), position.y()-1));

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x, y-1));
                toCheck.add(new Position(x+1, y-1));
            } else if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x, y-1));
                toCheck.add(new Position(x-1, y-1));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::y));
            for (Position box : toMove) {
                map[box.y()-1][box.x()] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()-1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()-1);
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushDown2(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        toCheck.add(new Position(position.x(), position.y()+1));

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x, y+1));
                toCheck.add(new Position(x+1, y+1));
            } else if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x, y+1));
                toCheck.add(new Position(x-1, y+1));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::y).reversed());
            for (Position box : toMove) {
                map[box.y()+1][box.x()] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()+1][position.x()] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x(), position.y()+1);
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushLeft2(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        toCheck.add(new Position(position.x()-1, position.y()));

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == ']') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x-1, y))) toMove.add(new Position(x-1, y));

                toCheck.add(new Position(x-2, y));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::x));
            for (Position box : toMove) {
                map[box.y()][box.x()-1] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()][position.x()-1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()-1, position.y());
        }

        return new Position(position.x(), position.y());
    }

    private static Position pushRight2(Position position, char[][] map) {
        Queue<Position> toCheck = new LinkedList<>();
        List<Position> toMove = new ArrayList<>();

        boolean canMove = true;
        toCheck.add(new Position(position.x()+1, position.y()));

        while (!toCheck.isEmpty()) {
            Position posToCheck = toCheck.poll();
            int x = posToCheck.x();
            int y = posToCheck.y();

            char c = map[y][x];

            if (c == '[') {
                if (!toMove.contains(new Position(x, y))) toMove.add(new Position(x, y));
                if (!toMove.contains(new Position(x+1, y))) toMove.add(new Position(x+1, y));

                toCheck.add(new Position(x+2, y));
            } else if (c == '#') {
                canMove = false;
                break;
            }
        }


        if (canMove) {
            toMove.sort(Comparator.comparingInt(Position::x).reversed());
            for (Position box : toMove) {
                map[box.y()][box.x()+1] = map[box.y()][box.x()];
                map[box.y()][box.x()] = '.';
            }

            map[position.y()][position.x()+1] = '@';
            map[position.y()][position.x()] = '.';
            return new Position(position.x()+1, position.y());
        }

        return new Position(position.x(), position.y());
    }

    private static char[][] extendMap(char[][] map) {
        char[][] extendedMap = new char[map.length][map[0].length * 2];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                if (c == '#' || c == '.') {
                    extendedMap[i][j*2] = c;
                    extendedMap[i][j*2 + 1] = c;
                } else if (c == 'O') {
                    extendedMap[i][j*2] = '[';
                    extendedMap[i][j*2 + 1] = ']';
                } else if (c == '@') {
                    extendedMap[i][j*2] = c;
                    extendedMap[i][j*2 + 1] = '.';
                }
            }
        }

        return extendedMap;
    }

    private static void print(char[][] map) {
        for (char[] line : map) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
