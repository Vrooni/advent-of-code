package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day15 {

    static class Disc {
        final int positions;
        int position;

        public Disc(int positions, int position) {
            this.positions = positions;
            this.position = position;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("15.txt");
        List<Disc> discs = new ArrayList<>();

        for (String line : input) {
            String[] splitLine = line.replace(".", "").split(" ");
            discs.add(new Disc(
                    Integer.parseInt(splitLine[3]),
                    Integer.parseInt(splitLine[11])
            ));
        }

        for (int t = 0; true; t++) {
            if (goesThrew(t, discs)) {
                System.out.println(t);
                break;
            }
        }


        //Part two
        discs.add(new Disc(11, 0));
        for (int t = 0; true; t++) {
            if (goesThrew(t, discs)) {
                System.out.println(t);
                break;
            }
        }
    }

    private static boolean goesThrew(int t, List<Disc> discs) {
        for (int i = 0; i < discs.size(); i++) {
            Disc disc = discs.get(i);
            int turns = t + i + 1;

            if ((disc.position + turns) % disc.positions != 0) {
                return false;
            }
        }

        return true;
    }
}
