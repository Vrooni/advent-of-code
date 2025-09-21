package year2018;

import year2018.utils.Utils;

import java.util.*;

public class Day16 {
    private record Sample(int[] input, int[] outcome, int[] instruction, List<Opcode> opcodes) { }

    private enum Opcode {
        ADDR, ADDI, MULR, MULI, BANR, BANI, BORR, BORI, SETR, SETI, GTIR, GTRI, GTRR, EQIR, EQRI, EQRR
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("16.txt");

        List<Sample> samples = new ArrayList<>();
        List<int[]> tests = new ArrayList<>();

        readInput(input, samples, tests);
        System.out.println(samples.stream().filter(sample -> sample.opcodes.size() >= 3).count());


        //Part two
        Map<Integer, Opcode> opcodes = getOpcodes(samples);

        int[] registers = new int[4];
        runTests(opcodes, registers, tests);

        System.out.println(registers[0]);
    }

    private static void readInput(List<String> input, List<Sample> samples, List<int[]> tests) {
        int i;
        for (i = 0; input.get(i).startsWith("Before:"); i += 4) {
            samples.add(new Sample(
                    Arrays.stream(input.get(i)
                                    .replace("Before: ", "")
                                    .replace("[", "")
                                    .replace("]", "").split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    Arrays.stream(input.get(i+2)
                                    .replace("After:  ", "")
                                    .replace("[", "")
                                    .replace("]", "").split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    Arrays.stream(input.get(i+1).split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray(),
                    new ArrayList<>()
            ));
        }

        for (i = i+2; i < input.size(); i++) {
            tests.add(Arrays.stream(input.get(i).split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray()
            );
        }

        samples.forEach(Day16::getPossibleOpcodes);
    }

    private static void getPossibleOpcodes(Sample sample) {
        getPossibleCalculations(sample);
        getPossibleGates(sample);
        getPossibleAssignments(sample);
        getPossibleComparisons(sample);
    }

    private static void getPossibleCalculations(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //addr
        int[] result = sample.input.clone();
        result[c] = result[a] + result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.ADDR);
        }

        //addi
        result = sample.input.clone();
        result[c] = result[a] + b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.ADDI);
        }

        //mulr
        result = sample.input.clone();
        result[c] = result[a] * result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.MULR);
        }

        //muli
        result = sample.input.clone();
        result[c] = result[a] * b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.MULI);
        }
    }

    private static void getPossibleGates(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //banr
        int[] result = sample.input.clone();
        result[c] = result[a] & result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BANR);
        }

        //bani
        result = sample.input.clone();
        result[c] = result[a] & b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BANI);
        }

        //borr
        result = sample.input.clone();
        result[c] = result[a] | result[b];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BORR);
        }

        //bori
        result = sample.input.clone();
        result[c] = result[a] | b;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.BORI);
        }
    }


    private static void getPossibleAssignments(Sample sample) {
        int a = sample.instruction[1];
        int c = sample.instruction[3];

        //setr
        int[] result = sample.input.clone();
        result[c] = result[a];
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.SETR);
        }

        //seti
        result = sample.input.clone();
        result[c] = a;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.SETI);
        }
    }

    private static void getPossibleComparisons(Sample sample) {
        int a = sample.instruction[1];
        int b = sample.instruction[2];
        int c = sample.instruction[3];

        //gtir
        int[] result = sample.input.clone();
        result[c] = a > result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTIR);
        }

        //gtri
        result = sample.input.clone();
        result[c] = result[a] > b ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTRI);
        }

        //gtrr
        result = sample.input.clone();
        result[c] = result[a] > result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.GTRR);
        }

        //eqir
        result = sample.input.clone();
        result[c] = a == result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQIR);
        }

        //eqri
        result = sample.input.clone();
        result[c] = result[a] == b ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQRI);
        }

        //eqrr
        result = sample.input.clone();
        result[c] = result[a] == result[b] ? 1 : 0;
        if (Arrays.equals(result, sample.outcome)) {
            sample.opcodes.add(Opcode.EQRR);
        }
    }

    private static Map<Integer, Opcode> getOpcodes(List<Sample> samples) {
        Map<Integer, Sample> opcodeSampleMap = new HashMap<>();
        samples.forEach(sample -> opcodeSampleMap.put(sample.instruction[0], sample));

        Queue<Sample> queue = new LinkedList<>(opcodeSampleMap.values());
        List<Opcode> taken = new ArrayList<>();

        Map<Integer, Opcode> opcodes = new HashMap<>();

        while (!queue.isEmpty()) {
            Sample sample = queue.poll();
            List<Opcode> possibleOpcodes = sample.opcodes.stream()
                    .filter(opcode -> !taken.contains(opcode))
                    .toList();

            if (possibleOpcodes.size() == 1) {
                taken.add(possibleOpcodes.get(0));
                opcodes.put(sample.instruction[0], possibleOpcodes.get(0));
            } else {
                queue.add(sample);
            }
        }

        return opcodes;
    }

    private static void runTests(Map<Integer, Opcode> opcodes, int[] registers, List<int[]> tests) {
        tests.forEach(test -> {
            int opcodeNumber = test[0];
            int a = test[1];
            int b = test[2];
            int c = test[3];

            switch (opcodes.get(opcodeNumber)) {
                case ADDR -> registers[c] = registers[a] + registers[b];
                case ADDI -> registers[c] = registers[a] + b;
                case MULR -> registers[c] = registers[a] * registers[b];
                case MULI -> registers[c] = registers[a] * b;
                case BANR -> registers[c] = registers[a] & registers[b];
                case BANI -> registers[c] = registers[a] & b;
                case BORR -> registers[c] = registers[a] | registers[b];
                case BORI -> registers[c] = registers[a] | b;
                case SETR -> registers[c] = registers[a];
                case SETI -> registers[c] = a;
                case GTIR -> registers[c] = a > registers[b] ? 1 : 0;
                case GTRI -> registers[c] = registers[a] > b ? 1 : 0;
                case GTRR -> registers[c] = registers[a] > registers[b] ? 1 : 0;
                case EQIR -> registers[c] = a == registers[b] ? 1 : 0;
                case EQRI -> registers[c] = registers[a] == b ? 1 : 0;
                case EQRR -> registers[c] = registers[a] == registers[b] ? 1 : 0;
            }
        });
    }
}
