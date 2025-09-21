package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day1 {
    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private record Point(int x, int y) {}

    public static void main(String[] args) {
        //Part one
        String moves = Utils.readString("01.txt");

        int x = 0;
        int y = 0;
        Direction facing = Direction.NORTH;

        for (String move : moves.split(", ")) {
            switch (move.charAt(0)) {
                case 'R' -> facing = Direction.values()[facing.ordinal() + 1 > 3 ? 0 : facing.ordinal() + 1];
                case 'L' -> facing = Direction.values()[facing.ordinal() - 1 < 0 ? 3 : facing.ordinal() - 1];
            }

            int steps = Integer.parseInt(move.substring(1));

            switch (facing) {
                case NORTH -> y -= steps;
                case SOUTH -> y += steps;
                case EAST -> x += steps;
                case WEST -> x -= steps;
            }
        }

        System.out.println(Math.abs(x) + Math.abs(y));


        //Part two
        x = 0;
        y = 0;
        facing = Direction.NORTH;

        List<Point> visited = new ArrayList<>();

        for (String move : moves.split(", ")) {
            switch (move.charAt(0)) {
                case 'R' -> facing = Direction.values()[facing.ordinal() + 1 > 3 ? 0 : facing.ordinal() + 1];
                case 'L' -> facing = Direction.values()[facing.ordinal() - 1 < 0 ? 3 : facing.ordinal() - 1];
            }

            int steps = Integer.parseInt(move.substring(1));

            switch (facing) {
                case NORTH -> {
                    for (int i = y; i > y - steps; i--) {
                        if (visited.contains(new Point(x, i))) {
                            System.out.println(Math.abs(x) + Math.abs(i));
                            return;
                        }
                        visited.add(new Point(x, i));
                    }

                    y -= steps;
                }
                case SOUTH -> {
                    for (int i = y; i < y + steps; i++) {
                        if (visited.contains(new Point(x, i))) {
                            System.out.println(Math.abs(x) + Math.abs(i));
                            return;
                        }
                        visited.add(new Point(x, i));
                    }

                    y += steps;
                }
                case EAST -> {
                    for (int i = x; i < x + steps; i++) {
                        if (visited.contains(new Point(i, y))) {
                            System.out.println(Math.abs(i) + Math.abs(y));
                            return;
                        }
                        visited.add(new Point(i, y));
                    }

                    x += steps;
                }
                case WEST -> {
                    for (int i = x; i > x - steps; i--) {
                        if (visited.contains(new Point(i, y))) {
                            System.out.println(Math.abs(i) + Math.abs(y));
                            return;
                        }
                        visited.add(new Point(i, y));
                    }

                    x -= steps;
                }
            }
        }
    }
}
