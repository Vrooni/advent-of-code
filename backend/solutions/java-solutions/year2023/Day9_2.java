import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day9_2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int result = 0;

        for (String line : lines) {
            List<Integer> input = readInput(line);
            List<List<Integer>> history = readHistory(input);
            history.get(history.size() - 1).add(0, 0);

            for (int i = history.size() - 2; i >= 0; i--) {
                List<Integer> list = history.get(i);
                list.add(0, history.get(i).get(0) - history.get(i + 1).get(0));
            }

            result += history.get(0).get(0);
        }

        System.out.println(result);
    }

    public static List<Integer> readInput(String line) {
        List<Integer> input = new ArrayList<>();

        for (String number : line.split(" ")) {
            input.add(Integer.parseInt(number));
        }

        return input;
    }

    public static List<List<Integer>> readHistory(List<Integer> input) {
        List<List<Integer>> history = new ArrayList<>();
        history.add(input);

        while (true) {
            List<Integer> mappedList = new ArrayList<>();

            for (int i = 0; i < input.size() - 1; i++) {
                mappedList.add(input.get(i + 1) - input.get(i));
            }

            input = mappedList;
            history.add(mappedList);

            if (mappedList.stream().allMatch(number -> number == 0)) {
                break;
            }
        }

        return history;
    }
}
