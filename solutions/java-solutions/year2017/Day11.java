package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day11 {
    private record Position(int x, int y) {}

    /**
     * THOUGHTS
     *     0,0     2,0
     *-1,1     1,1
     *     0,2     2,2
     * -1,3    1,3
     *     0,4     2,4
     * -1,5    1,5
     * <p>
     * Steps down/up: by 2
     * Steps diagonal 1
     * <p>
     * Part two:
     *     0:0     2:2
     * 2:1     2:1
     *     2:1     4:2
     * 4:2     4:2
     *     4:2     6:3
     * 6:3     6:3
     * <p>
     * Right and Left are more expensive in relation, so save all with same coord distance (x+y)
     */
    public static void main(String[] args) {
        //Part one
        String directions = Utils.readString("11.txt");

        int x = 0;
        int y = 0;

        for (String direction : directions.split(",")) {
            switch (direction) {
                case "n" -> y -= 2;
                case "s" -> y += 2;
                case "nw" -> { x++; y--; }
                case "sw" -> { x++; y++; }
                case "ne" -> { x--; y--; }
                case "se" -> { x--; y++; }
            }
        }

        System.out.println(getSteps(x, y));


        //Part two
        List<Position> furthestList = new ArrayList<>();
        int furthest = 0;
        x = 0;
        y = 0;

        for (String direction : directions.split(",")) {
            switch (direction) {
                case "n" -> y -= 2;
                case "s" -> y += 2;
                case "nw" -> { x++; y--; }
                case "sw" -> { x++; y++; }
                case "ne" -> { x--; y--; }
                case "se" -> { x--; y++; }
            }

            int dist = Math.abs(x) + Math.abs(y);

            if (dist == furthest) {
                furthestList.add(new Position(x, y));
            }

            else if (dist > furthest) {
                furthest = dist;
                furthestList = new ArrayList<>();
                furthestList.add(new Position(x, y));
            }
        }

        int steps = 0;

        for (Position position : furthestList) {
            steps = Math.max(steps, getSteps(position.x, position.y));
        }

        System.out.println(steps);

    }

    private static int getSteps(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);

        int diagonalSteps = Math.min(x, y);

        x -= diagonalSteps;
        y -= diagonalSteps;

        return diagonalSteps + y/2 + x;
    }
}
