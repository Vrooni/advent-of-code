package year2015;

import org.json.JSONArray;
import org.json.JSONObject;
import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(String[] args) throws IOException {
        //Part one
        String input = Utils.read(Path.of("src/year2015/files/12.txt"));
        List<String> numbers = getNumbersFromString(input);

        int sum = 0;
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        System.out.println(sum);

        //Part two
        if (input.startsWith("[")) {
            System.out.println(getValue(new JSONArray(input)));
        } else {
            System.out.println(getValue(new JSONObject(input)));
        }
        ;
    }

    private static List<String> getNumbersFromString(String input) {
        List<String> numbers = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            StringBuilder number = new StringBuilder();
            boolean startOfNumber = false;

            while (true) {
                if (i >= input.length()) {
                    break;
                }

                char nextChar = input.charAt(i);

                if (startOfNumber) {
                    if (nextChar == '-' || Character.isDigit(nextChar)) {
                        number.append(nextChar);
                    } else {
                        break;
                    }
                } else {
                    if (nextChar == '-' || Character.isDigit(nextChar)) {
                        number.append(nextChar);
                        startOfNumber = true;
                    }
                }

                i++;
            }

            if (startOfNumber) {
                numbers.add(number.toString());
            }
        }

        return numbers;
    }

    private static int getValue(JSONArray jsonArray) {
        int sum = 0;

        for (Object entry : jsonArray) {
            if (entry instanceof Integer i) {
                sum += i;
            } else if (entry instanceof JSONObject jsonObject) {
                sum += getValue(jsonObject);
            } else if (entry instanceof  JSONArray jsonArray1) {
                sum += getValue(jsonArray1);
            }
        }

        return sum;
    }

    private static int getValue(JSONObject jsonObject) {
        int sum = 0;
        boolean includesRed = false;

        for (String key : jsonObject.keySet()) {
            Object entry = jsonObject.get(key);

            if (entry instanceof Integer i) {
                sum += i;
            } else if (entry instanceof String s) {
                if (s.equals("red")) {
                    includesRed = true;
                }
            } else if (entry instanceof JSONObject jsonObject1) {
                sum += getValue(jsonObject1);
            } else if (entry instanceof  JSONArray jsonArray1) {
                sum += getValue(jsonArray1);
            }
        }

        return includesRed ? 0 : sum;
    }
}
