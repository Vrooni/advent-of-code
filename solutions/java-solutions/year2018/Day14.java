package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 {
    private static final int RECEIPTS = 919901;

    public static void main(String[] args) {
        //Part one
        List<Integer> scores = new ArrayList<>(List.of(3, 7));
        int receipt1 = 0;
        int receipt2 = 1;

        while (scores.size() < RECEIPTS + 10) {
            int newScore = scores.get(receipt1) + scores.get(receipt2);
            if (newScore < 10) {
                scores.add(newScore);
            } else {
                scores.add(newScore / 10);
                scores.add(newScore % 10);
            }

            receipt1 = (receipt1 + scores.get(receipt1) + 1) % scores.size();
            receipt2 = (receipt2 + scores.get(receipt2) + 1) % scores.size();
        }

        String result = scores
                .subList(RECEIPTS, RECEIPTS + 10).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));

        System.out.println(result);

        //Part two
        List<Integer> receipts = Integer.toString(RECEIPTS).chars()
                .map(Character::getNumericValue).boxed()
                .toList();

        scores = new ArrayList<>(List.of(3, 7));
        receipt1 = 0;
        receipt2 = 1;

        while (!contains(scores, receipts)) {
            int newScore = scores.get(receipt1) + scores.get(receipt2);
            if (newScore < 10) {
                scores.add(newScore);
            } else {
                scores.add(newScore / 10);
                scores.add(newScore % 10);
            }

            receipt1 = (receipt1 + scores.get(receipt1) + 1) % scores.size();
            receipt2 = (receipt2 + scores.get(receipt2) + 1) % scores.size();
        }

        System.out.println(scores.subList(0, indexOf(scores)).size());
    }

    private static boolean contains(List<Integer> scores, List<Integer> receipts) {
        if (scores.size() < receipts.size()) {
            return false;
        }

        boolean contains = containsFrom(scores, receipts, scores.size() - receipts.size());
        if (contains) {
            return true;
        }

        if (scores.size() < receipts.size() + 1) {
            return false;
        }

        return containsFrom(scores, receipts, scores.size() - receipts.size() - 1);
    }

    private static boolean containsFrom(List<Integer> scores, List<Integer> receipts, int offset) {
        for (int i = 0; i < receipts.size(); i++) {
            if (!scores.get(i + offset).equals(receipts.get(i))) {
                return false;
            }
        }

        return true;
    }

    private static int indexOf(List<Integer> scores) {
        String receiptsString = String.valueOf(RECEIPTS);

        String scoresString = scores.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));

        return scoresString.indexOf(receiptsString);
    }
}
