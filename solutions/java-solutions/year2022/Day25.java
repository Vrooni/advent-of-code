package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day25 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/25.txt"));
        long result = getResult(lines);

        System.out.println(getSnafuResult(result));
    }

    private static long getResult(List<String> lines) {
        long result = 0;

        for (String line : lines) {
            long decimal = 0;
            int power = line.length()-1;

            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    decimal += Character.getNumericValue(c) * Math.pow(5, power);
                } else {
                    if (c == '-') {
                        decimal += -1 * Math.pow(5, power);
                    } else if (c == '=') {
                        decimal += -2 * Math.pow(5, power);
                    }
                }

                power--;
            }

            result += decimal;
        }

        return result;
    }

    private static String getSnafuResult(long decimal) {
        int power = getStartPower(decimal);
        List<Integer> nativeSnafu = getNativeResult(decimal, power);

        return convertToSnafu(nativeSnafu);
    }

    private static int getStartPower(long decimal) {
        int power = 0;

        while (true) {
            if (Math.pow(5, power+1) > decimal && decimal >= Math.pow(5, power)) {
                break;
            }

            power++;
        }

        return power;
    }

    private static List<Integer> getNativeResult(long decimal, int power) {
        List<Integer> nativeSnafu = new ArrayList<>();

        for (int i = 0; i <= power; i++) {
            nativeSnafu.add(0);
        }

        for (int i = power; i >= 0; i--) {
            int temp = (int) (decimal / Math.pow(5, i));
            decimal -= temp * Math.pow(5, i);

            nativeSnafu.set(i, temp);
        }

        for (int i = 0; i <= power; i++) {
            if (nativeSnafu.get(i) > 2) {

                if (nativeSnafu.size() == i + 1) {
                    nativeSnafu.add(1);
                } else {
                    nativeSnafu.set(i+1, nativeSnafu.get(i+1)+1);
                }

                nativeSnafu.set(i, nativeSnafu.get(i)-5);
            }
        }

        return nativeSnafu;
    }

    private static String convertToSnafu(List<Integer> nativeSnafu) {
        StringBuilder builder = new StringBuilder();

        for (int i = nativeSnafu.size()-1; i >= 0; i--) {
            if (nativeSnafu.get(i) >= 0) {
                builder.append(nativeSnafu.get(i));
            } else if (nativeSnafu.get(i) == -1) {
                builder.append("-");
            } else if (nativeSnafu.get(i) == -2) {
                builder.append("=");
            }
        }

        return builder.toString();
    }
}
