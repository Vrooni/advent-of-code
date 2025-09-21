package year2024;

import org.jetbrains.annotations.NotNull;
import year2024.utils.Direction;
import year2024.utils.Position;
import year2024.utils.Utils;

import java.util.*;

public class Day16 {
    private record Node(int x, int y, int score, Direction direction) implements Comparable<Node> {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Node other = (Node) obj;
            return x == other.x && y == other.y && direction == other.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }

        @Override
        public int compareTo(@NotNull Node o) {
            return Integer.compare(this.score, o.score);
        }
    }

    public static void main(String[] args) {
        //Part one
        char[][] map = Utils.readLines("16.txt").stream().map(String::toCharArray).toArray(char[][]::new);

        Position start = getStart(map);
        int minScore = getMinScore(start, map);

        System.out.println(minScore);

        //Part two
        Map<Node, Integer> distances = getDistances(start, map);
        Set<Position> sitPositions = new HashSet<>();

        Position end = distances.keySet().stream()
                .filter(node -> map[node.y][node.x] == 'E').findFirst()
                .map(node -> new Position(node.x, node.y)).get();

        addSitPositions(new Node(end.x(), end.y(), minScore, Direction.UP), start, distances, sitPositions, map);
        addSitPositions(new Node(end.x(), end.y(), minScore, Direction.DOWN), start, distances, sitPositions, map);
        addSitPositions(new Node(end.x(), end.y(), minScore, Direction.LEFT), start, distances, sitPositions, map);
        addSitPositions(new Node(end.x(), end.y(), minScore, Direction.RIGHT), start, distances, sitPositions, map);

        System.out.println(sitPositions.size());
    }

    private static void addSitPositions(Node end, Position start, Map<Node, Integer> distances, Set<Position> sitPositions, char[][] map) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(end);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            sitPositions.add(new Position(current.x, current.y));

            if (current.x == start.x() && current.y == start.y()) {
                continue;
            }

            List<Node> neighbours = getNeighboursReversed(current, map);
            for (Node neighbour : neighbours) {
                if (neighbour.score == distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    queue.add(neighbour);
                }
            }
        }
    }

    private static Map<Node, Integer> getDistances(Position start, char[][] map) {
        Node startNode = new Node(start.x(), start.y(), 0, Direction.RIGHT);

        Map<Node, Integer> distances = new HashMap<>();
        Queue<Node> queue = new PriorityQueue<>();
        distances.put(startNode, 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (map[current.y][current.x] == 'E') {
                continue;
            }

            List<Node> neighbours = getNeighbours(current, map);
            for (Node neighbour : neighbours) {
                if (neighbour.score < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    queue.add(neighbour);
                    distances.put(neighbour, neighbour.score);
                }
            }
        }

        return distances;
    }

    private static int getMinScore(Position start, char[][] map) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new PriorityQueue<>();
        visited.add(new Node(start.x(), start.y(), 0, Direction.RIGHT));
        queue.add(new Node(start.x(), start.y(), 0, Direction.RIGHT));

        int minScore = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (map[current.y][current.x] == 'E' && current.score <= minScore) {
                minScore = current.score;
            }

            List<Node> neighbours = getNeighbours(current, map);
            for (Node neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }

        return minScore;
    }

    private static Position getStart(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'S') {
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0);
    }

    private static List<Node> getNeighbours(Node current, char[][] map) {
        List<Node> neighbours = new ArrayList<>();

        Node turned = new Node(current.x, current.y, current.score+1000, current.direction.next());
        if (canMove(map, nextPosition(turned))) {
            neighbours.add(turned);
        }

        turned = new Node(current.x, current.y, current.score+1000, current.direction.prev());
        if (canMove(map, nextPosition(turned))) {
            neighbours.add(turned);
        }

        Position pos = nextPosition(current);
        if (canMove(map, pos)) {
            neighbours.add(new Node(pos.x(), pos.y(), current.score+1, current.direction));
        }

        return neighbours;
    }

    private static List<Node> getNeighboursReversed(Node current, char[][] map) {
        List<Node> neighbours = new ArrayList<>();

        Node turned = new Node(current.x, current.y, current.score-1000, current.direction.next());
        if (canMove(map, nextPositionReversed(turned))) {
            neighbours.add(turned);
        }

        turned = new Node(current.x, current.y, current.score-1000, current.direction.prev());
        if (canMove(map, nextPositionReversed(turned))) {
            neighbours.add(turned);
        }

        Position pos = nextPositionReversed(current);
        if (canMove(map, pos)) {
            neighbours.add(new Node(pos.x(), pos.y(), current.score-1, current.direction));
        }

        return neighbours;
    }

    private static Position nextPosition(Node current) {
        return switch (current.direction) {
            case LEFT -> new Position(current.x-1, current.y);
            case RIGHT -> new Position(current.x+1, current.y);
            case UP -> new Position(current.x, current.y-1);
            case DOWN -> new Position(current.x, current.y+1);
        };
    }

    private static Position nextPositionReversed(Node current) {
        return switch (current.direction) {
            case LEFT -> new Position(current.x+1, current.y);
            case RIGHT -> new Position(current.x-1, current.y);
            case UP -> new Position(current.x, current.y+1);
            case DOWN -> new Position(current.x, current.y-1);
        };
    }

    private static boolean canMove(char[][] map, Position pos) {
        return map[pos.y()][pos.x()] == '.' || map[pos.y()][pos.x()] == 'E' || map[pos.y()][pos.x()] == 'S';
    }
}
