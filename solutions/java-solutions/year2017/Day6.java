package year2017;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("06.txt");
        String[] splitInput = input.trim().split("\t");
        List<Integer> banks = new ArrayList<>(Arrays.stream(splitInput).map(Integer::parseInt).toList());

        List<List<Integer>> seen = new ArrayList<>();
        int steps;

        for (steps = 0; !seen.contains(banks); steps++) {
            seen.add(new ArrayList<>(banks));

            int max = banks.stream().max(Integer::compareTo).orElse(0);
            int indexMax = banks.indexOf(max);

            banks.set(indexMax, 0);

            for (int i = 1; i <= max; i++) {
                int index = (indexMax + i) % banks.size();
                banks.set(index, banks.get(index)+1);
            }
        }

        System.out.println(steps);


        //Part two
        System.out.println(seen.size() - seen.indexOf(banks));
    }
}
