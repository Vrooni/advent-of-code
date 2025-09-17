package year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        //Part one
        List<String> lines = Utils.readLines("02.txt");
        int result = 0;

        for (String line : lines) {
            String[] splitLine = line.split("\t");
            List<Integer> numbers = new ArrayList<>(Arrays.stream(splitLine).map(Integer::parseInt).toList());

            numbers.sort(Integer::compareTo);
            result += numbers.get(numbers.size()-1) - numbers.get(0);
        }

        System.out.println(result);


        //Part two
        result = 0;

        for (String line : lines) {
            String[] splitLine = line.split("\t");
            List<Integer> numbers = new ArrayList<>(Arrays.stream(splitLine).map(Integer::parseInt).toList());

            for (int i = 0; i < numbers.size() - 1; i++) {
                for (int j = i+1; j < numbers.size() ; j++) {
                    float res = numbers.get(i) > numbers.get(j)
                            ? (float) numbers.get(i) / numbers.get(j)
                            : (float) numbers.get(j) / numbers.get(i);

                    if (res % 1 == 0) {
                        result += res;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
