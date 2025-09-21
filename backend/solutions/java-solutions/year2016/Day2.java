package year2016;

import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        //Part one
        List<String> movesLine = Utils.readLines("02.txt");
        char[][] keypad = {
                {'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}
        };

        int x = 1;
        int y = 1;
        StringBuilder code = new StringBuilder();

        for (String moves : movesLine) {
            for (String move : moves.split("")) {
                switch (move) {
                    case "U" -> y = Math.max(0, y - 1);
                    case "D" -> y = Math.min(2, y + 1);
                    case "L" -> x = Math.max(0, x - 1);
                    case "R" -> x = Math.min(2, x + 1);
                }
            }

            code.append(keypad[y][x]);
        }

        System.out.println(code);


        //Part two
        keypad = new char[][] {
                {' ', ' ', '1', ' ', ' '},
                {' ', '2', '3', '4', ' '},
                {'5', '6', '7', '8', '9'},
                {' ', 'A', 'B', 'C', ' '},
                {' ', ' ', 'D', ' ', ' '},
        };

        x = 0;
        y = 2;
        code = new StringBuilder();

        for (String moves : movesLine) {
            for (String move : moves.split("")) {
                switch (move) {
                    case "U" -> y = y - 1 < 0 || keypad[y - 1][x] == ' ' ? y : y - 1;
                    case "D" -> y = y + 1 > 4 || keypad[y + 1][x] == ' ' ? y : y + 1;
                    case "L" -> x = x - 1 < 0 || keypad[y][x - 1] == ' ' ? x : x - 1;
                    case "R" -> x = x + 1 > 4 || keypad[y][x + 1] == ' ' ? x : x + 1;
                }
            }

            code.append(keypad[y][x]);
        }

        System.out.println(code);
    }
}
