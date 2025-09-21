package year2024;

import year2024.utils.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24 {
    private static class Gate {
        String input1;
        String operation;
        String input2;
        String output;

        public Gate(String input1, String operation, String input2, String output) {
            this.input1 = input1;
            this.operation = operation;
            this.input2 = input2;
            this.output = output;
        }

        public boolean hasInput(String input) {
            return this.input1.equals(input) || this.input2.equals(input);
        }

        public boolean hasInputs(String input1, String input2) {
            return this.input1.equals(input1) && this.input2.equals(input2)
                    || this.input1.equals(input2) && this.input2.equals(input1);
        }

        public boolean startsWithXY() {
            return (this.input1.startsWith("x") || this.input1.startsWith("y"))
                    && !this.input1.equals("x00") && !this.input1.equals("y00");
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Gate gate)) return false;
            return Objects.equals(input1, gate.input1)
                    && Objects.equals(operation, gate.operation)
                    && Objects.equals(input2, gate.input2)
                    && Objects.equals(output, gate.output);
        }

        @Override
        public int hashCode() {
            return Objects.hash(input1, operation, input2, output);
        }

        @Override
        public String toString() {
            return input1 + " " + operation + " " + input2 + " -> " + output;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("24.txt");

        Map<String, Boolean> wires = new HashMap<>();
        List<String> outputWires = new ArrayList<>();
        List<Gate> gates = new ArrayList<>();

        readInput(input, wires, gates, outputWires);
        String result = getResult(wires, outputWires, gates);

        System.out.println(Long.parseLong(result, 2));


        //Part two
        wires = new HashMap<>();
        outputWires = new ArrayList<>();
        gates = new ArrayList<>();

        readInput(input, wires, gates, outputWires);
        List<String> falseOutputs = new ArrayList<>(getFalseOutputs(gates));

        Collections.sort(falseOutputs);
        System.out.println(String.join(",", falseOutputs));
    }

    /**
     * outputs are always swapped in the same adder
     * cases first half adder:
     * 1. y00 XOR x00, output is z00
     * 2. y00 AND x00, output is !z00
     * cases last full adder:
     * 3. output z44, operation is XOR
     * 4. output z45, operation is OR
     * cases full adder:
     * 5. operation AND, output is in gate with OR
     * 6. operation OR, output is in gate with AND/OR
     * 7. y** XOR x**, output is in gate with XOR/AND
     * 8. !y** XOR !x**, output begins with z
     */
    private static Set<String> getFalseOutputs(List<Gate> gates) {
        Set<String> falseOutputs = new HashSet<>();

        for (Gate gate : gates) {
            if (gate.hasInputs("x00", "y00")) {
                addIf(gate.operation.equals("XOR") && !gate.output.startsWith("z"),
                    gate, falseOutputs
                );
                addIf(gate.operation.equals("AND") && gate.output.startsWith("z"),
                    gate, falseOutputs
                );
            }

            else if (gate.output.equals("z44")) {
                addIf(!gate.operation.equals("XOR"), gate, falseOutputs);
            }

            else if (gate.output.equals("z45")) {
                addIf(!gate.operation.equals("OR"), gate, falseOutputs);
            }

            else if (gate.operation.equals("AND")) {
                addIf(gates.stream().noneMatch(g -> g.hasInput(gate.output) && g.operation.equals("OR")),
                    gate, falseOutputs
                );

            }

            else if (gate.operation.equals("OR")) {
                addIf(gates.stream().noneMatch(g -> g.hasInput(gate.output) && g.operation.equals("AND")),
                    gate, falseOutputs
                );
            }

            else if (gate.operation.equals("XOR")) {
                addIf(gates.stream().noneMatch(g -> g.hasInput(gate.output) && g.operation.equals("AND"))
                                && gate.startsWithXY(), gate, falseOutputs
                );
                addIf(!gate.output.startsWith("z") && !gate.startsWithXY(), gate, falseOutputs);
            }
        }

        return falseOutputs;
    }

    private static void addIf(boolean condition, Gate gate, Set<String> falseOutputs) {
        if (condition) {
            falseOutputs.add(gate.output);
        }
    }

    private static String getResult(Map<String, Boolean> wires, List<String> outputWires, List<Gate> gates) {
        while (!wires.keySet().containsAll(outputWires)) {
            for (Gate gate : gates) {
                Boolean wire1 = wires.get(gate.input1);
                Boolean wire2 = wires.get(gate.input2);

                if (wire1 != null && wire2 != null) {
                    boolean result = switch (gate.operation) {
                        case "OR" -> wire1 || wire2;
                        case "XOR" -> !wire1.equals(wire2);
                        case "AND" -> wire1 && wire2;
                        default -> false;
                    };

                    wires.put(gate.output, result);
                }
            }
        }

        List<String> sortedWires = wires.keySet().stream()
                .filter(entry -> entry.startsWith("z"))
                .sorted((o1, o2) -> Integer.compare(
                        Integer.parseInt(o2.replace("z", "")),
                        Integer.parseInt(o1.replace("z", ""))
                ))
                .toList();

        return sortedWires.stream().map(wire -> wires.get(wire) ? "1" : "0").collect(Collectors.joining(""));
    }

    private static void readInput(List<String> input, Map<String, Boolean> wires, List<Gate> gates, List<String> outputWires) {
        Pattern wirePattern = Pattern.compile("^(.*): (0|1)");
        Pattern gatePattern = Pattern.compile("^(.*) (OR|XOR|AND) (.*) -> (.*)");

        for (String line : input) {
            Matcher wireMatcher = wirePattern.matcher(line);
            Matcher gateMatcher = gatePattern.matcher(line);

            if (wireMatcher.find()) {
                wires.put(wireMatcher.group(1), wireMatcher.group(2).equals("1"));
            }

            if (gateMatcher.find()) {
                gates.add(new Gate(gateMatcher.group(1), gateMatcher.group(2), gateMatcher.group(3), gateMatcher.group(4)));
            }
        }

        for (Gate gate : gates) {
            if (gate.output.startsWith("z")) {
                outputWires.add(gate.output);
            }
        }
    }
}
