package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day24 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/24.txt"));
        List<Integer> presents = input.stream().map(Integer::parseInt).toList();
        presents = reverse(new ArrayList<>(presents));

        System.out.println(getBestQE(presents, sum(presents) / 3));


        //Part two
        System.out.println(getBestQE(presents, sum(presents) / 4));
    }

    private static long getBestQE(List<Integer> presents, int size) {
        long qe = Long.MAX_VALUE;

        for (int i = 1; i < presents.size(); i++) {
            List<List<Integer>> combinations = new ArrayList<>();
            getCombinations(presents, combinations, new Integer[i], 0, presents.size()-1, 0, i);
            combinations = combinations.stream().filter(combination -> sum(combination) == size).toList();

            if (!combinations.isEmpty()) {
                for (List<Integer> combination : combinations) {
                    qe = Math.min(qe, mul(combination));
                }
                break;
            }
        }

        return qe;

    }

    private static void getCombinations(List<Integer> presents, List<List<Integer>> combinations, Integer[] combination, int start, int end, int index, int size) {
        if (index == size) {
            combinations.add(Arrays.asList(Arrays.copyOf(combination, combination.length)));
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= size - index; i++) {
            combination[index] = presents.get(i);
            getCombinations(presents, combinations, combination, i+1, end, index+1, size);
        }
    }

    private static int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(a -> a).sum();
    }

    public static long mul(List<Integer> numbers) {
        long x = 1;
        for (int number : numbers) {
            x *= number;
        }

        return x;
    }

    private static List<Integer> reverse(List<Integer> list) {
        List<Integer> shallowCopy = list.subList(0, list.size());
        Collections.reverse(shallowCopy);

        return shallowCopy;
    }
}
