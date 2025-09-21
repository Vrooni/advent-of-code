package year2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {
    private static final Map<String, Integer> register = new HashMap<>();

    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("12.txt");
        register.put("a", 0);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            switch (instruction[0]) {
                case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
            }
        }

        System.out.println(register.get("a"));


        //Part two
        register.put("a", 0);
        register.put("b", 0);
        register.put("c", 1);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            switch (instruction[0]) {
                case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
            }
        }

        System.out.println(register.get("a"));
    }

    private static int getValue(String s) {
        return register.containsKey(s) ? register.get(s) : Integer.parseInt(s);
    }
}
