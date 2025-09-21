package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3 {
    record Point(int x, int y) {}

    public static void main(String[] args) throws IOException {

        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/03.txt"));
        Map<Point, String> numbers = new HashMap<>();
        List<Point> symbols = new ArrayList<>();

        int sum = 0;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                String s = line.substring(x, x+1);

                if (Utils.isNumeric(s)) {
                    int end = findEnd(line, x);
                    numbers.put(new Point(x, y), line.substring(x, end + 1));

                    x = end;
                } else if (!s.equals(".")) {
                    symbols.add(new Point(x, y));
                }
            }
        }

        for (Map.Entry<Point, String> entrySet : numbers.entrySet()) {
            Point point = entrySet.getKey();
            String number = entrySet.getValue();

            outer: for (int y = point.y - 1; y <= point.y + 1 ; y++) {
                for (int x = point.x - 1; x <= point.x + number.length() ; x++) {
                    if (symbols.contains(new Point(x, y))) {
                        sum += Integer.parseInt(number);
                        break outer;
                    }
                }
            }
        }

        System.out.println(sum);


        //Part two
        Map<Point, List<Integer>> gears = new HashMap<>();
        sum = 0;

        symbols = symbols.stream()
                .filter(point -> {
                    String symbol = lines.get(point.y).substring(point.x, point.x + 1);
                    return symbol.equals("*");
                })
                .collect(Collectors.toList());


        for (Map.Entry<Point, String> entrySet : numbers.entrySet()) {
            Point point = entrySet.getKey();
            String number = entrySet.getValue();

            for (int y = point.y - 1; y <= point.y + 1 ; y++) {
                for (int x = point.x - 1; x <= point.x + number.length() ; x++) {
                    if (symbols.contains(new Point(x, y))) {
                        gears.computeIfAbsent(new Point(x, y), k -> new ArrayList<>()).add(Integer.parseInt(number));
                    }
                }
            }
        }

        for (List<Integer> gearNumbers : gears.values()) {
            if (gearNumbers.size() > 1) {
                sum += Utils.mul(gearNumbers);
            }
        }

        System.out.println(sum);
    }

    private static int findEnd(String line, int x) {
        if (x >= line.length() - 1 || !Utils.isNumeric(line.substring(x + 1, x + 2))) {
            return x;
        }

        return findEnd(line, x + 1);
    }
}
