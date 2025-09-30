import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4_2 {

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

        Map<Integer, Integer> wonCards = new HashMap<>(); // won cards with id and times card has been won

        for (Map.Entry<Integer, Integer> entrySet : cards.entrySet()) {
            wonCards.put(entrySet.getKey(), 1);
        }

        for (int i = 1; i <= cards.size(); i++) {
            int matchesCard = cards.get(i);
            int wonCountCard = wonCards.get(i);

            for (int j = i + 1; j <= i + matchesCard; j++) {
                wonCards.put(j, wonCards.get(j) + wonCountCard);
            }
        }

        System.out.println(sum(wonCards.values().stream().toList()));
    }

    private static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }
}
