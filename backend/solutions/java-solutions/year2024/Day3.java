package year2024;

import year2024.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private record Toggle(int index, boolean on) { }

    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("03.txt");
        int result = 0;

        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        System.out.println(result);


        //Part two
        List<Boolean> toggles = new ArrayList<>();
        boolean toggle = true;

        for (int i = 0; i < input.toCharArray().length; i++) {
            if (input.startsWith("do()", i)) {
                toggle = true;
            } else if (input.startsWith("don't()", i)) {
                toggle = false;
            }

            toggles.add(toggle);
        }

        result = 0;
        matcher = pattern.matcher(input);

        while (matcher.find()) {
            int index = matcher.start();

            if (toggles.get(index)) {
                result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        System.out.println(result);
    }
}
