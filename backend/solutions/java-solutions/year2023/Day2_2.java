import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2_2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int sum = 0;

        for (String line : lines) {
            line = line.replace("Game ", "");

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
}