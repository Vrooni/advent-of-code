import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_1 {
    record Point(int x, int y) {
    }

    record Pipe(Point p1, Point p2) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        Map<Point, Pipe> pipes = readInput(lines);
        Point start = getStart(lines);

        List<Point> currentPositions = new ArrayList<>();
        List<Point> previousPositions = new ArrayList<>();
        int steps = 1;

        for (Map.Entry<Point, Pipe> entry : pipes.entrySet()) {
            if (entry.getValue().p1.equals(start) || entry.getValue().p2.equals(start)) {
                currentPositions.add(entry.getKey());
                previousPositions.add(start);
            }
        }

        while (true) {
            for (int i = 0; i < currentPositions.size(); i++) {
                Point currentPosition = currentPositions.get(i);
                Point previousPosition = previousPositions.get(i);
                Pipe pipe = pipes.get(currentPosition);

                if (!previousPosition.equals(pipe.p1)) {
                    currentPositions.set(i, pipe.p1);
                    previousPositions.set(i, currentPosition);
                } else if (!previousPosition.equals(pipe.p2)) {
                    currentPositions.set(i, pipe.p2);
                    previousPositions.set(i, currentPosition);
                }
            }

            steps++;

            List<Point> previous = new ArrayList<>();
            for (Point currentPosition : currentPositions) {
                if (previous.contains(currentPosition)) {
                    System.out.println(steps);
                    return;
                }
                previous.add(currentPosition);
            }
        }
    }

    private static Map<Point, Pipe> readInput(List<String> lines) {
        Map<Point, Pipe> pipes = new HashMap<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case 'F' -> {
                        Point p1 = new Point(x, y + 1);
                        Point p2 = new Point(x + 1, y);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                    case '7' -> {
                        Point p1 = new Point(x, y + 1);
                        Point p2 = new Point(x - 1, y);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                    case 'L' -> {
                        Point p1 = new Point(x, y - 1);
                        Point p2 = new Point(x + 1, y);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                    case 'J' -> {
                        Point p1 = new Point(x, y - 1);
                        Point p2 = new Point(x - 1, y);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                    case '|' -> {
                        Point p1 = new Point(x, y + 1);
                        Point p2 = new Point(x, y - 1);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                    case '-' -> {
                        Point p1 = new Point(x + 1, y);
                        Point p2 = new Point(x - 1, y);
                        Point pOwn = new Point(x, y);
                        pipes.put(pOwn, new Pipe(p1, p2));
                    }
                }
            }
        }

        return pipes;
    }

    private static Point getStart(List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) == 'S') {
                    return new Point(x, y);
                }
            }
        }

        return null;
    }
}
