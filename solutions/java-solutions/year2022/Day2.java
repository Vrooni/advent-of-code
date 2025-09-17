package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day2 {
    public static void main(String[] args) throws IOException {
        //Part one
        Map<String, Map<String, Integer>> myMovePoints = new HashMap<>();
        Map<String, Integer> movePoints;

        //X
        movePoints = Map.of("C", 6 + 1, "A", 3 + 1, "B", 0 + 1);
        myMovePoints.put("X", movePoints);

        //Y
        movePoints = Map.of("A", 6 + 2, "B", 3 + 2, "C", 0 + 2);
        myMovePoints.put("Y", movePoints);

        //Z
        movePoints = Map.of("B", 6 + 3, "C", 3 + 3, "A", 0 + 3);
        myMovePoints.put("Z", movePoints);


        List<String> lines = Utils.readLines(Path.of("src/year2022/files/02.txt"));

        int points = 0;
        for (String line : lines) {
            String[] moves = line.split(" ");

            String otherMove = moves[0];
            String myMove = moves[1];

            points += myMovePoints.get(myMove).get(otherMove);
        }

        System.out.println(points);

        //Part two
        //A
        movePoints = Map.of("X", 3 + 0, "Y", 1 + 3, "Z", 2 + 6);
        myMovePoints.put("A", movePoints);

        //B
        movePoints = Map.of("X", 1 + 0, "Y", 2 + 3, "Z", 3 + 6);
        myMovePoints.put("B", movePoints);

        //C
        movePoints = Map.of("X", 2 + 0, "Y", 3 + 3, "Z", 1 + 6);
        myMovePoints.put("C", movePoints);

        points = 0;
        for (String line : lines) {
            String[] moves = line.split(" ");

            String otherMove = moves[0];
            String myWinningStatus = moves[1];

            points += myMovePoints.get(otherMove).get(myWinningStatus);
        }

        System.out.println(points);
    }
}
