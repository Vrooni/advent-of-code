package year2024;

import org.jetbrains.annotations.NotNull;
import year2024.utils.Utils;

import java.util.*;

public class Day18 {
    private record Step(int x, int y, int steps) implements Comparable<Step> {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Step other = (Step) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(@NotNull Step o) {
            return Integer.compare(this.steps, o.steps);
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("18.txt");
        boolean[][] memory = readMemory(input, 1024);

        System.out.println(minSteps(memory));


        //Part two
        for (int i = 2025; i <= input.size(); i++) {
            memory = readMemory(input, i);
            if (minSteps(memory) == -1) {
                System.out.println(input.get(i-1));
                break;
            }
        }
    }

    private static boolean[][] readMemory(List<String> input, int bytesToRead) {
        boolean[][] memory = new boolean[71][71];

        for (int i = 0; i < bytesToRead; i++) {
            String[] position = input.get(i).split(",");
            memory[Integer.parseInt(position[1])][Integer.parseInt(position[0])] = true;
        }

        return memory;
    }

    private static int minSteps(boolean[][] memory) {
        Queue<Step> queue = new PriorityQueue<>();
        queue.add(new Step(0, 0, 0));
        Set<Step> visited = new HashSet<>();
        visited.add(new Step(0, 0, 0));

        while (!queue.isEmpty()) {
            Step current = queue.poll();

            if (current.x == 70 && current.y == 70) {
                return current.steps;
            }

            for (Step neighbour : getNeighbours(memory, current)) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }

        return -1;
    }

    private static List<Step> getNeighbours(boolean[][] memory, Step current) {
        int x = current.x;
        int y = current.y;

        List<Step> neighbours = new ArrayList<>();

        if (y > 0 && !memory[y-1][x]) {
            neighbours.add(new Step(x, y-1, current.steps+1));
        }

        if (y < 70 && !memory[y+1][x]) {
            neighbours.add(new Step(x, y+1, current.steps+1));
        }

        if (x > 0 && !memory[y][x-1]) {
            neighbours.add(new Step(x-1, y, current.steps+1));
        }

        if (x < 70 && !memory[y][x+1]) {
            neighbours.add(new Step(x+1, y, current.steps+1));
        }

        return neighbours;
    }
}
