package year2015;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    private static final List<Integer> INPUT = List.of(1, 1, 1, 3, 1, 2, 2, 1, 1, 3);

    public static void main(String[] args) {

        //Part one
        List<Integer> sequence = INPUT;
        for (int i = 0; i < 40; i++) {
            sequence = lookAndSay(sequence);
        }

        System.out.println(sequence.size());

        //Part two
        sequence = INPUT;
        for (int i = 0; i < 50; i++) {
            sequence = lookAndSay(sequence);
        }

        System.out.println(sequence.size());
    }

    private static int getSizeOfSequence(List<Integer> input, int number, int index) {
        int size = 0;

        for (int i = index; i < input.size(); i++) {
            if (input.get(i) == number) {
                size++;
            } else {
                break;
            }
        }

        return size;
    }

    private static List<Integer> lookAndSay(List<Integer> input) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < input.size();) {
            int number = input.get(i);
            int sizeOfSequence = getSizeOfSequence(input, number, i);

            result.add(sizeOfSequence);
            result.add(number);

            i += sizeOfSequence;
        }

        return result;
    }
}
