package year2017;

import java.util.HashMap;
import java.util.Map;

public class Day3 {
    private static final int SQUARE = 312051;

    private record Position(int x, int y) {}

    public static void main(String[] args) {
        //THOUGHTS:

        //37  36  35  34  33  32  31
        //38  17  16  15  14  13  30
        //39  18   5   4   3  12  29
        //40  19   6   1   2  11  28
        //41  20   7   8   9  10  27
        //42  21  22  23  24  25  26
        //43  44  45  46  47  48  49

        //new circle width = previous circle width + 2 => circle with = circle*2 + 1
        //0: 1
        //1: 3
        //2: 5
        //3: 7

        //circle size = circumference = 4 * circle square width - 4
        //0: 1
        //1: 8
        //2: 16
        //3: 24

        //circle square end = circle width^2 => circle square end = (circle*2 + 1)^2
        //0: 1
        //1: 9
        //2: 25
        //3: 49

        //circle of square = ???
        //circle end = (circle*2 + 1)^2 <=> (sqr(circle end) - 1) / 2 = circle
        //circle of square = circle (rounded up)

        Position position = getPosition(SQUARE);
        System.out.println(Math.abs(position.x) + Math.abs(position.y));


        //Part two
        Map<Position, Integer> squares = new HashMap<>();
        squares.put(new Position(0, 0), 1);
        int result = 0;

        for (int i = 2; result <= SQUARE; i++) {
            position = getPosition(i);
            result = squares.getOrDefault(new Position(position.x+1, position.y), 0) //right
                    + squares.getOrDefault(new Position(position.x+1, position.y-1), 0) //right up
                    + squares.getOrDefault(new Position(position.x+1, position.y+1), 0) //right down
                    + squares.getOrDefault(new Position(position.x-1, position.y), 0) //left
                    + squares.getOrDefault(new Position(position.x-1, position.y-1), 0) //left up
                    + squares.getOrDefault(new Position(position.x-1, position.y+1), 0) //left down
                    + squares.getOrDefault(new Position(position.x, position.y-1), 0) //up
                    + squares.getOrDefault(new Position(position.x, position.y+1), 0); //down
            squares.put(position, result);
        }

        System.out.println(result);
    }

    private static Position getPosition(int square) {
        int circle = roundUp((Math.sqrt(square) - 1) / 2);
        int circleWidth = circle*2 + 1;
        int circleEnd = (int) Math.pow(circleWidth, 2);

        int sideEndDown = circleEnd - 0*(circleWidth - 1);
        int sideEndLeft = circleEnd - 1*(circleWidth - 1);
        int sideEndUp = circleEnd - 2*(circleWidth - 1);
        int sideEndRight = circleEnd - 3*(circleWidth - 1);

        int x;
        int y;

        //right
        if (square <= sideEndRight) {
            int middle = sideEndRight - (circleWidth/2);
            x = circle;
            y = -(square - middle);
        }

        //up
        else if (square <= sideEndUp) {
            int middle = sideEndUp - (circleWidth/2);
            y = -circle;
            x = -(square - middle);
        }

        //left
        else if (square <= sideEndLeft) {
            int middle = sideEndLeft - (circleWidth/2);
            x = -circle;
            y = square - middle;
        }

        //down
        else {
            int middle = sideEndDown - (circleWidth/2);
            y = circle;
            x = square - middle;
        }

        return new Position(x, y);
    }

    private static int roundUp(double i) {
        if (i > (int) i) {
            return (int) i + 1;
        } else {
            return (int) i;
        }
    }
}
