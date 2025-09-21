package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/04.txt"));

        int fullyOverlaps = 0;
        for (String line : lines) {
            String[] cleanRanges = line.split(",");

            int[] range1 = Arrays.stream(cleanRanges[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] range2 = Arrays.stream(cleanRanges[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if (range1[0] <= range2[0] && range1[1] >= range2[1]
                    || range1[0] >= range2[0] && range1[1] <= range2[1]) {
                fullyOverlaps++;
            }
        }

        System.out.println(fullyOverlaps);

        //Part two
        int overlaps = 0;
        for (String line : lines) {
            String[] cleanRanges = line.split(",");

            int[] range1 = Arrays.stream(cleanRanges[0].split("-")).mapToInt(Integer::parseInt).toArray();
            int[] range2 = Arrays.stream(cleanRanges[1].split("-")).mapToInt(Integer::parseInt).toArray();

            if (isBetween(range1[0], range2) || isBetween(range1[1], range2)
                    || isBetween(range2[0], range1) || isBetween(range2[1], range1)) {
                overlaps++;
            }
        }

        System.out.println(overlaps);
    }

    private static boolean isBetween(int value, int[] range) {
        return value >= range[0] && value <= range[1];
    }
}
