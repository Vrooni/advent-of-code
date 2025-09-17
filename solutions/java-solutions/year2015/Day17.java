package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day17 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/17.txt"));
        List<Integer> containers = readInput(input);
        List<List<Integer>> allPossibleCombinations = getPossibleCombinations(containers, new ArrayList<>());

        System.out.println(allPossibleCombinations.size());

        //Part two
        int minimumContainers = allPossibleCombinations.stream().mapToInt(List::size).min().getAsInt();
        long count = allPossibleCombinations.stream().filter(container -> container.size() == minimumContainers).count();
        System.out.println(count);
    }

    private static List<Integer> readInput(List<String> input) {
        List<Integer> containers = new ArrayList<>();

        for (String line : input) {
            containers.add(Integer.parseInt(line));
        }

        return containers;
    }

    private static List<List<Integer>> getPossibleCombinations(List<Integer> containers, List<Integer> thisCombination) {
        List<List<Integer>> possibleCombination = new ArrayList<>();
        int filledLiters = thisCombination.stream().mapToInt(c -> c).sum();

        if (filledLiters == 150) {
            possibleCombination.add(new ArrayList<>(thisCombination));
            return possibleCombination;
        }

        List<Integer> copyContainers = new ArrayList<>(containers);
        for (int container : containers) {
            copyContainers.remove(copyContainers.indexOf(container));

            if (filledLiters + container > 150) {
                continue;
            }

            thisCombination.add(container);
            possibleCombination.addAll(getPossibleCombinations(copyContainers, thisCombination));
            thisCombination.remove(thisCombination.size()-1);
        }

        return possibleCombination;
    }
}
