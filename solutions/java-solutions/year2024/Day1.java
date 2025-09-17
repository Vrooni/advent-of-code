package year2024;

import year2024.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("01.txt");

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (String line : input) {
            list1.add(Integer.parseInt(line.substring(0, line.indexOf(" "))));
            list2.add(Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1)));
        }

        Collections.sort(list1);
        Collections.sort(list2);

        int result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += Math.abs(list1.get(i) - list2.get(i));
        }

        System.out.println(result);


        //Part two
        result = 0;
        for (Integer locationId : list1) {
            result += locationId * Collections.frequency(list2, locationId);
        }

        System.out.println(result);
    }
}
