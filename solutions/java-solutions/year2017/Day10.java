package year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day10 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("10.txt");
        int[] numbers = IntStream.rangeClosed(0, 255).toArray();

        List<Integer> lengths = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();

        int currentPosition = 0;
        int skipSize = 0;

        for (Integer length : lengths) {
            for (int i = 0; i < length/2; i++) {
                int index1 = (currentPosition + i) % numbers.length;
                int index2 = (currentPosition + length - i - 1) % numbers.length;

                int temp = numbers[index1];
                numbers[index1] = numbers[index2];
                numbers[index2] = temp;
            }

            currentPosition = (currentPosition + length + skipSize++) % numbers.length;
        }

        System.out.println(numbers[0] * numbers[1]);


        //Part two
        byte[] bytes = input.getBytes();
        int[] intArray = new int[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            intArray[i] = bytes[i] & 0xFF;
        }

        numbers = IntStream.rangeClosed(0, 255).toArray();
        lengths = new ArrayList<>(Arrays.stream(intArray).boxed().toList());
        lengths.addAll(List.of(17, 31, 73, 47, 23));

        currentPosition = 0;
        skipSize = 0;

        for (int j = 0; j < 64; j++) {
            for (Integer length : lengths) {
                for (int i = 0; i < length/2; i++) {
                    int index1 = (currentPosition + i) % numbers.length;
                    int index2 = (currentPosition + length - i - 1) % numbers.length;

                    int temp = numbers[index1];
                    numbers[index1] = numbers[index2];
                    numbers[index2] = temp;
                }

                currentPosition = (currentPosition + length + skipSize++) % numbers.length;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numbers.length; i += 16) {
            int xor = numbers[i];

            for (int j = 1; j < 16; j++) {
                xor = xor ^ numbers[i+j];
            }

            String hexXor = Integer.toHexString(xor);
            hexXor = hexXor.length() == 1 ? "0" + hexXor : hexXor;
            result.append(hexXor);
        }

        System.out.println(result);
    }
}
