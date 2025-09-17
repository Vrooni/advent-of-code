package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;

public class Day25 {

    public static void main(String[] args) throws IOException {
        //Part one
        String input = Utils.read(Path.of("src/year2015/files/25.txt"));
        String[] splitLine = input.substring(input.indexOf("row")).split(", ");

        int row = Integer.parseInt(splitLine[0].replace("row ", ""));
        int column = Integer.parseInt(splitLine[1]
                .replace("column ", "")
                .replace(".", "")
                .replace("\n", "")
        );

        int currentRow = 1;
        int currentColumn = 1;
        int value = 20151125;

        do {
            if (currentRow - 1 == 0) {
                currentRow = currentColumn + 1;
                currentColumn = 1;
            } else {
                currentRow--;
                currentColumn++;
            }

            long result = (value * 252533L) % 33554393;
            value = (int) result;
        } while (currentRow != row || currentColumn != column);

        System.out.println(value);
    }
}
