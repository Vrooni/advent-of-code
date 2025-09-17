package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day18 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("18.txt");
        List<List<Boolean>> tiles = new ArrayList<>();

        List<Boolean> firstRow = new ArrayList<>();
        for (char c : input.toCharArray()) {
            firstRow.add(c == '^');
        }
        tiles.add(firstRow);

        for (int i = 0; i < 40 - 1; i++) {
            List<Boolean> rowBefore = tiles.get(i);
            List<Boolean> thisRow = new ArrayList<>();

            for (int j = 0; j < rowBefore.size(); j++) {
                boolean left = j != 0 && rowBefore.get(j - 1);
                boolean middle = rowBefore.get(j);
                boolean right = j != rowBefore.size() - 1 && rowBefore.get(j + 1);

                thisRow.add(left && middle && !right
                                || !left && middle && right
                                || left && !middle && !right
                                || !left && !middle && right
                );
            }

            tiles.add(thisRow);
        }

        System.out.println(tiles.stream().mapToInt(row -> (int) row.stream().filter(tile -> !tile).count()).sum());


        //Part two
        for (int i = 39; i < 400000 - 1; i++) {
            List<Boolean> rowBefore = tiles.get(i);
            List<Boolean> thisRow = new ArrayList<>();

            for (int j = 0; j < rowBefore.size(); j++) {
                boolean left = j != 0 && rowBefore.get(j - 1);
                boolean middle = rowBefore.get(j);
                boolean right = j != rowBefore.size() - 1 && rowBefore.get(j + 1);

                thisRow.add(left && middle && !right
                                || !left && middle && right
                                || left && !middle && !right
                                || !left && !middle && right
                );
            }

            tiles.add(thisRow);
        }

        System.out.println(tiles.stream().mapToInt(row -> (int) row.stream().filter(tile -> !tile).count()).sum());
    }
}
