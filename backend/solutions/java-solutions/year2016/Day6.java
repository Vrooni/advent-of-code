package year2016;

import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("06.txt");
        List<Map<Character, Integer>> lettersOccurrences = init(input.get(0).length());

        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                Map<Character, Integer> map = lettersOccurrences.get(i);
                map.put(line.charAt(i), map.computeIfAbsent(line.charAt(i), k -> 0) + 1);
            }
        }

        List<Character> message = lettersOccurrences.stream().map(map -> Utils.getKeyByValue(map, Collections.max(map.values()))).toList();

        for (Character letter : message) {
            System.out.print(letter);
        }

        System.out.println();


        //Part two
        message = lettersOccurrences.stream().map(map -> Utils.getKeyByValue(map, Collections.min(map.values()))).toList();

        for (Character letter : message) {
            System.out.print(letter);
        }

        System.out.println();
    }

    private static List<Map<Character, Integer>> init(int size) {
        List<Map<Character, Integer>> lettersOccurrences = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lettersOccurrences.add(new HashMap<>());
        }

        return lettersOccurrences;
    }
}
