package year2018;

import year2018.utils.Utils;

import java.util.List;

public class Day19 {
    private record Instruction(String opcode, int a, int b, int c) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("19.txt");
        int pointer = Integer.parseInt(input.remove(0).replace("#ip ", ""));

        List<Instruction> instructions = readInput(input);
        int[] registers = new int[6];

        runInstructions(instructions, registers, pointer);
        System.out.println(registers[0]);


        //Part two
        registers = new int[6];
        registers[0] = 1;

        runInstructions(instructions, registers, pointer);
        System.out.println(registers[0]);
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
     *
     * START -> goto INIT
     * 0 - addi 5 16 5
     *
     * PREPARATION LOOP
     * reg3 = 1, reg2 = 1
     * 1 - seti 1 0 3
     * 2 - seti 1 2 2
     *
     * LOOP
     * add every value which evenly divides reg1
     * for (reg3 = 1; reg3 < reg1; reg3++) {
     *      for (reg2 = 1; reg2 < reg1; reg2++) {
     *          if (reg3 * reg2 == reg1) {
     *              reg0 += reg3;
     *          }
     *      }
     * }
     *
     *
     * INNER LOOP
     * 3 - mulr 3 2 4
     * 4 - eqrr 4 1 4
     * 5 - addr 4 5 5 => goto 7 if (reg3 * reg2 == reg1)
     * 6 - addi 5 1 5 => goto 8
     * 7 - addr 3 0 0
     * 8 - addi 2 1 2
     * 9 - gtrr 2 1 4
     * 10 - addr 5 4 5 => goto 12 if (reg2 > reg1)
     * 11 - seti 2 7 5 => goto 3
     * END INNER LOOP
     *
     * 12 - addi 3 1 3
     * 12 - gtrr 3 1 4
     * 14 - addr 4 5 5 => goto 16 if (reg3 > reg1)
     * 15 - seti 1 3 5 => goto 1
     * 16 - mulr 5 5 5 => halt
     * END LOOP
     *
     * INIT
     * reg1 = 2 * 2 * 19 * 11 + (7 * 22 + 20) = 1010, reg4 = 7 * 22 + 20 = 174
     * 17 - addi 1 2 1
     * 18 - mulr 1 1 1
     * 19 - mulr 5 1 1
     * 20 - muli 1 11 1
     * 21 - addi 4 7 4
     * 22 - mulr 4 5 4
     * 23 - addi 4 20 4
     * 24 - addr 1 4 1
     *
     * //DECISION
     * 25 - addr 5 0 5 => goto FURTHER INIT => this ist part two
     * 26 - seti 0 4 5 => goto PREPARATION LOOP => this is part one
     *
     * //FURTHER INIT
     * reg4 = (27 * 28 + 29) * 30 * 14 * 32 = 10550400, reg1 += reg4 = 10551410, reg0 = 0
     * 27 - setr 5 9 4
     * 28 - mulr 4 5 4
     * 29 - addr 5 4 4
     * 30 - mulr 5 4 4
     * 31 - muli 4 14 4
     * 32 - mulr 4 5 4
     * 33 - addr 1 4 1
     * 34 - seti 0 2 0
     * 35 - seti 0 5 5 => goto PREPARATION LOOP
     *
     *
     * @param instructions
     * @param registers
     * @param pointer
     */

    private static void runInstructions(List<Instruction> instructions, int[] registers, int pointer) {
        while (registers[pointer] >= 0 && registers[pointer] < instructions.size()) {
            //LOOP
            if (registers[pointer] == 1) {
                int reg1 = registers[instructions.get(1).a];

                for (int reg2 = 1; reg2 <= reg1; reg2++) {
                    if (reg1 % reg2 == 0) {
                        registers[0] += reg2;
                    }
                }

                break;
            }

            Instruction instruction = instructions.get(registers[pointer]);
            int a = instruction.a;
            int b = instruction.b;
            int c = instruction.c;

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

            registers[pointer] += 1;
        }
    }
}
