package year2017;

import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("05.txt");
        int[] jumps = input.stream().mapToInt(Integer::parseInt).toArray();

        int steps;
        int index = 0;
        int lastIndex = 0;

        for (steps = 0; index >= 0 && index < jumps.length; steps++) {
            index += jumps[index];
            jumps[lastIndex]++;
            lastIndex = index;
        }

        System.out.println(steps);


        //Part two
        jumps = input.stream().mapToInt(Integer::parseInt).toArray();

        index = 0;
        lastIndex = 0;

        for (steps = 0; index >= 0 && index < jumps.length; steps++) {
            int offset = jumps[index];
            index += offset;

            jumps[lastIndex] += offset >= 3 ? -1 : 1;
            lastIndex = index;
        }

        System.out.println(steps);
    }
}
