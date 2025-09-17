package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day17 {
    private enum Direction {
        UP, DOWN, RIGHT, LEFT, NONE
    }

    record Node(int x, int y, Direction direction, int sameDirection) {}
    record Element(Node node, int heatLoss) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/17.txt"));
        List<List<Integer>> map = lines.stream().map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).toList()).toList();

        Set<Node> visited = new HashSet<>();
        Queue<Element> queue = new PriorityQueue<>(Comparator.comparingInt(element -> element.heatLoss));
        queue.add(new Element(new Node(0, 0, Direction.NONE, 1),0));

        int heatLoss = 0;
        while (!queue.isEmpty()) {
            Element current = queue.poll();

            if (visited.contains(current.node())) {
                continue;
            }

            if (current.node.x == map.get(0).size() - 1 && current.node.y == map.size() - 1) {
                heatLoss = current.heatLoss;
                break;
            }

            visited.add(current.node);
            queue.addAll(getNeighbours(current, map));
        }

        System.out.println(heatLoss);


        //Part two
        visited = new HashSet<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(element -> element.heatLoss));
        queue.add(new Element(new Node(0, 1, Direction.DOWN, 1),map.get(1).get(0)));
        queue.add(new Element(new Node(1, 0, Direction.RIGHT, 1),map.get(0).get(1)));

        heatLoss = 0;
        while (!queue.isEmpty()) {
            Element current = queue.poll();

            if (visited.contains(current.node())) {
                continue;
            }

            if (current.node.x == map.get(0).size() - 1 && current.node.y == map.size() - 1 && current.node.sameDirection >= 4) {
                heatLoss = current.heatLoss;
                break;
            }

            visited.add(current.node);
            queue.addAll(getNeighbours2(current, map));
        }

        System.out.println(heatLoss);
    }

    private static List<Element> getNeighbours(Element element, List<List<Integer>> map) {
        List<Element> neighbours = new ArrayList<>();
        Node node = element.node;

        int x = element.node.x;
        int y = element.node.y;

        //up
        if (y-1 >= 0 && node.direction != Direction.DOWN) {
            if (node.direction != Direction.UP || node.sameDirection < 3) {
                int sameDirection = node.direction == Direction.UP ? node.sameDirection + 1 : 1;
                neighbours.add(new Element(new Node(x, y-1, Direction.UP, sameDirection), element.heatLoss + map.get(y-1).get(x)));
            }
        }

        //down
        if (y+1 < map.size() && node.direction != Direction.UP) {
            if (node.direction != Direction.DOWN || node.sameDirection < 3) {
                int sameDirection = node.direction == Direction.DOWN ? node.sameDirection + 1 : 1;
                neighbours.add(new Element(new Node(x, y+1, Direction.DOWN, sameDirection), element.heatLoss + map.get(y+1).get(x)));
            }
        }

        //left
        if (x-1 >= 0 && node.direction != Direction.RIGHT) {
            if (node.direction != Direction.LEFT || node.sameDirection < 3) {
                int sameDirection = node.direction == Direction.LEFT ? node.sameDirection + 1 : 1;
                neighbours.add(new Element(new Node(x-1, y, Direction.LEFT, sameDirection), element.heatLoss + map.get(y).get(x-1)));
            }
        }

        //right
        if (x+1 < map.get(0).size() && node.direction != Direction.LEFT) {
            if (node.direction != Direction.RIGHT || node.sameDirection < 3) {
                int sameDirection = node.direction == Direction.RIGHT ? node.sameDirection + 1 : 1;
                neighbours.add(new Element(new Node(x+1, y, Direction.RIGHT, sameDirection), element.heatLoss + map.get(y).get(x+1)));
            }
        }

        return neighbours;
    }

    private static List<Element> getNeighbours2(Element element, List<List<Integer>> map) {
        List<Element> neighbours = new ArrayList<>();
        Node node = element.node;

        int x = element.node.x;
        int y = element.node.y;

        //up
        if (y-1 >= 0 && node.direction != Direction.DOWN) {
            int sameDirection = node.sameDirection + 1;
            int heatLoss = element.heatLoss + map.get(y - 1).get(x);

            if (node.direction != Direction.UP && node.sameDirection >=4) {
                neighbours.add(new Element(new Node(x, y - 1, Direction.UP, 1), heatLoss));
            }

            if (node.direction == Direction.UP && node.sameDirection < 10) {
                neighbours.add(new Element(new Node(x, y - 1, Direction.UP, sameDirection), heatLoss));
            }
        }

        //down
        if (y+1 < map.size() && node.direction != Direction.UP) {
            int sameDirection = node.sameDirection + 1;
            int heatLoss = element.heatLoss + map.get(y+1).get(x);

            if (node.direction != Direction.DOWN && node.sameDirection >= 4) {
                neighbours.add(new Element(new Node(x, y+1, Direction.DOWN, 1), heatLoss));
            }

            if (node.direction == Direction.DOWN && node.sameDirection < 10) {
                neighbours.add(new Element(new Node(x, y+1, Direction.DOWN, sameDirection), element.heatLoss + map.get(y+1).get(x)));
            }
        }

        //left
        if (x-1 >= 0 && node.direction != Direction.RIGHT) {
            int sameDirection = node.sameDirection + 1;
            int heatLoss = element.heatLoss + map.get(y).get(x-1);

            if (node.direction != Direction.LEFT && node.sameDirection >= 4) {
                neighbours.add(new Element(new Node(x-1, y, Direction.LEFT, 1), heatLoss));
            }

            if (node.direction == Direction.LEFT && node.sameDirection < 10) {
                neighbours.add(new Element(new Node(x-1, y, Direction.LEFT, sameDirection), heatLoss));
            }
        }

        //right
        if (x+1 < map.get(0).size() && node.direction != Direction.LEFT) {
            int sameDirection = node.sameDirection + 1;
            int heatLoss = element.heatLoss + map.get(y).get(x+1);

            if (node.direction != Direction.RIGHT && node.sameDirection >= 4) {
                neighbours.add(new Element(new Node(x+1, y, Direction.RIGHT, 1), heatLoss));
            }

            if (node.direction == Direction.RIGHT && node.sameDirection < 10) {
                neighbours.add(new Element(new Node(x+1, y, Direction.RIGHT, sameDirection), heatLoss));
            }
        }

        return neighbours;
    }
}
