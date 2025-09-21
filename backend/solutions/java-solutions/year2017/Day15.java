package year2017;

import java.util.List;

public class Day15 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("15.txt");

        long aValue = Integer.parseInt(input.get(0).split(" ")[4]);
        long bValue = Integer.parseInt(input.get(1).split(" ")[4]);

        int count = 0;

        for (int i = 0; i < 40000000; i++) {
            aValue = (aValue * 16807) % 2147483647;
            bValue = (bValue * 48271) % 2147483647;

            if ((aValue & 0xFFFF) == (bValue & 0xFFFF)) {
                count++;
            }
        }

        System.out.println(count);


        //Part two
        aValue = Integer.parseInt(input.get(0).split(" ")[4]);
        bValue = Integer.parseInt(input.get(1).split(" ")[4]);

        count = 0;
        for (int i = 0; i < 5000000; i++) {
            aValue = getNextValue(aValue,16807, 4);
            bValue = getNextValue(bValue,48271, 8);

            if ((aValue & 0xFFFF) == (bValue & 0xFFFF)) {
                count++;
            }
        }

        System.out.println(count);
    }

    private static long getNextValue(long value, int mul, int mod) {
        while (true) {
            value = (value * mul) % 2147483647;

            if (value % mod == 0) {
                return value;
            }
        }
    }
}
