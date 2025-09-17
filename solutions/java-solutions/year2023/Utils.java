package year2023;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Utils {
    public static List<String> readLines(Path path) throws IOException {
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static String read(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    public static int max(Collection<Integer> numbers) {
        try {
            return Collections.max(numbers);
        } catch (NoSuchElementException ex) {
            return 0;
        }
    }

    public static int min(Collection<Integer> numbers) {
        try {
            return Collections.min(numbers);
        } catch (NoSuchElementException ex) {
            return 0;
        }
    }

    public static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }

    public static int mul(List<Integer> numbers) {
        int result = 1;
        for (int number : numbers) {
            result *= number;
        }

        return result;
    }

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumeric(char c) {
        try {
            Double.parseDouble(new String(String.valueOf(c)));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer last(List<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(list.size()-1);
    }

    public static String replace(String s, int index, char c) {
        return s.substring(0, index) + c + s.substring(index + 1);
    }

    public static void printLines(List<String> lines) {
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println();
    }

    public static void print(List<Integer> numbers) {
        for (int number : numbers) {
            System.out.print(number + ",");
        }

        System.out.println();
    }
}
