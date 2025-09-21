package year2018;

import year2018.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day21 {
    private record Instruction(String opcode, int a, int b, int c) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("21.txt");
        int pointer = Integer.parseInt(input.remove(0).replace("#ip ", ""));

        List<Instruction> instructions = readInput(input);
        int[] registers = new int[6];

        System.out.println(getPossibleValue(instructions, registers, pointer, true));


        //Part two
        System.out.println(getPossibleValue(instructions, registers, pointer, false));
    }

    private static List<Instruction> readInput(List<String> input) {
        return input.stream().map(line -> {
            String[] splitLine = line.split(" ");
            return new Instruction(splitLine[0],
                    Integer.parseInt(splitLine[1]),
                    Integer.parseInt(splitLine[2]),
                    Integer.parseInt(splitLine[3])
            );
        }).toList();
    }

    /**
     * reg4 goes from 0 to reg3 / 256 => reg4 = reg3 / 256; reg3 = 1;
     * 17 - seti 0 6 4
     * 18 - addi 4 1 3
     * 19 - muli 3 256 3
     * 20 - gtrr 3 1 3
     * 21 - addr 3 2 2 => if (reg3 > reg1) GOTO 23
     * 22 - addi 2 1 2 => GOTO 24
     * 23 - seti 25 4 2 => GOTO 26
     * 24 - addi 4 1 4
     * 25 - seti 17 6 2 => GOTO 18
     */
    private static int getPossibleValue(List<Instruction> instructions, int[] registers, int pointer, boolean lowest) {
        List<Integer> possibleValues = new ArrayList<>();

        while (registers[pointer] >= 0 && registers[pointer] < instructions.size()) {
            Instruction instruction = instructions.get(registers[pointer]);
            int a = instruction.a;
            int b = instruction.b;
            int c = instruction.c;

            if (registers[pointer] == 18) {
                registers[a] = registers[b] / 256;
                registers[b] = 1;
                registers[pointer] = 26;
                continue;
            }

            //28 - addr 4 2 2 => reg5 == reg0 HALT !!ONLY TIME register 0 is required
            if (registers[pointer] == 28) {
                int value = registers[a];

                if (lowest) {
                    return value;
                }

                if (possibleValues.contains(value)) {
                    return possibleValues.get(possibleValues.size()-1);
                }

                possibleValues.add(value);
            }

            switch (instruction.opcode) {
                case "addr" -> registers[c] = registers[a] + registers[b];
                case "addi" -> registers[c] = registers[a] + b;
                case "mulr" -> registers[c] = registers[a] * registers[b];
                case "muli" -> registers[c] = registers[a] * b;
                case "banr" -> registers[c] = registers[a] & registers[b];
                case "bani" -> registers[c] = registers[a] & b;
                case "borr" -> registers[c] = registers[a] | registers[b];
                case "bori" -> registers[c] = registers[a] | b;
                case "setr" -> registers[c] = registers[a];
                case "seti" -> registers[c] = a;
                case "gtir" -> registers[c] = a > registers[b] ? 1 : 0;
                case "gtri" -> registers[c] = registers[a] > b ? 1 : 0;
                case "gtrr" -> registers[c] = registers[a] > registers[b] ? 1 : 0;
                case "eqir" -> registers[c] = a == registers[b] ? 1 : 0;
                case "eqri" -> registers[c] = registers[a] == b ? 1 : 0;
                case "eqrr" -> registers[c] = registers[a] == registers[b] ? 1 : 0;
            }

            registers[pointer]++;
        }

        return Integer.MAX_VALUE;
    }
}
