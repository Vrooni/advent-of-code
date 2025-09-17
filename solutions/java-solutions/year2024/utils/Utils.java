package year2024.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Utils {
    public static List<String> readLines(String file) {
        try {
            Path path = Path.of("src/year2024/files/" + file);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String file) {
        try {
            Path path = Path.of("src/year2024/files/" + file);
            return Files.readString(path, StandardCharsets.UTF_8).replace("\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
