package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day19 {
    private static final Map<String, Long> memory = new HashMap<>();

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("19.txt");
        String[] towels = input.get(0).split(", ");

        int possibleDesigns = 0;
        for (int i = 2; i < input.size(); i++) {
            if (isPossible(input.get(i), towels)) {
                possibleDesigns++;
            }
        }

        System.out.println(possibleDesigns);


        //Part two
        long combinations = 0L;
        for (int i = 2; i < input.size(); i++) {
            combinations += getCombinations(input.get(i), towels);
        }

        System.out.println(combinations);
    }

    private static boolean isPossible(String design, String[] towels) {
        Queue<String> queue = new LinkedList<>();
        queue.add(design);
        Set<String> seenDesigns = new HashSet<>();
        seenDesigns.add(design);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.isEmpty()) {
                return true;
            }

            List<String> possibleTowels = Arrays.stream(towels)
                    .filter(current::startsWith)
                    .toList();

            for (String possibleTowel : possibleTowels) {
                String remainingDesign = current.replaceFirst(possibleTowel, "");
                if (!seenDesigns.contains(remainingDesign)) {
                    queue.add(remainingDesign);
                    seenDesigns.add(remainingDesign);
                }
            }
        }

        return false;
    }

    private static long getCombinations(String design, String[] towels) {
        if (design.isEmpty()) {
            return 1;
        }

        Long combinations = memory.get(design);
        if (combinations != null) {
            return combinations;
        }

        combinations = 0L;
        List<String> possibleTowels = Arrays.stream(towels)
                .filter(design::startsWith)
                .toList();

        for (String possibleTowel : possibleTowels) {
            combinations += getCombinations(design.replaceFirst(possibleTowel, ""), towels);
        }

        memory.put(design, combinations);
        return combinations;
    }
}
