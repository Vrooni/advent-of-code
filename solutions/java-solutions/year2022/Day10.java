package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day10 {
    //Part one
    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/10.txt"));

        int x = 1;
        int cycle = 0;
        int signalStrength = 0;

        for (String line : lines) {
            cycle++;
            signalStrength += signalStrength(x, cycle);

            String[] splittedLine = line.split(" ");
            if (splittedLine[0].equals("addx")) {
                cycle++;
                signalStrength += signalStrength(x, cycle);
                x += Integer.parseInt(splittedLine[1]);
            }
        }

        System.out.println(signalStrength);

        //Part two
        x = 1;
        cycle = 0;

        for (String line : lines) {
            cycle++;
            draw(x, cycle);

            String[] splittedLine = line.split(" ");
            if (splittedLine[0].equals("addx")) {
                cycle++;
                draw(x, cycle);
                x += Integer.parseInt(splittedLine[1]);
            }
        }
    }

    private static int signalStrength(int x, int cycle) {
        if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
            return cycle * x;
        }

        return 0;
    }

    private static void draw(int x, int cycle) {
        int i = (cycle-1)%40;
        if (Math.abs(i - x) <= 1) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }

        if (i == 39) {
            System.out.println();
        }
    }
}
