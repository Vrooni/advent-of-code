package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 {
    private record Instruction(String command, String register, Integer value) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/23.txt"));
        List<Instruction> instructions = input.stream().map(line -> {
            String[] splitLine = line.replace(",", "").split(" ");
            String register = null;
            Integer value = null;

            if (isNumeric(splitLine[1])) {
                value = Integer.parseInt(splitLine[1]);
            } else {
                register = splitLine[1];
                value = splitLine.length == 3 ? Integer.parseInt(splitLine[2]) : null;
            }

            return new Instruction(splitLine[0], register, value);
        }).toList();

        Map<String, Integer> registers = new HashMap<>();
        int index = 0;

        runInstruction(instructions, registers, index);
        System.out.println(registers.get("b"));

        //Part two
        registers = new HashMap<>();
        registers.put("a", 1);
        index = 0;

        runInstruction(instructions, registers, index);
        System.out.println(registers.get("b"));
    }

    private static void runInstruction(List<Instruction> instructions, Map<String, Integer> registers, int index) {
        while (index < instructions.size()) {
            Instruction instruction = instructions.get(index);

            switch (instruction.command) {
                case "hlf" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) / 2);
                case "tpl" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) * 3);
                case "inc" -> registers.put(instruction.register, registers.getOrDefault(instruction.register, 0) + 1);
                case "jmp" -> index += instruction.value - 1;
                case "jie" -> index += registers.getOrDefault(instruction.register, 0) % 2 == 0 ? instruction.value - 1 : 0;
                case "jio" -> index += registers.getOrDefault(instruction.register, 0) == 1 ? instruction.value - 1 : 0;
            }

            index++;
        }
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
