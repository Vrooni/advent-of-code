package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {
    record Point(int x, int y) {
        public Point updateT(Point h) {
            if (Math.abs(h.x - this.x) <= 1 && Math.abs(h.y - this.y) <= 1) {
                return this;
            }

            if (h.x == this.x) {
                if (h.y > this.y) {
                    return new Point(this.x, this.y+1);
                } else {
                    return new Point(this.x, this.y-1);
                }
            }

            if (h.y == this.y) {
                if (h.x > this.x) {
                    return new Point(this.x+1, this.y);
                } else {
                    return new Point(this.x-1, this.y);
                }
            }

            if (h.x > this.x) {
                if (h.y > this.y) {
                    return new Point(this.x+1, this.y+1);
                } else {
                    return new Point(this.x+1, this.y-1);
                }
            } else {
                if (h.y > this.y) {
                    return new Point(this.x-1, this.y+1);
                } else {
                    return new Point(this.x-1, this.y-1);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/09.txt"));
        Set<Point> visitedPoints = new HashSet<>();
        Point tPosition = new Point(0, 0);
        Point hPosition = new Point(0, 0);

        visitedPoints.add(hPosition);
        for (String line: lines) {
            String[] splittedLine = line.split(" ");
            int steps =  Integer.parseInt(splittedLine[1]);

            for (int i = 0; i < steps; i++) {
                switch (splittedLine[0]) {
                    case "R" -> hPosition = new Point(hPosition.x + 1, hPosition.y);
                    case "L" -> hPosition = new Point(hPosition.x - 1, hPosition.y);
                    case "U" -> hPosition = new Point(hPosition.x, hPosition.y - 1);
                    case "D" -> hPosition = new Point(hPosition.x, hPosition.y + 1);
                }

                tPosition = tPosition.updateT(hPosition);
                visitedPoints.add(tPosition);
            }
        }

        System.out.println(visitedPoints.size());

        //Part two
        visitedPoints = new HashSet<>();
        List<Point> knots = new ArrayList<>(IntStream.range(0, 10).mapToObj(i -> new Point(0, 0)).toList());
        visitedPoints.add(knots.get(9));

        for (String line: lines) {
            String[] splittedLine = line.split(" ");
            int steps =  Integer.parseInt(splittedLine[1]);

            for (int i = 0; i < steps; i++) {
                switch (splittedLine[0]) {
                    case "R" -> knots.set(0, new Point(knots.get(0).x + 1, knots.get(0).y));
                    case "L" -> knots.set(0, new Point(knots.get(0).x - 1, knots.get(0).y));
                    case "U" -> knots.set(0, new Point(knots.get(0).x, knots.get(0).y - 1));
                    case "D" -> knots.set(0, new Point(knots.get(0).x, knots.get(0).y + 1));
                }

                for (int j = 1; j < knots.size(); j++) {
                    knots.set(j, knots.get(j).updateT(knots.get(j-1)));

                    if (j == 9) {
                        visitedPoints.add(knots.get(j));
                    }
                }
            }
        }

        //Because we were curious
        /*int x1 = visitedPoints.stream().mapToInt(p -> p.x).min().getAsInt();
        int x2 = visitedPoints.stream().mapToInt(p -> p.x).max().getAsInt();
        int y1 = visitedPoints.stream().mapToInt(p -> p.y).min().getAsInt();
        int y2 = visitedPoints.stream().mapToInt(p -> p.y).max().getAsInt();
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                if (visitedPoints.contains(new Point(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print("·");
                }
            }

            System.out.println();
        }*/

        System.out.println(visitedPoints.size());
    }
}
