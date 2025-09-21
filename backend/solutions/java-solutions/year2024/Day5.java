package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day5 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("05.txt");
        int emptyLineIndex = input.indexOf("");

        Map<String, List<String>> rules = new HashMap<>();
        List<List<String>> updates = input
                .subList(emptyLineIndex + 1, input.size()).stream()
                .map(update -> Arrays.stream(update.split(",")).toList())
                .toList();

        for (String rule : input.subList(0, emptyLineIndex)) {
            String[] ruleParts = rule.split("\\|");
            rules.computeIfAbsent(ruleParts[0], v -> new ArrayList<>()).add(ruleParts[1]);
        }

        int result = updates.stream()
                .filter(update -> isCorrect(update, rules))
                .mapToInt(update -> Integer.parseInt(update.get(update.size()/2)))
                .sum();
        
        System.out.println(result);


        //Part two
        List<List<String>> wrongUpdates = updates.stream()
                .filter(update -> !isCorrect(update, rules))
                .toList();

        List<List<String>> correctedUpdates = new ArrayList<>();

        for (List<String> wrongUpdate : wrongUpdates) {
            List<String> correctedUpdate = new ArrayList<>(wrongUpdate);
            correctedUpdate.sort((page1, page2) -> {
                List<String> pagesAfterPage1 = rules.getOrDefault(page1, new ArrayList<>());
                List<String> pagesAfterPage2 = rules.getOrDefault(page2, new ArrayList<>());

                if (pagesAfterPage1.contains(page2)) {
                    return 1;
                }

                if (pagesAfterPage2.contains(page1)) {
                    return -1;
                }

                return 0;
            });

            correctedUpdates.add(correctedUpdate);
        }

        result = correctedUpdates.stream()
                .mapToInt(update -> Integer.parseInt(update.get(update.size()/2)))
                .sum();

        System.out.println(result);
    }

    private static boolean isCorrect(List<String> update, Map<String, List<String>> rules) {
        for (int i = 0; i < update.size(); i++) {
            String page = update.get(i);
            List<String> pagesAfter = rules.getOrDefault(page, new ArrayList<>());

            for (int j = 0; j < i; j++) {
                if (pagesAfter.contains(update.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }
}
