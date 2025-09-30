import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2_1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
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
