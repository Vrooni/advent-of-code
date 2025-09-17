package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private static final int TIMES = 303;

    public static void main(String[] args) {
        //Part one
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int index = 0;

        for (int i = 1; i <= 2017; i++) {
            index = (index + TIMES) % i + 1;

            if (index >= buffer.size()) {
                buffer.add(i);
            } else {
                buffer.add(index, i);
            }
        }

        System.out.println(buffer.get(index+1));


        //Part two
        index = 0;
        int result = 0;

        for (int i = 1; i <= 50_000_000; i++) {
            index = (index + TIMES) % i + 1;
            if (index == 1) {
                result = i;
            }
        }

        System.out.println(result);
    }
}
