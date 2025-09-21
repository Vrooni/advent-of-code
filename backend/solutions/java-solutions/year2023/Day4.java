package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) throws IOException {

        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/04.txt"));
        Map<Integer, Integer> cards = new HashMap<>(); //cards with id and matches
        List<String> winningNumbers;
        int matches;

        for (String line : lines) {
            winningNumbers = new ArrayList<>();
            matches = 0;

            int id = Integer.parseInt(line
                    .split(":")[0]
                    .replace("Card", "")
                    .trim()
            );

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


        //Part two
        Map<Integer, Integer> wonCards = new HashMap<>(); //won cards with id and times card has been won

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

        System.out.println(Utils.sum(wonCards.values().stream().toList()));
    }
}
