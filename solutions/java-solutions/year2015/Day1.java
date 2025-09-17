package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;

public class Day1 {
    public static void main(String[] args) throws IOException {
        //Part one
        String moves = Utils.read(Path.of("src/year2015/files/01.txt"));

        int floor = 0;
        for (char move : moves.toCharArray()) {
            if (move == '(') {
                floor += 1;
            } else if (move == ')') {
                floor -= 1;
            }
        }

        System.out.println(floor);

        //Part two
        floor = 0;
        for (int i = 0; i < moves.length(); i++) {
            if (floor == -1) {
                System.out.println(i);
                System.exit(0);
            }

            if (moves.charAt(i) == '(') {
                floor += 1;
            } else if (moves.charAt(i) == ')') {
                floor -= 1;
            }
        }
    }
}
