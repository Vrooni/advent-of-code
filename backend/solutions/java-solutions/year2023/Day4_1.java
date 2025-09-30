import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        Map<Integer, Integer> cards = new HashMap<>(); // cards with id and matches
        List<String> winningNumbers;
        int matches;

        for (String line : lines) {
            winningNumbers = new ArrayList<>();
            matches = 0;

            int id = Integer.parseInt(line
                    .split(":")[0]
                    .replace("Card", "")
                    .trim());

            line = line.substring(line.indexOf(":") + 1);
            String[] splittedLine = line.split("\\|");

            for (String number : splittedLine[0].split(" ")) {
                number = number.replaceAll(" ", "");
                if (!number.equals("")) {
                    winningNumbers.add(number);
                }
            }

            for (String number : splittedLine[1].split(" ")) {
                number = number.replaceAll(" ", "");
                if (!number.equals("") && winningNumbers.contains(number)) {
                    matches++;
                }
            }

            cards.put(id, matches);
        }

        int sum = 0;

        for (int value : cards.values()) {
            sum += Math.pow(2, value - 1);
        }

        System.out.println(sum);
    }
}
