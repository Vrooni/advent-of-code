package year2018;

import year2018.utils.Utils;

public class Day5 {
    public static void main(String[] args) {
        //Part one
        String polymers = Utils.readString("05.txt");
        System.out.println(react(polymers, '-').length());


         //Part two
        int minLength = Integer.MAX_VALUE;

        for (char c = 'A'; c <= 'Z'; c++) {
            minLength = Math.min(minLength, react(polymers, c).length());
        }

        System.out.println(minLength);
    }

    private static String react(String polymers, char toDelete) {
        StringBuilder result = new StringBuilder();

        for (char polymer : polymers.toCharArray()) {
            if (polymer != toDelete && polymer != (char) (toDelete + 32)) {
                result.append(polymer);

                if (result.length() >= 2) {
                    char previousPolymer = result.charAt(result.length() - 2);
                    if (Math.abs(polymer - previousPolymer) == 32) {
                        result = new StringBuilder(result.substring(0, result.length() - 2));
                    }
                }
            }
        }

        return result.toString();
    }
}
