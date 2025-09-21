package year2018;

import year2018.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 {
    public static void main(String[] args) {
        //Part one
        List<Integer> changes = Utils.readLines("01.txt").stream().map(Integer::parseInt).toList();
        System.out.println(changes.stream().mapToInt(i -> i).sum());


        //Part two
        Set<Integer> frequencies = new HashSet<>();
        frequencies.add(0);
        int frequency = 0;

        while (true) {
            for (Integer change : changes) {

                frequency = frequency + change;
                if (frequencies.contains(frequency)) {
                    System.out.println(frequency);
                    return;
                }
                frequencies.add(frequency);
            }
        }
    }
}
