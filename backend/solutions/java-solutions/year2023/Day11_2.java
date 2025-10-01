import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day11_2 {
    record Point(long x, long y) {
    }

    record Combination(Point g1, Point g2) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        List<List<Character>> universe = readInput(lines);
        List<Point> galaxies = getGalaxies(universe, 999999);
        List<Combination> combinations = getCombinations(galaxies);

        long result = 0;

        for (Combination combination : combinations) {
            result += Math.abs(combination.g1.x - combination.g2.x) + Math.abs(combination.g1.y - combination.g2.y);
        }

        System.out.println(result);
    }

    private static List<List<Character>> readInput(List<String> lines) {
        List<List<Character>> universe = new ArrayList<>();

        for (String line : lines) {
            List<Character> universeLine = new ArrayList<>();

            for (char c : line.toCharArray()) {
                universeLine.add(c);
            }

            universe.add(universeLine);
        }

        return universe;
    }

    private static List<Point> getGalaxies(List<List<Character>> universe, int expansion) {
        List<Integer> linesToExpand = new ArrayList<>();
        List<Integer> columnsToExpand = new ArrayList<>();

        for (int i = 0; i < universe.size(); i++) {
            if (universe.get(i).stream().allMatch(c -> c == '.')) {
                linesToExpand.add(i);
            }

            int finalI = i;
            if (universe.stream().allMatch(line -> line.get(finalI) == '.')) {
                columnsToExpand.add(i);
            }
        }

        List<Point> galaxies = new ArrayList<>();

        for (int y = 0; y < universe.size(); y++) {
            for (int x = 0; x < universe.get(0).size(); x++) {
                if (universe.get(y).get(x) == '#') {
                    int finalX = x;
                    int finalY = y;

                    long xPos = x + (long) columnsToExpand.stream().filter(i -> i < finalX).toList().size() * expansion;
                    long yPos = y + (long) linesToExpand.stream().filter(i -> i < finalY).toList().size() * expansion;

                    galaxies.add(new Point(xPos, yPos));
                }
            }
        }

        return galaxies;
    }

    private static List<Combination> getCombinations(List<Point> galaxies) {
        List<Combination> combinations = new ArrayList<>();

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                combinations.add(new Combination(galaxies.get(i), galaxies.get(j)));
            }
        }

        return combinations;
    }
}
