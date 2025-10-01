import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_2 {
    record Point(int x, int y) {
    }

    record Pipe(Point p1, Point p2) {
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        Map<Point, Pipe> pipes = readInput(lines);
        Point start = getStart(lines);
        Point end = getEnd(pipes, start);

        List<Point> visited = getHistory(pipes, start, end);
        replaceStartWithPipe(lines, visited, start);
        List<List<Character>> map = getMap(lines, visited);
        markInsideAndOutside(map);

        int insideCount = 0;

        for (int y = 0; y < map.size(); y += 2) {
            for (int x = 0; x < map.get(0).size(); x += 2) {
                if (map.get(y).subList(x, x + 2).stream().allMatch(c -> c == 'I') &&
                        map.get(y + 1).subList(x, x + 2).stream().allMatch(c -> c == 'I')) {
                    insideCount++;
                }
            }
        }

        System.out.println(insideCount);
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

    private static Point getEnd(Map<Point, Pipe> pipes, Point start) {
        List<Point> currentPositions = new ArrayList<>();
        List<Point> previousPositions = new ArrayList<>();

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

            List<Point> previous = new ArrayList<>();
            for (Point currentPosition : currentPositions) {
                if (previous.contains(currentPosition)) {
                    return currentPosition;
                }
                previous.add(currentPosition);
            }
        }
    }

    private static List<Point> getHistory(Map<Point, Pipe> pipes, Point start, Point end) {
        List<Point> currentPositions = new ArrayList<>();
        List<Point> previousPositions = new ArrayList<>();
        List<Point> visited = new ArrayList<>();

        Pipe pipe = pipes.get(end);
        currentPositions.add(pipe.p1);
        currentPositions.add(pipe.p2);

        previousPositions.add(end);
        previousPositions.add(end);

        visited.add(end);
        visited.add(pipe.p1);
        visited.add(pipe.p2);

        while (true) {
            for (int i = 0; i < currentPositions.size(); i++) {
                Point currentPosition = currentPositions.get(i);
                Point previousPosition = previousPositions.get(i);
                pipe = pipes.get(currentPosition);

                if (!previousPosition.equals(pipe.p1)) {
                    currentPositions.set(i, pipe.p1);
                    previousPositions.set(i, currentPosition);
                    visited.add(pipe.p1);
                } else if (!previousPosition.equals(pipe.p2)) {
                    currentPositions.set(i, pipe.p2);
                    previousPositions.set(i, currentPosition);
                    visited.add(pipe.p2);
                }
            }

            if (visited.contains(start)) {
                return visited;
            }
        }
    }

    private static List<List<Character>> getMap(List<String> lines, List<Point> visited) {
        List<List<Character>> map = initMap(lines.size() * 2, lines.get(0).length() * 2);

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {

                if (visited.contains(new Point(x, y))) {

                    switch (lines.get(y).charAt(x)) {
                        case 'L' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '|');
                            line.set(x * 2 + 1, '.');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '_');
                            line.set(x * 2 + 1, '_');
                        }

                        case 'J' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '.');
                            line.set(x * 2 + 1, '|');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '_');
                            line.set(x * 2 + 1, '_');
                        }

                        case '7' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '_');
                            line.set(x * 2 + 1, '_');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '.');
                            line.set(x * 2 + 1, '|');
                        }

                        case 'F' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '_');
                            line.set(x * 2 + 1, '_');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '|');
                            line.set(x * 2 + 1, '.');
                        }

                        case '-' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '_');
                            line.set(x * 2 + 1, '_');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '.');
                            line.set(x * 2 + 1, '.');
                        }

                        case '|' -> {
                            List<Character> line = map.get(y * 2);
                            line.set(x * 2, '|');
                            line.set(x * 2 + 1, '.');

                            line = map.get(y * 2 + 1);
                            line.set(x * 2, '|');
                            line.set(x * 2 + 1, '.');
                        }
                    }
                } else {
                    List<Character> line = map.get(y * 2);
                    line.set(x * 2, '.');
                    line.set(x * 2 + 1, '.');

                    line = map.get(y * 2 + 1);
                    line.set(x * 2, '.');
                    line.set(x * 2 + 1, '.');
                }
            }
        }

        return map;
    }

    private static void replaceStartWithPipe(List<String> lines, List<Point> visited, Point start) {
        char startPipe = getStartPipe(
                visited.get(visited.size() - 3),
                visited.get(visited.size() - 4),
                start);

        String lineWithStart = lines.get(start.y);
        lineWithStart = lineWithStart.substring(0, start.x) + startPipe + lineWithStart.substring(start.x + 1);
        lines.set(start.y, lineWithStart);
    }

    private static char getStartPipe(Point p1, Point p2, Point start) {
        char p1Cardinal = getCardinal(start, p1);
        char p2Cardinal = getCardinal(start, p2);

        if (p1Cardinal == 'N' && p2Cardinal == 'S' || p1Cardinal == 'S' && p2Cardinal == 'N') {
            return '|';
        } else if (p1Cardinal == 'O' && p2Cardinal == 'W' || p1Cardinal == 'W' && p2Cardinal == 'O') {
            return '-';
        } else if (p1Cardinal == 'O' && p2Cardinal == 'S' || p1Cardinal == 'S' && p2Cardinal == 'O') {
            return 'F';
        } else if (p1Cardinal == 'W' && p2Cardinal == 'S' || p1Cardinal == 'S' && p2Cardinal == 'W') {
            return '7';
        } else if (p1Cardinal == 'W' && p2Cardinal == 'N' || p1Cardinal == 'N' && p2Cardinal == 'W') {
            return 'J';
        } else if (p1Cardinal == 'O' && p2Cardinal == 'N' || p1Cardinal == 'N' && p2Cardinal == 'O') {
            return 'L';
        }

        return 'E';
    }

    private static char getCardinal(Point start, Point point) {
        if (start.x == point.x && start.y > point.y) {
            return 'N';
        } else if (start.x == point.x && start.y < point.y) {
            return 'S';
        } else if (start.y == point.y && start.x > point.x) {
            return 'W';
        } else {
            return 'O';
        }
    }

    private static void markInsideAndOutside(List<List<Character>> map) {
        for (int y = 0; y < map.size(); y++) {
            boolean inside = false;

            for (int x = 0; x < map.get(0).size(); x++) {
                char symbol = map.get(y).get(x);

                if (symbol == '.') {
                    map.get(y).set(x, inside ? 'I' : 'O');
                } else if (symbol == '|') {
                    inside = !inside;
                }
            }
        }
    }

    private static List<List<Character>> initMap(int height, int width) {
        List<List<Character>> map = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            List<Character> line = new ArrayList<>();

            for (int j = 0; j < width; j++) {
                line.add('.');
            }

            map.add(line);
        }

        return map;
    }
}
