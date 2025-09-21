package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 {
    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("23.txt");
        Map<String, Integer> registers = new HashMap<>(Map.of(
                "a", 0,
                "b", 0,
                "c", 0,
                "d", 0,
                "e", 0,
                "f", 0,
                "h", 0
        ));

        int index = 0;
        int mulCount = 0;
        while (index >= 0 && index < instructions.size()) {
            String[] instruction = instructions.get(index).split(" ");

            switch (instruction[0]) {
                case "set" -> registers.put(instruction[1], getValue(registers, instruction[2]));
                case "sub" -> registers.put(instruction[1], registers.get(instruction[1]) - getValue(registers, instruction[2]));
                case "mul" -> { registers.put(instruction[1], registers.get(instruction[1]) * getValue(registers, instruction[2])); mulCount++; }
                case "jnz" -> index += getValue(registers, instruction[1]) == 0 ? 0 : getValue(registers, instruction[2]) - 1;
            }

            index++;
        }

        System.out.println(mulCount);


        //Part two
        int b = Integer.parseInt(instructions.get(0).split(" ")[2]);
        b = b * 100 + 100000;
        int c = b + 17000;
        int h = 0;

        for (; b != c + 17; b += 17) {
            int f = 1;
            for (int d = 2; d < b; d++) {
                if (b % d == 0) {
                    f = 0;
                    break;
                }
            }

            if (f == 0) {
                h++;
            }
        }

        System.out.println(h);
    }

    private static int getValue(Map<String, Integer> registers, String regOrValue) {
        return registers.containsKey(regOrValue) ? registers.get(regOrValue) : Integer.parseInt(regOrValue);
    }
}
