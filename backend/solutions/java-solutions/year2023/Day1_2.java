import java.util.ArrayList;
import java.util.List;

public class Day1_2 {

    public static void main(String[] args) {
        int sum = 0;

        for (String line : args) {
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
        if (isNumeric(number)) {
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

    private static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
