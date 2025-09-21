package year2018.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {
    public static List<String> readLines(String file) {
        try {
            Path path = Path.of("src/year2018/files/" + file);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String file) {
        try {
            Path path = Path.of("src/year2018/files/" + file);
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

    public static int max(Collection<Integer> numbers) {
        return numbers.stream().mapToInt(i -> i).max().getAsInt();
    }

    public static int max(int... numbers) {
        return Arrays.stream(numbers).max().getAsInt();
    }

    public static <K, V> K getKeyFromValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
