package year2024;

import year2024.utils.Utils;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        List<String> input = Utils.readLines("04.txt");
        int width = input.get(0).length();
        int height = input.size();
        int count = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Horizontal forwards
                if (j <= width - 4 && input.get(i).startsWith("XMAS", j)) {
                    count++;
                }

                // Horizontal backwards
                if (j >= 3 && input.get(i).substring(j - 3, j + 1).equals("SAMX")) {
                    count++;
                }

                // Vertical forwards
                if (i <= height - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j) == 'M'
                        && input.get(i + 2).charAt(j) == 'A'
                        && input.get(i + 3).charAt(j) == 'S') {
                    count++;
                }

                // Vertical backwards
                if (i >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j) == 'M'
                        && input.get(i - 2).charAt(j) == 'A'
                        && input.get(i - 3).charAt(j) == 'S') {
                    count++;
                }

                // Diagonal down-right
                if (i <= height - 4 && j <= width - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j + 1) == 'M'
                        && input.get(i + 2).charAt(j + 2) == 'A'
                        && input.get(i + 3).charAt(j + 3) == 'S') {
                    count++;
                }

                // Diagonal up-left
                if (i >= 3 && j >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j - 1) == 'M'
                        && input.get(i - 2).charAt(j - 2) == 'A'
                        && input.get(i - 3).charAt(j - 3) == 'S') {
                    count++;
                }

                // Diagonal down-left
                if (i <= height - 4 && j >= 3
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i + 1).charAt(j - 1) == 'M'
                        && input.get(i + 2).charAt(j - 2) == 'A'
                        && input.get(i + 3).charAt(j - 3) == 'S') {
                    count++;
                }

                // Diagonal up-right
                if (i >= 3 && j <= width - 4
                        && input.get(i).charAt(j) == 'X'
                        && input.get(i - 1).charAt(j + 1) == 'M'
                        && input.get(i - 2).charAt(j + 2) == 'A'
                        && input.get(i - 3).charAt(j + 3) == 'S') {
                    count++;
                }
            }
        }

        System.out.println(count);


        //Part two
        count = 0;

        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if ((diagonalDownLeft(input, i, j) || diagonalUpRight(input, i, j))
                    && (diagonalDownRight(input, i, j) || diagonalUpLeft(input, i, j))) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static boolean diagonalDownLeft(List<String> input, int i, int j) {
        return input.get(i-1).charAt(j+1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i+1).charAt(j-1) == 'S';
    }

    private static boolean diagonalUpRight(List<String> input, int i, int j) {
        return input.get(i+1).charAt(j-1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i-1).charAt(j+1) == 'S';
    }

    private static boolean diagonalDownRight(List<String> input, int i, int j) {
        return input.get(i-1).charAt(j-1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i+1).charAt(j+1) == 'S';
    }

    private static boolean diagonalUpLeft(List<String> input, int i, int j) {
        return input.get(i+1).charAt(j+1) == 'M'
                && input.get(i).charAt(j) == 'A'
                && input.get(i-1).charAt(j-1) == 'S';
    }
}
