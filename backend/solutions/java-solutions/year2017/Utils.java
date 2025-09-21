package year2017;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Utils {
    public static List<String> readLines(String file) {
        try {
            Path path = Path.of("src/year2017/files/" + file);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String file) {
        try {
            Path path = Path.of("src/year2017/files/" + file);
            return Files.readString(path, StandardCharsets.UTF_8).replace("\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int sum(List<Integer> list) {
        int sum = 0;

        for (Integer i : list) {
            sum += i;
        }

        return sum;
    }

    public static String swap(String s, int index1, int index2) {
        StringBuilder builder = new StringBuilder(s);

        char temp = builder.charAt(index1);
        builder.setCharAt(index1, builder.charAt(index2));
        builder.setCharAt(index2, temp);

        return builder.toString();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
