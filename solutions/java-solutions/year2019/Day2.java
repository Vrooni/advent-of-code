package year2019;

import year2019.utils.Utils;

import java.util.Arrays;

public class Day2 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("02.txt");
        int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        program[1] = 12;
        program[2] = 2;
        runProgram(program);

        System.out.println(program[0]);


        //Part two
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

                program[1] = i;
                program[2] = j;
                runProgram(program);

                if (program[0] == 19690720) {
                    System.out.println(100 * i + j);
                    return;
                }
            }
        }
    }

    private static void runProgram(int[] program) {
        for (int i = 0; i < program.length; i += 4) {
            switch (program[i]) {
                case 1 -> program[program[i+3]] = program[program[i+1]] + program[program[i+2]];
                case 2 -> program[program[i+3]] = program[program[i+1]] * program[program[i+2]];
                case 99 -> { return; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }
    }
}
