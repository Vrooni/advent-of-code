package year2016;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Utils {
    public static List<String> readLines(String file) {
        try {
            Path path = Path.of("src/year2016/files/" + file);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String file) {
        try {
            Path path = Path.of("src/year2016/files/" + file);
            return Files.readString(path, StandardCharsets.UTF_8).replace("\n", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int countMatches(String s, String c){
        return s.length() - s.replace(c, "").length();
    }

    public static int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base));
    }
}


//package year2016;
//
//        import java.util.Arrays;
//
//public class Day19 {
//    private static final int COUNT = 3017957;
//
//    public static void main(String[] args) {
//        /**
//         * PATTERN:
//         *  1 -> 1
//         *  2 -> 1
//         *  3 -> 3
//         *  4 -> 1
//         *  5 -> 3
//         *  6 -> 5
//         *  7 -> 7
//         *  8 -> 1
//         *  9 -> 3
//         * 10 -> 5
//         * 11 -> 7
//         * 12 -> 9
//         * 13 -> 11
//         * 14 -> 13
//         * 15 -> 15
//         * 16 -> 1
//         *
//         * any potenz of 2 leads resets to 1, else adding 2
//         * count -> 1 + 2*(count - 2^a) (where a is as high as possible)
//         */
//        //Part one
//        int a = year2017.Utils.log(COUNT, 2);
//        System.out.println(1 + 2*(COUNT - (int) Math.pow(2, a)));
//
//
//        //Part two
//        /**
//         * PATTERN:
//         * 1: 1
//         * 2: 1
//         * 3: 3
//         * 4: 1
//         * 5: 2
//         * 6: 3
//         * 7: 5
//         * 8: 7
//         * 9: 9
//         * 10: 1
//         * 11: 2
//         * 12: 3
//         * 13: 4
//         * 14: 5
//         * 15: 6
//         * 16: 7
//         * 17: 8
//         * 18: 9
//         * 19: 11
//         * 20: 13
//         * 21: 15
//         * 22: 17
//         * 23: 19
//         * 24: 21
//         * 25: 23
//         * 26: 25
//         * 27: 27
//         * 28: 1
//         * 29: 2
//         * 30: 3
//         * Any potenz of 3 (+1) leads resets to 1, else adding 1 before half way or 2 after half way
//         * formel == ???
//         */
//
//        a = year2017.Utils.log(COUNT-1, 3);
//        int power = (int) Math.pow(3, a);
//        System.out.println(COUNT - power);
//    }
//}
