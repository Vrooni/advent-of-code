package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25 {
    private record State(Part part0, Part part1) {}

    private record Part(int value, Direction direction, char nextState) {}

    private enum Direction {
        LEFT, RIGHT
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("25.txt");
        final int STEPS = Integer.parseInt(input.get(1).split(" ")[5]);

        Map<Character, State> states = readInput(input);
        Map<Integer, Integer> tape = new HashMap<>();

        char currentState = input.get(0).charAt(input.get(0).length() - 2);
        int cursor = 0;

        for (int i = 0; i < STEPS; i++) {
            State state = states.get(currentState);

            Part part = switch (tape.getOrDefault(cursor, 0)) {
                case 0 -> state.part0;
                case 1 -> state.part1;
                default -> throw new RuntimeException("Value not allowed");
            };

            tape.put(cursor, part.value);
            cursor += part.direction == Direction.LEFT ? -1 : 1;
            currentState = part.nextState;
        }

        System.out.println(tape.values().stream().mapToInt(i -> i).sum());
    }

    private static Map<Character, State> readInput(List<String> input) {
        Map<Character, State> states = new HashMap<>();

        for (int i = 3; i < input.size(); i += 10) {
            char state = input.get(i).charAt(input.get(i).length()-2);
            Part part0 = readPart(input.get(i+2), input.get(i+3), input.get(i+4));
            Part part1 = readPart(input.get(i+6), input.get(i+7), input.get(i+8));

            states.put(state, new State(part0, part1));
        }

        return states;
    }

    private static Part readPart(String s1, String s2, String s3) {
        int value = Integer.parseInt(String.valueOf(s1.charAt(s1.length()-2)));

        Direction direction = s2.
                replace(".", "")
                .split(" - ")[1]
                .split(" ")[5].equals("right")
                ? Direction.RIGHT
                : Direction.LEFT;

        char nextState = s3.charAt(s3.length()-2);

        return new Part(value, direction, nextState);
    }
}
