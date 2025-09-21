package year2019;

import year2019.utils.Position;
import year2019.utils.Utils;

import java.util.*;

public class Day3 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("03.txt");

        List<Position> wire1 = readInput(input.get(0));
        List<Position> wire2 = readInput(input.get(1));

        List<Position> sortWire1 = new ArrayList<>(wire1);
        List<Position> sortWire2 = new ArrayList<>(wire2);
        sortWire1.sort(Comparator.comparingInt(o -> Math.abs(o.x()) + Math.abs(o.y())));
        sortWire2.sort(Comparator.comparingInt(o -> Math.abs(o.x()) + Math.abs(o.y())));

        for (Position position : sortWire1) {
            if (sortWire2.contains(position)) {
                System.out.println(Math.abs(position.x()) + Math.abs(position.y()));
                break;
            }
        }


        //Part two
        for (int i = 0; i < wire1.size(); i++) {
            Position position = wire1.get(i);
            int j = wire2.indexOf(position);

            if (j != -1) {
                System.out.println(i + j + 2);
                break;
            }
        }

        //TODO if answer two is wrong, use this
        /*
        I wasn't sure, if this is possible, so I added the implementation just in case.
        Explanation:  The solution above iterates over the first wire and takes the first matching pair with wire2.
                      Which could be the wrong answer if there's another pair with less distance before wire1's index
                      reaches the wire2's index (the index where we first find our matching pair).
        Example:
                      0 6 => first, but distance 6
                      1 2 => second, the right answer with distance 3
                      3 4 => third, which we check, but it's distance is 7
                      7 5 => out of wire2's index (index 6), we can stop continue searching
         */

//        int bound = Integer.MAX_VALUE;
//        int distance = Integer.MAX_VALUE;
//        boolean foundFirst = false;
//
//        for (int i = 0; i < wire1.size(); i++) {
//            if (i >= bound) {
//                break;
//            }
//
//            Position position = wire1.get(i);
//            int j = wire2.indexOf(position);
//
//            if (j != -1) {
//                bound = foundFirst ? bound : j;
//                distance = Math.min(distance, i + j + 2);
//                foundFirst = true;
//            }
//        }
//
//        System.out.println(distance);
    }

    private static List<Position> readInput(String input) {
        List<Position> wire = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (String move : input.split(",")) {
            int steps = Integer.parseInt(move.substring(1));

            switch (move.charAt(0)) {
                case 'U' -> {
                    addPositions(wire, x, x, y - 1, y - steps, false, true);
                    y -= steps;
                }
                case 'D' -> {
                    addPositions(wire, x, x, y + 1, y + steps, false, false);
                    y += steps;
                }
                case 'L' -> {
                    addPositions(wire, x - 1, x - steps, y, y, true, false);
                    x -= steps;
                }
                case 'R' -> {
                    addPositions(wire, x + 1, x + steps, y, y, false, false);
                    x += steps;
                }
            }
        }

        return wire;
    }

    private static void addPositions(List<Position> wire, int fromX, int toX, int fromY, int toY, boolean negativeX, boolean negativeY) {
        if (negativeY) {
            for (int y = fromY; y >= toY; y--) {
                addPosition(wire, fromX, toX, negativeX, y);
            }
        } else {
            for (int y = fromY; y <= toY; y++) {
                addPosition(wire, fromX, toX, negativeX, y);
            }
        }
    }

    private static void addPosition(List<Position> wire, int fromX, int toX, boolean negativeX, int y) {
        if (negativeX) {
            for (int x = fromX; x >= toX; x--) {
                wire.add(new Position(x, y));
            }
        } else {
            for (int x = fromX; x <= toX; x++) {
                wire.add(new Position(x, y));
            }
        }
    }
}
