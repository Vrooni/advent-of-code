package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day15 {
    private record Cookie(int capacity, int durability, int flavor, int texture, int calories) { }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/15.txt"));
        List<Cookie> cookies = readInput(input);

        int maxScore = getMaxScore(cookies, new ArrayList<>(), false);
        System.out.println(maxScore);

        //Part two
        maxScore = getMaxScore(cookies, new ArrayList<>(), true);
        System.out.println(maxScore);
    }


    public static List<Cookie> readInput(List<String> input) {
        List<Cookie> cookies = new ArrayList<>();

        for (String line : input) {
            String[] splittedLine = line.replace(",", "").split(" ");

            int capacity = Integer.parseInt(splittedLine[2]);
            int durability = Integer.parseInt(splittedLine[4]);
            int flavor = Integer.parseInt(splittedLine[6]);
            int texture = Integer.parseInt(splittedLine[8]);
            int calories = Integer.parseInt(splittedLine[10]);

            cookies.add(new Cookie(capacity, durability, flavor, texture, calories));
        }

        return cookies;
    }

    public static int getMaxScore(List<Cookie> cookies, List<Integer> division, boolean considerCalories) {
        int maxScore = Integer.MIN_VALUE;
        int sum = division.stream().mapToInt(d -> d).sum();

        if (division.size() == cookies.size()) {
            if (sum == 100) {
                return getScore(cookies, division, considerCalories);
            } else {
                return Integer.MIN_VALUE;
            }
        }

        for (int i = 0; i <= 100 - sum; i++) {
            division.add(i);
            maxScore = Math.max(getMaxScore(cookies, division, considerCalories), maxScore);
            division.remove(division.size()-1);
        }

        return maxScore;
    }

    private static int getScore(List<Cookie> cookies, List<Integer> division, boolean considerCalories) {
        int capacity = 0;
        int durability = 0;
        int flavor = 0;
        int texture = 0;
        int calories = 0;

        for (int i = 0; i < cookies.size(); i++) {
            capacity += division.get(i) * cookies.get(i).capacity;
            durability += division.get(i) * cookies.get(i).durability;
            flavor += division.get(i) * cookies.get(i).flavor;
            texture += division.get(i) * cookies.get(i).texture;
            calories += division.get(i) * cookies.get(i).calories;
        }

        return considerCalories && calories != 500
                ? Integer.MIN_VALUE
                : Math.max(0, capacity) * Math.max(0, durability) * Math.max(0, flavor) * Math.max(0, texture);
    }
}
