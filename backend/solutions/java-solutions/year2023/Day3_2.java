import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3_2 {
    record Point(int x, int y) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        Map<Point, String> numbers = new HashMap<>();
        List<Point> symbols = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                String s = line.substring(x, x + 1);

                if (isNumeric(s)) {
                    int end = findEnd(line, x);
                    numbers.put(new Point(x, y), line.substring(x, end + 1));

                    x = end;
                } else if (!s.equals(".")) {
                    symbols.add(new Point(x, y));
                }
            }
        }

        Map<Point, List<Integer>> gears = new HashMap<>();
        int sum = 0;

        symbols = symbols.stream()
                .filter(point -> {
                    String symbol = lines.get(point.y).substring(point.x, point.x + 1);
                    return symbol.equals("*");
                })
                .collect(Collectors.toList());

        for (Map.Entry<Point, String> entrySet : numbers.entrySet()) {
            Point point = entrySet.getKey();
            String number = entrySet.getValue();

            for (int y = point.y - 1; y <= point.y + 1; y++) {
                for (int x = point.x - 1; x <= point.x + number.length(); x++) {
                    if (symbols.contains(new Point(x, y))) {
                        gears.computeIfAbsent(new Point(x, y), _ -> new ArrayList<>()).add(Integer.parseInt(number));
                    }
                }
            }
        }

        for (List<Integer> gearNumbers : gears.values()) {
            if (gearNumbers.size() > 1) {
                sum += mul(gearNumbers);
            }
        }

        System.out.println(sum);
    }

    private static int findEnd(String line, int x) {
        if (x >= line.length() - 1 || !isNumeric(line.substring(x + 1, x + 2))) {
            return x;
        }

        return findEnd(line, x + 1);
    }

    private static int mul(List<Integer> numbers) {
        int result = 1;
        for (int number : numbers) {
            result *= number;
        }

        return result;
    }

    private static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
