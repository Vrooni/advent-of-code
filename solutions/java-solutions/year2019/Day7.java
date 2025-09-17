package year2019;

import year2019.utils.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day7 {
    public static void main(String[] args) {
        String input = Utils.readString("07.txt");
        int maxOutput = Integer.MIN_VALUE;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        for (int m = 0; m < 5; m++) {
                            if (new HashSet<>(List.of(i, j, k, l, m)).size() != 5) {
                                continue;
                            }

                            int[] program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            int output = runProgram(program, i, 0);

                            program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            output = runProgram(program, j, output);

                            program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            output = runProgram(program, k, output);

                            program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            output = runProgram(program, l, output);

                            program = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            output = runProgram(program, m, output);

                            maxOutput = Math.max(output, maxOutput);
                        }
                    }
                }
            }
        }

        System.out.println(maxOutput);


        //Part two
        maxOutput = Integer.MIN_VALUE;

        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                for (int k = 5; k < 10; k++) {
                    for (int l = 5; l < 10; l++) {
                        for (int m = 5; m < 10; m++) {
                            if (new HashSet<>(List.of(i, j, k, l, m)).size() != 5) {
                                continue;
                            }

                            int outputBefore;
                            int output = 0;

                            int[] program1 = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            int[] program2 = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            int[] program3 = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            int[] program4 = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
                            int[] program5 = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();

                            do {
                                outputBefore = output;
                                output = runProgram(program1, i, output);
                                output = runProgram(program2, j, output);
                                output = runProgram(program3, k, output);
                                output = runProgram(program4, l, output);
                                output = runProgram(program5, m, output);
                                maxOutput = Math.max(output, maxOutput);
                            } while (outputBefore != output);
                        }
                    }
                }
            }
        }

        System.out.println(maxOutput);
    }

    private static int runProgram(int[] program, int phaseSetting, int input) {
        boolean firstInput = true;

        for (int i = 0; i < program.length;) {
            String[] mode = Integer.toString(program[i]).split("");

            int opcode = program[i] % 100;
            int mode1 = getOrDefault(mode, 2);
            int mode2 = getOrDefault(mode, 3);

            switch (opcode) {
                case 1 -> { add(program, i, mode1, mode2); i += 4; }
                case 2 -> { mul(program, i, mode1, mode2); i += 4; }
                case 3 -> {program[program[i+1]] = firstInput ? phaseSetting : input; i += 2; firstInput = false; }
                case 4 -> { return print(program, i, mode1); }
                case 5 -> i = jumpIfTrue(program, i, mode1, mode2);
                case 6 -> i = jumpIfFalse(program, i, mode1, mode2);
                case 7 -> { less(program, i, mode1, mode2); i += 4; }
                case 8 -> { equals(program, i, mode1, mode2); i += 4; }
                case 99 -> { return -1; }
                default -> throw new RuntimeException("unknown opcode");
            }
        }

        return -1;
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

    private static int print(int[] program, int i, int mode1) {
        return mode1 == 1 ? program[i+1] : program[program[i+1]];
    }

    private static int getOrDefault(String[] arr, int digit) {
        if (digit < arr.length) {
            return Integer.parseInt(arr[arr.length - digit - 1]);
        } else {
            return 0;
        }
    }
}
