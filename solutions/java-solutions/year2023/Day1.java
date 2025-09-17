package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/01.txt"));
        int sum = 0;

        for (String line : lines) {
            line = line.replaceAll("[^0-9]", "");
            int n = line.length();

            String stringNumber = line.charAt(0) + line.substring(n - 1);
            sum += Integer.parseInt(stringNumber);
        }

        System.out.println(sum);


        //Part two
        sum = 0;

        for (String line : lines) {
            List<Integer> numbers = new ArrayList<>();

            for (int i = 0; i < line.length(); i++) {
                addNumber(numbers, line, i, i+3, "one", 1);
                addNumber(numbers, line, i, i+3, "two", 2);
                addNumber(numbers, line, i, i+5, "three", 3);
                addNumber(numbers, line, i, i+4, "four", 4);
                addNumber(numbers, line, i, i+4, "five", 5);
                addNumber(numbers, line, i, i+3, "six", 6);
                addNumber(numbers, line, i, i+5, "seven", 7);
                addNumber(numbers, line, i, i+5, "eight", 8);
                addNumber(numbers, line, i, i+4, "nine", 9);
            }

            sum += numbers.get(0)*10 + numbers.get(numbers.size()-1);
        }

        System.out.println(sum);
    }

    private static void addNumber(List<Integer> numbers, String s, int start, int end, String toReplace, int replacement) {
        String number = s.substring(start, start+1);
        if (Utils.isNumeric(number)) {
            numbers.add(Integer.valueOf(number));
            return;
        }

        if (end > s.length()) {
            return;
        }

        if (s.substring(start, end).equals(toReplace)) {
            numbers.add(replacement);
        }
    }
}
