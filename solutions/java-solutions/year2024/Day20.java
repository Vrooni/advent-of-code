package year2024;

import year2024.utils.Position;
import year2024.utils.Utils;

import java.util.*;

public class Day20 {
    private record Node(int x, int y, int ns) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Node other = (Node) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        //Part one
        char[][] map = Utils.readLines("20.txt").stream().map(String::toCharArray).toArray(char[][]::new);
        Position end = getEndPosition(map);

        Map<Node, Integer> distances = new HashMap<>();
        int minDistance = getMinDistance(map, end, distances);

        System.out.println(getCheats(map, minDistance, distances, 2));


        //Part two
        System.out.println(getCheats(map, minDistance, distances, 20));
    }

    private static Position getEndPosition(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'E') {
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0);
    }

    private static int getMinDistance(char[][] map, Position end, Map<Node, Integer> distances) {
        Node endNode = new Node(end.x(), end.y(), 0);
        Queue<Node> queue = new LinkedList<>();

        queue.add(endNode);
        distances.put(endNode, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;

            if (map[y][x] == 'S') {
                continue;
            }

            for (Node neighbour : getNeighbours(map, current)) {
                if (neighbour.ns < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    queue.add(neighbour);
                    distances.put(neighbour, neighbour.ns);
                }
            }
        }

        return distances.entrySet().stream()
                .filter(entry -> map[entry.getKey().y][entry.getKey().x] == 'S')
                .mapToInt(Map.Entry::getValue).min().getAsInt();
    }

    private static List<Node> getNeighbours(char[][] map, Node current) {
        int x = current.x;
        int y = current.y;
        List<Node> neighbours = new ArrayList<>();

        if (x > 0 && map[y][x - 1] != '#') {
            neighbours.add(new Node(x - 1, y, current.ns + 1));
        }

        if (x < map[0].length-1 && map[y][x + 1] != '#') {
            neighbours.add(new Node(x + 1, y, current.ns + 1));
        }

        if (y > 0 && map[y - 1][x] != '#') {
            neighbours.add(new Node(x, y - 1, current.ns + 1));
        }

        if (y < map.length-1 && map[y + 1][x] != '#') {
            neighbours.add(new Node(x, y + 1, current.ns + 1));
        }

        return neighbours;
    }

    private static int getCheats(char[][] map, int minDistance, Map<Node, Integer> distances, int picoSeconds) {
        int cheats = 0;
        List<Integer> savedSeconds = new ArrayList<>();

        for (Node node : distances.keySet()) {
            Position position = new Position(node.x, node.y);

            for (int picoSecond = 1; picoSecond <= picoSeconds; picoSecond++) {
                List<Position> neighbours = getCheatNeighbours(map, position, picoSecond);

                for (Position neighbour : neighbours) {
                    int distance = minDistance - node.ns
                            + getDistance(position, neighbour)
                            + distances.get(new Node(neighbour.x(), neighbour.y(), 0));

                    savedSeconds.add(minDistance - distance);
                    if (minDistance - distance >= 100) {
                        cheats++;
                    }
                }
            }
        }

        savedSeconds.sort(Comparator.reverseOrder());
        return cheats;
    }

    private static List<Position> getCheatNeighbours(char[][] map, Position current, int picoSecond) {
        Set<Position> neighbours = new HashSet<>();

        for (int i = -picoSecond; i <= picoSecond; i++) {
            int y = current.y() + i;
            int distanceY = picoSecond - Math.abs(i);

            int x1 = current.x() + distanceY;
            int x2 = current.x() - distanceY;

            neighbours.add(new Position(x1, y));
            neighbours.add(new Position(x2, y));
        }

        return neighbours.stream()
                .filter(pos -> pos.y() >= 0
                        && pos.x() >= 0
                        && pos.y() < map.length
                        && pos.x() < map[0].length
                        && map[pos.y()][pos.x()] != '#')
                .toList();
    }

    private static int getDistance(Position p1, Position p2) {
        return Math.abs(p1.x() - p2.x()) + Math.abs(p1.y() - p2.y());
    }
}
