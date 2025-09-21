package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day17 {
    private record A(long a, int i) {}
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("17.txt");
        String programString = input.get(4).split(": ")[1];
        int[] program = Arrays.stream(programString.split(",")).mapToInt(Integer::parseInt).toArray();

        int a = Integer.parseInt(input.get(0).split(": ")[1]);
        int b = Integer.parseInt(input.get(1).split(": ")[1]);
        int c = Integer.parseInt(input.get(2).split(": ")[1]);

        List<Long> output = runProgram(program, a, b, c);

        for (int i = 0; i < output.size()-1; i++) {
            System.out.print(output.get(i)+",");
        }
        System.out.println(output.get(output.size()-1));


        //Part two
        Queue<A> queue = new LinkedList<>();
        queue.add(new A(0, program.length-1));

        while (!queue.isEmpty()) {
            A current = queue.poll();

            for (long i = current.a; i < current.a + 8; i++) {
                if (runProgram(program, i, b, c).get(0) == program[current.i]) {
                    queue.add(new A(i * 8, current.i-1));

                    if (current.i == 0) {
                        System.out.println(i);
                        return;
                    }
                }
            }
        }
    }

    private static List<Long> runProgram(int[] program, long a, long b, long c) {
        List<Long> output = new ArrayList<>();
        int pointer = 0;

        while (pointer < program.length-1) {
            int opcode = program[pointer];
            int literalOperand = program[pointer+1];

            long comboOperand = switch (literalOperand) {
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> literalOperand;
            };

            switch (opcode) {
                case 0 -> a = (long) (a / (Math.pow(2, comboOperand)));
                case 1 -> b = b ^ literalOperand;
                case 2 -> b = comboOperand % 8;
                case 3 -> {
                    if (a != 0) {
                        pointer = literalOperand;
                        continue;
                    }
                }
                case 4 -> b = b ^ c;
                case 5 -> output.add(comboOperand % 8);
                case 6 -> b = (long) (a / (Math.pow(2, comboOperand)));
                case 7 -> c = (long) (a / (Math.pow(2, comboOperand)));
            }

            pointer += 2;
        }

        return output;
    }
}
