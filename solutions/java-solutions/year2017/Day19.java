package year2017;

import java.util.List;

public class Day19 {
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static void main(String[] args) {
        //Part one
        List<String> map = Utils.readLines("19.txt");

        StringBuilder path = new StringBuilder();
        int x = map.get(0).indexOf("|");
        int y = 0;

        Direction direction = Direction.DOWN;
        char current = charAt(map, x, y);
        int steps = 0;

        while (current != ' ') {
            switch (current) {
                case '+' -> direction = nextDirection(x, y, direction, map);
                case '|', '-' -> { /* do nothing */ }
                default -> path.append(current);
            }

            switch (direction) {
                case UP -> y--;
                case DOWN -> y++;
                case LEFT -> x--;
                case RIGHT -> x++;
            }

            current = charAt(map, x, y);
            steps++;
        }

        System.out.println(path);

        //Part two
        System.out.println(steps);
    }

    private static Direction nextDirection(int x, int y, Direction direction, List<String> map) {
        if (direction != Direction.DOWN && charAt(map, x, y-1) != ' ') {
            return Direction.UP;
        }

        if (direction != Direction.UP && charAt(map, x, y+1) != ' ') {
            return Direction.DOWN;
        }

        if (direction != Direction.RIGHT && charAt(map, x-1, y) != ' ') {
            return Direction.LEFT;
        }

        if (direction != Direction.LEFT && charAt(map, x+1, y) != ' ') {
            return Direction.RIGHT;
        }

        throw new RuntimeException("No next direction");
    }

    private static char charAt(List<String> map, int x, int y) {
        try {
            return map.get(y).charAt(x);
        } catch (IndexOutOfBoundsException e) {
            return ' ';
        }
    }
}
