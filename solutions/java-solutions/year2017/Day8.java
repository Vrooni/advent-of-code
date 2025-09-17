package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("08.txt");
        Map<String, Integer> registers = new HashMap<>();

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" if ");

            if (eval(splitInstruction[1], registers)) {
                String[] operation = splitInstruction[0].split(" ");

                String register = operation[0];
                int value = Integer.parseInt(operation[2]);
                int mul = operation[1].equals("inc") ? 1 : -1;

                int newValue = registers.getOrDefault(register, 0) + mul * value;
                registers.put(register, newValue);
            }
        }

        System.out.println(registers.values().stream().max(Integer::compareTo).get());


        //Part two
        registers = new HashMap<>();
        int maxValue = 0;

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" if ");

            if (eval(splitInstruction[1], registers)) {
                String[] operation = splitInstruction[0].split(" ");

                String register = operation[0];
                int value = Integer.parseInt(operation[2]);
                int mul = operation[1].equals("inc") ? 1 : -1;

                int newValue = registers.getOrDefault(register, 0) + mul * value;
                maxValue = Math.max(newValue, maxValue);

                registers.put(register, newValue);
            }
        }

        System.out.println(maxValue);
    }

    private static boolean eval(String condition, Map<String, Integer> registers) {
        String[] splitCondition = condition.split(" ");

        int value1 = registers.getOrDefault(splitCondition[0], 0);
        int value2 = Integer.parseInt(splitCondition[2]);

        boolean result;

        switch (splitCondition[1]) {
            case "==" -> result = value1 == value2;
            case "!=" -> result = value1 != value2;
            case "<" -> result = value1 < value2;
            case ">" -> result = value1 > value2;
            case "<=" -> result = value1 <= value2;
            case ">=" -> result = value1 >= value2;
            default -> result = false;
        }

        return result;
    }
}
