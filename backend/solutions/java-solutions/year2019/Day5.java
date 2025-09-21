package year2019;

import year2019.utils.Utils;

import java.util.Arrays;

public class Day5 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("05.txt");
        int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

        runProgram(program, 1);

        //Part two
        program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        runProgram(program, 5);
    }

    private static void runProgram(int[] program, int input) {
        for (int i = 0; i < program.length;) {
            String[] mode = Integer.toString(program[i]).split("");

            int opcode = program[i] % 100;
            int mode1 = getOrDefault(mode, 2);
            int mode2 = getOrDefault(mode, 3);

            switch (opcode) {
                case 1 -> { add(program, i, mode1, mode2); i += 4; }
                case 2 -> { mul(program, i, mode1, mode2); i += 4; }
                case 3 -> { program[program[i+1]] = input; i += 2; }
                case 4 -> { print(program, i, mode1); i += 2; }
                case 5 -> i = jumpIfTrue(program, i, mode1, mode2);
                case 6 -> i = jumpIfFalse(program, i, mode1, mode2);
                case 7 -> { less(program, i, mode1, mode2); i += 4; }
                case 8 -> { equals(program, i, mode1, mode2); i += 4; }
                case 99 -> { return; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }
    }

    private static void mul(int[] program, int i, int mode1, int mode2) {
        program[program[i+3]] = (mode1 == 1 ? program[i+1] : program[program[i+1]]) * (mode2 == 1 ? program[i+2] : program[program[i+2]]);
    }

    private static void add(int[] program, int i, int mode1, int mode2) {
        program[program[i+3]] = (mode1 == 1 ? program[i+1] : program[program[i+1]]) + (mode2 == 1 ? program[i+2] : program[program[i+2]]);
    }

    private static int jumpIfTrue(int[] program, int i, int mode1, int mode2) {
        if ((mode1 == 1 ? program[i+1] : program[program[i+1]]) != 0) {
            return mode2 == 1 ? program[i+2] : program[program[i+2]];
        } else {
            return i + 3;
        }
    }

    private static int jumpIfFalse(int[] program, int i, int mode1, int mode2) {
        if ((mode1 == 1 ? program[i+1] : program[program[i+1]]) == 0) {
            return mode2 == 1 ? program[i+2] : program[program[i+2]];
        } else {
            return i + 3;
        }
    }

    private static void less(int[] program, int i, int mode1, int mode2) {
        int param1 = mode1 == 1 ? program[i+1] : program[program[i+1]];
        int param2 = mode2 == 1 ? program[i+2] : program[program[i+2]];
        program[program[i+3]] = param1 < param2 ? 1 : 0;
    }

    private static void equals(int[] program, int i, int mode1, int mode2) {
        int param1 = mode1 == 1 ? program[i+1] : program[program[i+1]];
        int param2 = mode2 == 1 ? program[i+2] : program[program[i+2]];
        program[program[i+3]] = param1 == param2 ? 1 : 0;
    }

    private static void print(int[] program, int i, int mode1) {
        int result = (mode1 == 1 ? program[i+1] : program[program[i+1]]);
        if (result != 0) {
            System.out.println(result);
        }
    }

    private static int getOrDefault(String[] arr, int digit) {
        if (digit < arr.length) {
            return Integer.parseInt(arr[arr.length - digit - 1]);
        } else {
            return 0;
        }
    }
}
