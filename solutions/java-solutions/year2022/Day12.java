package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day12 {
    record Point(int x, int y) { }
    record Candidate(List<Point> way, Point point) { }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/12.txt"));
        Map<Point, List<Point>> directionsOfPoints = new HashMap<>();

        Point start = null;
        Point end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char letter = lines.get(y).charAt(x);

                if (letter == 'S') {
                    lines.set(y, lines.get(y).replace('S', 'a'));
                    start = new Point(x, y);
                } else if (letter == 'E') {
                    lines.set(y, lines.get(y).replace('E', 'z'));
                    end = new Point(x, y);
                }
            }
        }

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char letter = lines.get(y).charAt(x);

                List<Point> directions = directionsOfPoints.computeIfAbsent(new Point(x, y),
                p -> new ArrayList<>());


                //look left
                if (x != 0 && lines.get(y).charAt(x-1) - letter <= 1) {
                    directions.add(new Point(x-1, y));
                }

                //look right
                if (x != lines.get(0).length()-1 && lines.get(y).charAt(x+1) - letter <= 1) {
                    directions.add(new Point(x+1, y));
                }

                //look up
                if (y != 0 && lines.get(y-1).charAt(x) - letter <= 1) {
                    directions.add(new Point(x, y-1));
                }

                //look down
                if (y != lines.size()-1 && lines.get(y+1).charAt(x) - letter <= 1) {
                    directions.add(new Point(x, y+1));
                }
            }
        }

        ArrayDeque<Candidate> candidates = new ArrayDeque<>();
        candidates.addLast(new Candidate(new ArrayList<>(), start));
        Set<Point> seen = new HashSet<>();
        seen.add(start);

        while (true) {
            Candidate candidate = candidates.removeFirst();

            if (candidate.point.equals(end)) {
                System.out.println(candidate.way.size());
                break;
            }

            for (Point p : directionsOfPoints.get(candidate.point)) {
                if (seen.contains(p)) {
                    continue;
                }

                List<Point> currentWay = new ArrayList<>(candidate.way);
                currentWay.add(p);
                candidates.add(new Candidate(currentWay, p));
                seen.add(p);
            }
        }

        //Part two
        directionsOfPoints = new HashMap<>();

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char letter = lines.get(y).charAt(x);

                List<Point> directions = directionsOfPoints.computeIfAbsent(new Point(x, y),
                        p -> new ArrayList<>());


                //look left
                if (x != 0 && letter - lines.get(y).charAt(x-1) <= 1) {
                    directions.add(new Point(x-1, y));
                }

                //look right
                if (x != lines.get(0).length()-1 && letter - lines.get(y).charAt(x+1) <= 1) {
                    directions.add(new Point(x+1, y));
                }

                //look up
                if (y != 0 && letter - lines.get(y-1).charAt(x) <= 1) {
                    directions.add(new Point(x, y-1));
                }

                //look down
                if (y != lines.size()-1 && letter - lines.get(y+1).charAt(x) <= 1) {
                    directions.add(new Point(x, y+1));
                }
            }
        }

        candidates = new ArrayDeque<>();
        candidates.addLast(new Candidate(new ArrayList<>(), end));
        seen = new HashSet<>();
        seen.add(end);

        while (true) {
            Candidate candidate = candidates.removeFirst();

            if (lines.get(candidate.point.y).charAt(candidate.point.x) == 'a') {
                System.out.println(candidate.way.size());
                break;
            }

            for (Point p : directionsOfPoints.get(candidate.point)) {
                if (seen.contains(p)) {
                    continue;
                }

                List<Point> currentWay = new ArrayList<>(candidate.way);
                currentWay.add(p);
                candidates.add(new Candidate(currentWay, p));
                seen.add(p);
            }
        }
    }
}
