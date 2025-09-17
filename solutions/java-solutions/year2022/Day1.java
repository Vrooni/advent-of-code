package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/01.txt"));

        List<Integer> calories = new ArrayList<>();
        int calorie = 0;
        for (String line : lines) {
            if (line.equals("")) {
                calories.add(calorie);
                calorie = 0;
            } else {
                calorie += Integer.parseInt(line);
            }
        }

        Collections.sort(calories);

        System.out.println(calories.get(calories.size()-1));
        //Part two
        System.out.println(calories.get(calories.size()-1) + calories.get(calories.size()-2) + calories.get(calories.size()-3));

    }
}
