package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day12 {
    record SpringInformation(String springs, List<Integer> damagedSprings) {}

    private static Map<SpringInformation, Long> memoization = new HashMap<>();

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/12.txt"));
        long result = 0;

        for (String line : lines) {
            String[] splitLine = line.split(" ");

            String springs = splitLine[0];
            List<Integer> damagedSprings = getDamagedSprings(splitLine[1]);

            result += getCombinations(springs, damagedSprings);
        }

        System.out.println(result);


        //Part two
        result = 0;

        for (String line : lines) {
            String[] splitLine = line.split(" ");

            String springs = expandSprings(splitLine[0]);
            List<Integer> damagedSprings = expandDamagedSprings(getDamagedSprings(splitLine[1]));

            result += getCombinations(springs, damagedSprings);
        }

        System.out.println(result);
    }

    private static List<Integer> getDamagedSprings(String damagedSprings) {
        return Arrays.stream(damagedSprings.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private static String expandSprings(String springs) {
        StringBuilder expandedSprings = new StringBuilder(springs);

        for (int i = 0; i < 4; i++) {
            expandedSprings.append("?").append(springs);
        }

        return expandedSprings.toString();
    }

    private static List<Integer> expandDamagedSprings(List<Integer> damagedSprings) {
        List<Integer> expandedDamagedSprings = new ArrayList<>(damagedSprings);

        for (int i = 0; i < 4; i++) {
            expandedDamagedSprings.addAll(damagedSprings);
        }

        return expandedDamagedSprings;
    }

    private static long getCombinations(String springs, List<Integer> damagedSprings) {
        long combinations = 0;

        SpringInformation springInformation = new SpringInformation(springs, damagedSprings);
        if (memoization.containsKey(springInformation)) {
            return memoization.get(springInformation);
        }

        //reached end
        if (springs.isBlank()) {
            return damagedSprings.isEmpty() ? 1 : 0;
        }

        switch (springs.charAt(0)) {
            case '.' -> combinations = getCombinations(springs.substring(1), damagedSprings);

            case '?' -> combinations = getCombinations("." + springs.substring(1), damagedSprings)
                    + getCombinations('#' + springs.substring(1), damagedSprings);

            case '#' -> {
                if (damagedSprings.isEmpty()) {
                    break;
                }

                int damaged = damagedSprings.get(0);
                if (damaged > springs.length()) {
                    break;
                }

                if (!springs.chars().limit(damaged).allMatch(spring -> spring == '#' || spring == '?')) {
                    break;
                }

                if (damaged == springs.length()) {
                    combinations = damagedSprings.size() == 1 ? 1 : 0;
                    break;
                }

                if (springs.charAt(damaged) == '#') {
                    break;
                }

                combinations = getCombinations(springs.substring(damaged+1), damagedSprings.subList(1, damagedSprings.size()));
            }
        }

        memoization.put(springInformation, combinations);
        return combinations;
    }
}
