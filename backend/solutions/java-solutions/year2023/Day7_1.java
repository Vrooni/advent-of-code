import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day7_1 {
    record HandInformation(String cards, int points, int type) {
    }

    private static final Map<Character, Integer> ranking = new HashMap<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        List<HandInformation> hands = new ArrayList<>();
        initRankingMap();
        int sum = 0;

        for (String line : lines) {
            String cards = line.split(" ")[0];
            int points = Integer.parseInt(line.split(" ")[1]);

            hands.add(new HandInformation(cards, points, getType(cards)));
        }

        rankHands(hands);

        for (int i = 0; i < hands.size(); i++) {
            sum += (i + 1) * hands.get(i).points;
        }

        System.out.println(sum);
    }

    private static void initRankingMap() {
        ranking.put('A', 13);
        ranking.put('K', 12);
        ranking.put('Q', 11);
        ranking.put('J', 10);
        ranking.put('T', 9);
        ranking.put('9', 8);
        ranking.put('8', 7);
        ranking.put('7', 6);
        ranking.put('6', 5);
        ranking.put('5', 4);
        ranking.put('4', 3);
        ranking.put('3', 2);
        ranking.put('2', 1);
    }

    private static void rankHands(List<HandInformation> hands) {
        hands.sort((o1, o2) -> {
            if (o1.type > o2.type) {
                return 1;
            } else if (o1.type < o2.type) {
                return -1;
            }

            for (int i = 0; i < o1.cards.length(); i++) {
                int r1 = ranking.get(o1.cards.charAt(i));
                int r2 = ranking.get(o2.cards.charAt(i));

                if (r1 > r2) {
                    return 1;
                } else if (r1 < r2) {
                    return -1;
                }
            }

            return 0;
        });
    }

    private static int getType(String cards) {
        Map<Character, Integer> numberCountMap = new HashMap<>();
        for (char card : cards.toCharArray()) {
            numberCountMap.put(card, numberCountMap.getOrDefault(card, 0) + 1);
        }

        if (numberCountMap.containsValue(5)) {
            return 7;
        } else if (numberCountMap.containsValue(4)) {
            return 6;
        } else if (numberCountMap.containsValue(3)) {

            if (numberCountMap.containsValue(2)) {
                return 5;
            } else {
                return 4;
            }

        } else if (numberCountMap.size() == 3) {
            return 3;
        } else if (numberCountMap.containsValue(2)) {
            return 2;
        } else {
            return 1;
        }
    }
}
