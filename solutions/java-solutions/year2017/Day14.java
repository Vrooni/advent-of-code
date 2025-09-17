package year2017;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class Day14 {
    private static final String KEY = "amgozmfv";

    public static void main(String[] args) {
        //Part one
        int used = 0;

        for (int i = 0; i < 128; i++) {
            String hash = getKnotHash(KEY + "-" + i);
            String binaryHash = new BigInteger(hash, 16).toString(2);
            used += binaryHash.chars().filter(c -> c == '1').count();
        }

        System.out.println(used);


        //Part two
        int[][] disk = new int[128][128];

        for (int i = 0; i < 128; i++) {
            String hash = getKnotHash(KEY + "-" + i);
            StringBuilder binaryHash = new StringBuilder(new BigInteger(hash, 16).toString(2));

            while (binaryHash.length() != 128) {
                binaryHash.insert(0, "0");
            }

            disk[i] = binaryHash.chars().map(Character::getNumericValue).toArray();
        }

        int[][] groups = new int[128][128];
        int group = 0;

        for (int y = 0; y < disk.length; y++) {
            for (int x = 0; x < disk[0].length; x++) {

                if (disk[y][x] == 1 && groups[y][x] == 0) {
                    markGroup(x, y, disk, groups, ++group);
                }
            }
        }

        System.out.println(group);
    }

    private static String getKnotHash(String key) {
        byte[] bytes = key.getBytes();
        int[] intArray = new int[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            intArray[i] = bytes[i] & 0xFF;
        }

        int[] numbers = IntStream.rangeClosed(0, 255).toArray();
        List<Integer> lengths = new ArrayList<>(Arrays.stream(intArray).boxed().toList());
        lengths.addAll(List.of(17, 31, 73, 47, 23));

        int currentPosition = 0;
        int skipSize = 0;

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

        return result.toString();
    }

    private static void markGroup(int x, int y, int[][] disk, int[][] groups, int group) {
        groups[y][x] = group;

        int xn = x-1;
        int yn = y;
        if (xn >= 0 && disk[yn][xn] == 1 && groups[yn][xn] == 0) {
            markGroup(xn, yn, disk, groups, group);
        }

        xn = x+1;
        yn = y;
        if (xn < 128 && disk[yn][xn] == 1 && groups[yn][xn] == 0) {
            markGroup(xn, yn, disk, groups, group);
        }

        xn = x;
        yn = y-1;
        if (yn >= 0 && disk[yn][xn] == 1 && groups[yn][xn] == 0) {
            markGroup(xn, yn, disk, groups, group);
        }

        xn = x;
        yn = y+1;
        if (yn < 128 && disk[yn][xn] == 1 && groups[yn][xn] == 0) {
            markGroup(xn, yn, disk, groups, group);
        }
    }
}
