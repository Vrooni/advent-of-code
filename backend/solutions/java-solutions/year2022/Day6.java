package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class Day6 {
    public static void main(String[] args) throws IOException {
        //Part one
        String input = Utils.read(Path.of("src/year2022/files/06.txt"));

        for (int i = 0; i < input.length()-3; i++) {
            char c1 = input.charAt(i);
            char c2 = input.charAt(i+1);
            char c3 = input.charAt(i+2);
            char c4 = input.charAt(i+3);

            if (c1 != c2 && c1 != c3 && c1 != c4
                    && c2 != c3 && c2 != c4
                    && c3 != c4) {
                System.out.println(i+4);
                break;
            }
        }

        //Part two
        for (int i = 0; i < input.length()-13; i++) {
            boolean allDistinct = true;
            char[] fourteenValues = Arrays.copyOfRange(input.toCharArray(), i, i+14);

            Magic: for (int j = 0; j < 14; j++) {
                for (int k = j+1; k < 14; k++) {
                    if (fourteenValues[j] == fourteenValues[k]) {
                        allDistinct = false;
                        break Magic;
                    }
                }
            }

            if (allDistinct) {
                System.out.println(i+14);
                System.exit(0);
            }

        }
    }
}
