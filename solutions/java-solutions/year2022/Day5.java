package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    static class Move {
        final int amount;
        final int from;
        final int to;

        Move(String line) {
            line = line.replaceAll(" ","");
            line = line.replace("move", "");
            String[] temp1 = line.split("from");
            String[] temp2 = temp1[1].split("to");

            this.amount = Integer.parseInt(temp1[0]);
            this.from = Integer.parseInt(temp2[0]) - 1;
            this.to = Integer.parseInt(temp2[1]) - 1;
        }

        public void move(List<List<Character>> crates) {
            for (int i = 0; i < amount; i++) {
                crates.get(to).add(0, crates.get(from).get(0));
                crates.get(from).remove(0);
            }
        }

        public void moveInOne(List<List<Character>> crates) {
            for (int i = amount - 1; i >= 0; i--) {
                crates.get(to).add(0, crates.get(from).get(i));
                crates.get(from).remove(i);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/05.txt"));
        List<List<Character>> crates = new ArrayList<>();

        boolean cratesReady = false;
        for (String line : lines) {
            if (!cratesReady) {
                cratesReady = addCrates(line, crates);
            }

            if (line.startsWith("move")) {
                Move move = new Move(line);
                move.move(crates);
            }
        }

        for (List<Character> crate : crates) {
            System.out.print(crate.get(0));
        }
        System.out.println();

        //Part two
        crates = new ArrayList<>();

        cratesReady = false;
        for (String line : lines) {
            if (!cratesReady) {
                cratesReady = addCrates(line, crates);
            }

            if (line.startsWith("move")) {
                Move move = new Move(line);
                move.moveInOne(crates);
            }
        }

        for (List<Character> crate : crates) {
            System.out.print(crate.get(0));
        }
        System.out.println();
    }

    private static boolean addCrates(String line, List<List<Character>> crates) {
        for (int i = 1; i < line.length(); i+=4) {
            char stack = line.charAt(i);

            if (stack  == '1') {
                return true;
            } else if (stack == ' ') {
                continue;
            }

            while (crates.size() <= i/4) {
                crates.add(new ArrayList<>());
            }
            crates.get(i/4).add(line.charAt(i));
        }

        return false;
    }
}
