package year2018;

import year2018.utils.Utils;

import java.util.*;

public class Day25 {
    private record Point(int x, int y, int z, int t) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("25.txt");
        List<Point> points = new ArrayList<>();
        Map<Point, List<Point>> pointNeighbours = new HashMap<>();

        readInput(input, points, pointNeighbours);
        int constellations = 0;

        while (!points.isEmpty()) {
            List<Point> constellation = new ArrayList<>();
            constellation.add(points.get(0));

            Queue<Point> queue = new LinkedList<>();
            queue.add(points.get(0));

            while (!queue.isEmpty()) {
                Point current = queue.poll();
                List<Point> neighbours = pointNeighbours.get(current);

                for (Point neighbour : neighbours) {
                    if (!constellation.contains(neighbour)) {
                        constellation.add(neighbour);
                        queue.add(neighbour);
                    }
                }
            }

            points.removeAll(constellation);
            constellations++;
        }

        System.out.println(constellations);
    }

    private static void readInput(List<String> input, List<Point> points, Map<Point, List<Point>> pointNeighbours) {
        for (String line : input) {
            String[] point = line.split(",");
            points.add(new Point(
                    Integer.parseInt(point[0]),
                    Integer.parseInt(point[1]),
                    Integer.parseInt(point[2]),
                    Integer.parseInt(point[3])
            ));
        }

        for (Point point : points) {
            List<Point> neighbours = points.stream().filter(neighbour -> {
                int distance = Math.abs(point.x - neighbour.x) +
                        Math.abs(point.y - neighbour.y) +
                        Math.abs(point.z - neighbour.z) +
                        Math.abs(point.t - neighbour.t);
                return distance > 0 && distance <=3;
            }).toList();

            pointNeighbours.put(point, neighbours);
        }
    }
}
