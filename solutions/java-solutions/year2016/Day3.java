package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    public static void main(String[] args) {
        //Part one
        List<String> lengthsLines = Utils.readLines("03.txt");
        int possible = 0;

        for (String lengths : lengthsLines) {
            int length1 = Integer.parseInt(lengths.substring(0, 5).replaceAll(" ", ""));
            int length2 = Integer.parseInt(lengths.substring(5, 10).replaceAll(" ", ""));
            int length3 = Integer.parseInt(lengths.substring(10).replaceAll(" ", ""));

            if (length1 + length2 > length3 && length1 + length3 > length2 && length2 + length3 > length1) {
                possible++;
            }
        }

        System.out.println(possible);


        //Part two
        List<List<Integer>> allLengths = new ArrayList<>();
        possible = 0;

        for (String lengths : lengthsLines) {
            int length1 = Integer.parseInt(lengths.substring(0, 5).replaceAll(" ", ""));
            int length2 = Integer.parseInt(lengths.substring(5, 10).replaceAll(" ", ""));
            int length3 = Integer.parseInt(lengths.substring(10).replaceAll(" ", ""));

            allLengths.add(List.of(length1, length2, length3));
        }

        for (int i = 0; i < allLengths.get(0).size(); i++) {
            for (int j = 0; j < allLengths.size(); j += 3) {
                int length1 = allLengths.get(j).get(i);
                int length2 = allLengths.get(j+1).get(i);
                int length3 = allLengths.get(j+2).get(i);

                if (length1 + length2 > length3 && length1 + length3 > length2 && length2 + length3 > length1) {
                    possible++;
                }
            }
        }

        System.out.println(possible);
    }
}
