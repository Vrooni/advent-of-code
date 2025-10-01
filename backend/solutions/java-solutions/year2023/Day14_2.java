import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day14_2 {
    private static List<List<String>> history = new ArrayList<>();
    private static final int TIMES = 1000000000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int cycleIndex = findCycle(lines);

        for (int i = 0; i < cycleIndex; i++) {
            history.remove(0);
        }

        int endIndex = (TIMES - cycleIndex) % history.size() - 1;
        endIndex = endIndex == -1 ? history.size() - 1 : endIndex; // Underflow

        int result = getResult(history.get(endIndex));
        System.out.println(result);
    }

    private static void roll(List<String> lines) {
        for (int x = 0; x < lines.get(0).length(); x++) {
            for (int y = 0; y < lines.size(); y++) {

                // roll to north
                if (lines.get(y).charAt(x) == 'O') {
                    for (int y2 = y - 1; y2 >= 0; y2--) {
                        if (lines.get(y2).charAt(x) != '.') {
                            lines.set(y, replace(lines.get(y), x, '.'));
                            lines.set(y2 + 1, replace(lines.get(y2 + 1), x, 'O'));
                            break;
                        } else if (y2 == 0) {
                            lines.set(y, replace(lines.get(y), x, '.'));
                            lines.set(y2, replace(lines.get(y2), x, 'O'));
                            break;
                        }
                    }
                }
            }
        }
    }

    private static List<String> turn(List<String> lines) {
        List<String> turnedLines = new ArrayList<>();

        for (int x = 0; x < lines.get(0).length(); x++) {
            StringBuilder s = new StringBuilder();
            for (int y = lines.size() - 1; y >= 0; y--) {
                s.append(lines.get(y).charAt(x));
            }

            turnedLines.add(s.toString());
        }

        return turnedLines;
    }

    private static int findCycle(List<String> lines) {
        for (int i = 0; i < TIMES; i++) {
            if (history.contains(lines)) {
                return history.indexOf(lines);
            }

            // ignore 0 step
            if (i != 0) {
                history.add(new ArrayList<>(lines));
            }

            for (int j = 0; j < 4; j++) {
                roll(lines);
                lines = turn(lines);
            }
        }

        return -1;
    }

    private static int getResult(List<String> lines) {
        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            int times = lines.size() - i;
            result += times * lines.get(i).replaceAll("\\.", "").replaceAll("#", "").length();
        }

        return result;
    }

    private static String replace(String s, int index, char c) {
        return s.substring(0, index) + c + s.substring(index + 1);
    }
}
