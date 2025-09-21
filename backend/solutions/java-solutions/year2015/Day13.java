package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/13.txt"));
        Map<String, Map<String, Integer>> personList = readInput(input);
        List<List<String>> possibleCombinations = getPossibleCombinations(personList);

        int happiness = getBestCombination(personList, possibleCombinations);
        System.out.println(happiness);

        //Part two
        addMeToList(personList);
        possibleCombinations = getPossibleCombinations(personList);

        happiness = getBestCombination(personList, possibleCombinations);
        System.out.println(happiness);
    }

    private static Map<String, Map<String, Integer>> readInput(List<String> input) {
        Map<String, Map<String, Integer>> personList = new HashMap<>();

        for (String line : input) {
            String[] splittedString = line.split(" ");
            String name = splittedString[0];
            String neighbourName = splittedString[splittedString.length-1].replace(".", "");
            int value = splittedString[2].equals("gain") ? Integer.parseInt(splittedString[3]) : -Integer.parseInt(splittedString[3]);

            Map<String, Integer> neighbours = personList.computeIfAbsent(splittedString[0], (s) -> new HashMap<>());
            neighbours.put(neighbourName, value);
        }

        return personList;
    }

    private static List<List<String>> getPossibleCombinations(Map<String, Map<String, Integer>> personList) {
        List<List<String>> possibleCombinations = new ArrayList<>();
        Set<String> personNames = new HashSet<>(personList.keySet());
        String personAtEnd = personNames.iterator().next();
        personNames.remove(personAtEnd);

        getAllPossibleCombinations(personNames, possibleCombinations, new Stack<>(), personNames.size());
        possibleCombinations.forEach(possibleCombination -> possibleCombination.add(personAtEnd));

        return possibleCombinations;
    }

    private static void getAllPossibleCombinations(Set<String> persons, List<List<String>> possibleCombinations, Stack<String> thisCombination, int size) {
        if (thisCombination.size() == size) {
            possibleCombinations.add(new ArrayList<>(thisCombination));
        }

        String[] personCopy = persons.toArray(new String[0]);

        for(String person : personCopy) {
            thisCombination.push(person);

            persons.remove(person);
            getAllPossibleCombinations(persons, possibleCombinations, thisCombination, size);
            persons.add(thisCombination.pop());
        }
    }

    private static int getBestCombination(Map<String, Map<String, Integer>> personList, List<List<String>> possibleCombinations) {
        int maxCount = Integer.MIN_VALUE;

        for (List<String> possibleCombination : possibleCombinations) {
            int happiness = 0;

            for (int i = 1; i < possibleCombination.size()-1; i++) {
                happiness += personList.get(possibleCombination.get(i)).get(possibleCombination.get(i+1));
                happiness += personList.get(possibleCombination.get(i)).get(possibleCombination.get(i-1));
            }

            int n = possibleCombination.size();
            //START of circle
            happiness += personList.get(possibleCombination.get(0)).get(possibleCombination.get(n-1));
            happiness += personList.get(possibleCombination.get(0)).get(possibleCombination.get(1));
            //END of circle
            happiness += personList.get(possibleCombination.get(n-1)).get(possibleCombination.get(n-2));
            happiness += personList.get(possibleCombination.get(n-1)).get(possibleCombination.get(0));

            maxCount = Math.max(maxCount, happiness);
        }

        return maxCount;
    }

    private static void addMeToList(Map<String, Map<String, Integer>> personList) {
        for (String person : personList.keySet()) {
            personList.get(person).put("Ich", 0);
        }

        personList.put("Ich", personList.keySet().stream().collect(Collectors.toMap(Function.identity(), person -> 0)));
    }
}
