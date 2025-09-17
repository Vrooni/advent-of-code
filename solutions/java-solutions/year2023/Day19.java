package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {

    record Workflow(String condition, String workflow1, String workflow2) {}

    record Rating(int x, int m, int a, int s) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/18.txt"));
        List<Rating> ratings = new ArrayList<>();
        Map<String, Workflow> workflows = new HashMap<>();

        for (String line : lines) {

            if (line.startsWith("{")) {
                ratings.add(new Rating(
                        getValue(line, "x"),
                        getValue(line, "m"),
                        getValue(line, "a"),
                        getValue(line, "s")
                ));
            }

            else {
                String name = line.substring(0, line.indexOf("{"));
                line = line.substring(line.indexOf("{"))
                        .replace("{", "")
                        .replace("}", "");

                workflows.put(name, new Workflow(
                        line.split(",")[0].split(":")[0],
                        line.split(",")[0].split(":")[1],
                        line.split(",")[1]
                ));
            }
        }
    }


//    private static String function() {
//
//    }
    private static int getValue(String line, String letter) {
        for (String s : letter.replace("{", "").replace("}", "").split("")) {
            if (s.contains(letter)) {
                return Integer.parseInt(line
                        .replace(letter, "")
                        .replace("=", "")
                );
            }
        }

        throw new RuntimeException("No number for letter: " + letter);
    }
}

