package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day2 {

    public static void main(String[] args) throws IOException {

        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/02.txt"));
        int sum = 0;

        for (String line : lines) {
            line = line.replace("Game ", "");
            int id = Integer.parseInt(line.substring(0, line.indexOf(":")));
            boolean possbile = true;

            line = line.substring(line.indexOf(":") + 1);
            String[] sets = line.split(";");

            outer: for (String set : sets) {
                String[] reveals = set.split(", ");

                for (String reveal : reveals) {
                    reveal = reveal.trim();

                    int count = Integer.parseInt(reveal.split(" ")[0]);
                    String color = reveal.split(" ")[1];

                    if (!isPossible(color, count)) {
                        possbile = false;
                        break outer;
                    }
                }
            }

            if (possbile) {
                sum += id;
            }
        }

        System.out.println(sum);


        //Part two
        sum = 0;

        for (String line : lines) {
            line = line.replace("Game ", "");
            int id = Integer.parseInt(line.substring(0, line.indexOf(":")));

            int red = 0;
            int green = 0;
            int blue = 0;

            line = line.substring(line.indexOf(":") + 1);
            String[] sets = line.split(";");

            for (String set : sets) {
                String[] reveals = set.split(", ");

                for (String reveal : reveals) {
                    reveal = reveal.trim();

                    int count = Integer.parseInt(reveal.split(" ")[0]);
                    String color = reveal.split(" ")[1];

                    switch (color) {
                        case "red":
                            red = Math.max(red, count);
                            break;
                        case "green":
                            green = Math.max(green, count);
                            break;
                        case "blue":
                            blue = Math.max(blue, count);
                            break;
                    }
                }
            }

            sum += red * green * blue;
        }

        System.out.println(sum);
    }

    private static boolean isPossible(String color, int count) {
        switch (color) {
            case "red":
                if (count > 12) {
                    return false;
                }
                break;
            case "green":
                if (count > 13) {
                    return false;
                }
                break;
            case "blue":
                if (count > 14) {
                    return false;
                }
                break;
        }

        return true;
    }
}
