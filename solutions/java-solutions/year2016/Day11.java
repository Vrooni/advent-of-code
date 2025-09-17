package year2016;

import java.util.*;

public class Day11 {

    private static class Floor {
        List<String> chips = new ArrayList<>();
        List<String> generators = new ArrayList<>();

        private boolean isValid() {
            return this.generators.isEmpty() || new HashSet<>(this.generators).containsAll(this.chips);
        }

        public boolean isEmpty() {
            return chips.isEmpty() && generators.isEmpty();
        }

        @Override
        public int hashCode() {
            int result = 17;
            List<String> sortedChips = new ArrayList<>(chips);
            List<String> sortedGenerators = new ArrayList<>(generators);
            Collections.sort(sortedChips);
            Collections.sort(sortedGenerators);
            result = 31 * result + sortedChips.hashCode();
            result = 31 * result + sortedGenerators.hashCode();
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            final Floor other = (Floor) obj;
            return new HashSet<>(this.chips).containsAll(other.chips) && new HashSet<>(this.generators).containsAll(other.generators);
        }
    }

    public static class Node implements Comparable<Node> {
        Floor[] floors = new Floor[4];
        int floor;
        int steps;

        public Node() {
            floors[0] = new Floor();
            floors[1] = new Floor();
            floors[2] = new Floor();
            floors[3] = new Floor();
            this.floor = 0;
            this.steps = 0;
        }

        public Node(int floor, int steps) {
            floors[0] = new Floor();
            floors[1] = new Floor();
            floors[2] = new Floor();
            floors[3] = new Floor();
            this.floor = floor;
            this.steps = steps;
        }

        private Node getCopy(int next) {
            final Node newNode = new Node(next, steps + 1);
            final Floor f1 = new Floor();
            final Floor f2 = new Floor();
            final Floor f3 = new Floor();
            final Floor f4 = new Floor();
            f1.chips.addAll(floors[0].chips);
            f1.generators.addAll(floors[0].generators);
            f2.chips.addAll(floors[1].chips);
            f2.generators.addAll(floors[1].generators);
            f3.chips.addAll(floors[2].chips);
            f3.generators.addAll(floors[2].generators);
            f4.chips.addAll(floors[3].chips);
            f4.generators.addAll(floors[3].generators);
            newNode.floors[0] = f1;
            newNode.floors[1] = f2;
            newNode.floors[2] = f3;
            newNode.floors[3] = f4;
            return newNode;
        }

        List<Node> getChipGeneratorCombos(int current, int next) {
            List<Node> neighbours = new ArrayList<>();

            for (String chip : floors[current].chips) {
                if (floors[current].generators.contains(chip)) {
                    Node newNode = getCopy(next);

                    newNode.floors[current].chips.remove(chip);
                    newNode.floors[current].generators.remove(chip);
                    newNode.floors[next].chips.add(chip);
                    newNode.floors[next].generators.add(chip);

                    neighbours.add(newNode);

                    return neighbours;
                }
            }
            return neighbours;
        }

        List<Node> getChipCombos(int current, int next) {
            List<Node> neighbours = new ArrayList<>();
            List<String> chips = floors[current].chips;

            for (int i = 0; i < chips.size() - 1; i++) {
                for (int j = i + 1; j < chips.size(); j++) {
                    Node newNode = getCopy(next);
                    String chip1 = floors[current].chips.get(i);
                    String chip2 = floors[current].chips.get(j);

                    newNode.floors[current].chips.remove(chip1);
                    newNode.floors[current].chips.remove(chip2);
                    newNode.floors[next].chips.add(chip1);
                    newNode.floors[next].chips.add(chip2);

                    if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                        neighbours.add(newNode);
                        return neighbours;
                    }
                }
            }

            return neighbours;
        }

        List<Node> getGeneratorCombos(int current, int next) {
            List<Node> neighbours = new ArrayList<>();
            List<String> generators = floors[current].generators;

            for (int i = 0; i < generators.size() - 1; i++) {
                for (int j = i + 1; j < generators.size(); j++) {
                    Node newNode = getCopy(next);
                    String gen1 = floors[current].generators.get(i);
                    String gen2 = floors[current].generators.get(j);

                    newNode.floors[current].generators.remove(gen1);
                    newNode.floors[current].generators.remove(gen2);
                    newNode.floors[next].generators.add(gen1);
                    newNode.floors[next].generators.add(gen2);

                    if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                        neighbours.add(newNode);
                        return neighbours;
                    }
                }
            }

            return neighbours;
        }

        List<Node> getDouble(int current, int next) {
            final List<Node> neighbours = getChipGeneratorCombos(current, next);
            neighbours.addAll(getChipCombos(current, next));
            neighbours.addAll(getGeneratorCombos(current, next));
            return neighbours;
        }

        List<Node> getSingleChip(int current, int next) {
            List<Node> neighbours = new ArrayList<>();

            for (String element : floors[current].chips) {
                Node newNode = getCopy(next);
                newNode.floors[current].chips.remove(element);
                newNode.floors[next].chips.add(element);

                if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                    neighbours.add(newNode);
                    return neighbours;
                }
            }
            return neighbours;
        }

        List<Node> getSingleGenerator(int current, int next) {
            List<Node> neighbours = new ArrayList<>();

            for (String element : floors[current].generators) {
                Node newNode = getCopy(next);
                newNode.floors[current].generators.remove(element);
                newNode.floors[next].generators.add(element);

                if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                    neighbours.add(newNode);
                    return neighbours;
                }
            }
            return neighbours;
        }

        List<Node> getNeighbours() {
            List<Node> neighbours = new ArrayList<>();

            if (floor < 3) {
                neighbours = getDouble(floor, floor + 1);
                if (neighbours.isEmpty()) {
                    neighbours.addAll(getSingleChip(floor, floor + 1));
                    if (neighbours.isEmpty()) {
                        neighbours.addAll(getSingleGenerator(floor, floor + 1));
                    }
                }
            }

            if (floor > 0) {
                neighbours.addAll(getSingleChip(floor, floor - 1));
                if (neighbours.isEmpty()) {
                    neighbours.addAll(getSingleGenerator(floor, floor - 1));
                }
            }

            return neighbours;
        }

        @Override
        public int compareTo(final Node o) {
            return Integer.compare(this.steps, o.steps);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + floor;
            result = prime * result + Arrays.hashCode(floors);
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            final Node other = (Node) obj;
            if (floor != other.floor) {
                return false;
            }

            return Arrays.equals(floors, other.floors);
        }
    }

    public static void main(final String[] args) {
        //Part one
        List<String> input = Utils.readLines("11.txt");
        Node start = readInput(input);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(start);

        Map<Node, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int steps = current.steps;

            if (current.floors[0].isEmpty() && current.floors[1].isEmpty() && current.floors[2].isEmpty()) {
                System.out.println(current.steps);
                break;
            }

            List<Node> neighbours = current.getNeighbours();
            for (Node neighbour : neighbours) {
                int nsteps = steps + 1;

                if (nsteps < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, nsteps);
                    queue.remove(neighbour);
                    neighbour.steps = nsteps;
                    queue.add(neighbour);
                }
            }
        }


        //Part two
        start.floors[0].chips.addAll(List.of("elerium", "dilithium"));
        start.floors[0].generators.addAll(List.of("elerium", "dilithium"));

        queue = new PriorityQueue<>();
        queue.offer(start);

        distances = new HashMap<>();
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int steps = current.steps;

            if (current.floors[0].isEmpty() && current.floors[1].isEmpty() && current.floors[2].isEmpty()) {
                System.out.println(current.steps);
                break;
            }

            List<Node> neighbours = current.getNeighbours();
            for (Node neighbour : neighbours) {
                int nsteps = steps + 1;

                if (nsteps < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, nsteps);
                    queue.remove(neighbour);
                    neighbour.steps = nsteps;
                    queue.add(neighbour);
                }
            }
        }
    }

    private static Node readInput(List<String> lines) {
        Node node = new Node();

        for (int i = 0; i < 4; i++) {
            String line = lines.get(i);

            if (!line.contains("nothing relevant")) {
                for (String part : line.replace(".", "").split(",and|,|and")) {
                    if (part.isBlank()) {
                        continue;
                    }

                    String[] splitPart = part.split(" ");
                    String type = splitPart[splitPart.length-1];
                    String element = splitPart[splitPart.length-2];

                    if (type.equals("generator")) {
                        node.floors[i].generators.add(element);
                    } else if (type.equals("microchip")) {
                        node.floors[i].chips.add(element.replace("-compatible", ""));
                    }
                }
            }
        }

        return node;
    }
}
