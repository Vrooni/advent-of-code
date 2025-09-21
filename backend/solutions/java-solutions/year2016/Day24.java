package year2016;

import java.util.*;

public class Day24 {

    private record Position(int x, int y, int number, Map<Position, Integer> distances) {
        public List<Position> getNeighbours(List<String> input) {
            List<Position> neighbours = new ArrayList<>();

            int nx = x;
            int ny = y - 1;
            if (ny >= 0 && input.get(ny).charAt(nx) != '#') {
                neighbours.add(new Position(nx, ny, -1, new HashMap<>()));
            }

            nx = x;
            ny = y + 1;
            if (input.get(ny).charAt(nx) != '#') {
                neighbours.add(new Position(nx, ny, -1, new HashMap<>()));
            }

            nx = x - 1;
            ny = y;
            if (nx >= 0 && input.get(ny).charAt(nx) != '#') {
                neighbours.add(new Position(nx, ny, -1, new HashMap<>()));
            }

            nx = x + 1;
            ny = y;
            if (input.get(ny).charAt(nx) != '#') {
                neighbours.add(new Position(nx, ny, -1, new HashMap<>()));
            }

            return neighbours;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Position other = (Position) obj;
            return this.x == other.x && this.y == other.y;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("24.txt");
        List<Position> visitPoints = getVisitPoints(input);

        for (int i = 0; i < visitPoints.size(); i++) {
            Position p1 = visitPoints.get(i);

            for (int j = i + 1; j < visitPoints.size(); j++) {
                Position p2 = visitPoints.get(j);
                int minDist = getMinDistance(p1, p2, input);

                p1.distances.put(p2, minDist);
                p2.distances.put(p1, minDist);
            }
        }

        Position start = visitPoints.stream().filter(pos -> pos.number == 0).findFirst().get();
        visitPoints.remove(start);

        List<List<Position>> permutations = new ArrayList<>();
        getPermutations(visitPoints, 0, permutations);

        int minDist = Integer.MAX_VALUE;
        for (List<Position> permutation : permutations) {
            int distance = start.distances.get(permutation.get(0));

            for (int i = 1; i < permutation.size(); i++) {
                distance += permutation.get(i).distances.get(permutation.get(i - 1));
            }

            if (distance < minDist) {
                minDist = distance;
            }
        }

        System.out.println(minDist);


        //Part two
        minDist = Integer.MAX_VALUE;
        for (List<Position> permutation : permutations) {
            Position first = permutation.get(0);
            Position last = permutation.get(permutation.size()-1);

            int distance = start.distances.get(first) + start.distances.get(last);

            for (int i = 1; i < permutation.size(); i++) {
                distance += permutation.get(i).distances.get(permutation.get(i - 1));
            }

            if (distance < minDist) {
                minDist = distance;
            }
        }

        System.out.println(minDist);
    }


    private static List<Position> getVisitPoints(List<String> input) {
        List<Position> visitPoints = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c != '.' && c != '#') {
                    visitPoints.add(new Position(j, i, Integer.parseInt(String.valueOf(c)), new HashMap<>()));
                }
            }
        }

        return visitPoints;
    }

    private static int getMinDistance(Position start, Position end, List<String> input) {
        Queue<Position> queue = new LinkedList<>();
        queue.offer(start);

        Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Position node = queue.poll();
            int dist = distances.get(node);
            List<Position> neighbours = node.getNeighbours(input);

            for (Position neighbour : neighbours) {
                if (dist + 1 < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, dist + 1);
                    queue.add(neighbour);
                }
            }
        }

        return distances.entrySet().stream()
                .filter(entry -> entry.getKey().equals(end))
                .map(Map.Entry::getValue)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);
    }

    private static void getPermutations(List<Position> original, int element, List<List<Position>> permutations) {
        for (int i = element; i < original.size(); i++) {
            java.util.Collections.swap(original, i, element);
            getPermutations(new ArrayList<>(original), element + 1, permutations);
            java.util.Collections.swap(original, element, i);
        }

        if (element == original.size() - 1) {
            permutations.add(original);
        }
    }
}
