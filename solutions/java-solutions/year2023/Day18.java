package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

//TODO Zeilenweise lösung mit der verlgeichen
public class Day18 {

    private enum Wall {
        UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, VERTICAL, HORIZONTAL, NONE
    }

    private record Range(Position start, Position end, boolean vertical) {}
    private record Position(int x, int y) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/18.txt"));
        List<Range> dugRanges = getDugRanges(lines, false);

        System.out.println(getPerimeter(dugRanges) + getArea(dugRanges));
//        44436


//        //Part one
//        List<String> lines = year2017.Utils.readLines(Path.of("src/year2023/files/18.txt"));
//        List<Position> dugBlocks = getDugBlocks(lines, false);
//
//        for (int i = -500; i < 500; i++) {
//            for (int j = -500; j < 500; j++) {
//                System.out.print(dugBlocks.contains(new Position(j, i)) ? '#' : '.');
//            }
//            System.out.println(i);
//        }
//        List<List<Boolean>> dugMap = getDugMap(dugBlocks);
//
//        System.out.println(year2017.Utils.sum(dugMap.stream()
//                .map(line -> line.stream().filter(dugBlock -> dugBlock).toList().size())
//                .toList()));
//
//
//        //Part two
//        dugBlocks = getDugBlocks(lines, true);
//        dugMap = getDugMap(dugBlocks);
//
//        System.out.println(year2017.Utils.sum(dugMap.stream()
//                .map(line -> line.stream().filter(dugBlock -> dugBlock).toList().size())
//                .toList()));

    }

    private static List<Range> getDugRanges(List<String> lines, boolean decode) {
        List<Range> dugRanges = new ArrayList<>();

        int x = 0;
        int y = 0;

        for (String line : lines) {
            String[] splitLine = line.split(" ");
            String decoded = splitLine[2].replace("(", "").replace(")", "");

            int times = decode
                    ? Integer.parseInt(decoded.substring(1, decoded.length()-1), 16)
                    : Integer.parseInt(splitLine[1]);

            switch (decode ? decoded.substring(decoded.length()-1) : splitLine[0]) {
                case "R", "0" -> {
                    dugRanges.add(new Range(
                            new Position(x, y),
                            new Position(x + times, y),
                            false));

                    x = x + times;
                }

                case "D", "1" -> {
                    dugRanges.add(new Range(
                            new Position(x, y),
                            new Position(x, y + times),
                            true));

                    y = y + times;
                }

                case "L", "2" -> {
                    dugRanges.add(new Range(
                            new Position(x - times, y),
                            new Position(x, y),
                            false));

                    x = x - times;
                }

                case "U", "3" -> {
                    dugRanges.add(new Range(
                            new Position(x, y - times),
                            new Position(x, y),
                            true));

                    y = y - times;
                }

                default -> throw new RuntimeException("No direction gone");
            }
        }

        return dugRanges;
    }

    private static int getPerimeter(List<Range> dugRanges) {
        return Utils.sum(dugRanges.stream().map(dugRange ->
                dugRange.vertical
                        ? Math.abs(dugRange.start.y - dugRange.end.y)
                        : Math.abs(dugRange.start.x - dugRange.end.x)).toList()
        );
    }

    private static int getArea(List<Range> dugRanges) {
        int area = 0;
        List<Range> verticalRanges = dugRanges.stream().filter(dugRange -> dugRange.vertical).collect(Collectors.toList());

        while (!verticalRanges.isEmpty()) {
            Range wall1 = getNextRange(verticalRanges);
            Range wall2 = getNextRange(verticalRanges, wall1);
            verticalRanges.remove(wall1);
            verticalRanges.remove(wall2);

            int endY = Math.min(wall1.end.y, wall2.end.y);
            if (wall1.end.y > endY) {

                verticalRanges.add(new Range(
                        new Position(wall1.start.x, endY),
                        new Position(wall1.end.x, wall1.end.y),
                        true
                ));

                wall1 = new Range(
                        new Position(wall1.start.x, wall1.start.y),
                        new Position(wall1.end.x, endY),
                        true
                );
            } else if (wall2.end.y > endY) {

                verticalRanges.add(new Range(
                        new Position(wall2.start.x, endY),
                        new Position(wall2.end.x, wall2.end.y),
                        true
                ));

                wall2 = new Range(
                        new Position(wall2.start.x, wall2.start.y),
                        new Position(wall2.end.x, endY),
                        true
                );
            }

            int width = Math.abs(wall1.start.x - wall2.start.x) - 1;
            int height = Math.abs(wall1.start.y - endY);

            area += width * height - getHorizontalWall(dugRanges, wall1, wall2); //minus horizontal wall
        }

        return area;
    }

    private static Range getNextRange(List<Range> ranges) {
        ranges.sort(Comparator.comparingInt(o -> o.start.y));
        return ranges.get(0);
    }

    private static Range getNextRange(List<Range> ranges, Range wall) {
        ranges = new ArrayList<>(ranges.stream().filter(range -> range.start.y == wall.start.y).toList());

        if (ranges.size() == 1) {
            return ranges.get(0);
        }

        ranges.sort((o1, o2) -> {
            if (o1.start.x == o2.start.x) {
                return 0;
            } else if (o1.start.x < o2.start.x) {
                return -1;
            } else {
                return 1;
            }
        });

        int index = ranges.indexOf(wall);
        return index%2 == 0 ? ranges.get(index+1) : ranges.get(index-1);
    }

    private static int getHorizontalWall(List<Range> dugRanges, Range wall1, Range wall2) {

        if (wall1.end.x > wall2.end.x) {
            Range temp = wall1;
            wall1 = wall2;
            wall2 = temp;
        }

        Range finalWall = wall1;

        int horizontalWall = 0;
        List<Range> horizontalWalls = dugRanges.stream().filter(range -> !range.vertical && range.start.y == finalWall.end.y).toList();

        for (Range wall : horizontalWalls) {

            //case no common edge
            int width = Math.abs(wall.start.x - wall.end.x);
            if (wall.start.x > wall1.end.x && wall.end.x < wall2.end.x) {
                horizontalWall += width + 1;
            }

            //case one common edge
            if (wall.start.x == wall1.end.x && wall.end.x < wall2.end.x || wall.start.x > wall1.end.x && wall.end.x == wall2.end.x) {
                horizontalWall += width;
            }

            //case two common edge
            if (wall.start.x == wall1.end.x && wall.end.x == wall2.end.x) {
                horizontalWall += width - 1;
            }
        }

        return horizontalWall;
    }

//    private static int getHorizontalWall(List<Range> dugRanges, Range wall1, Range wall2) {
//
//        if (wall1.end.x > wall2.end.x) {
//            Range temp = wall1;
//            wall1 = wall2;
//            wall2 = temp;
//        }
//
//        Range finalWall1 = wall1;
//        Range finalWall2 = wall2;
//        Range wall3 = null;
//        Range wall4 = null;
//
//        int horizontalWall = 0;
//        List<Range> horizontalRanges = dugRanges.stream().filter(range -> !range.vertical && range.start.y == finalWall1.end.y).toList();
//
//        List<Range> potentialWalls = horizontalRanges.stream().filter(range -> range.start.x == finalWall1.end.x).toList();
//        if (!potentialWalls.isEmpty()) {
//            wall3 = potentialWalls.get(0);
//        }
//
//        potentialWalls = horizontalRanges.stream().filter(range -> range.end.x == finalWall2.end.x).toList();
//        if (!potentialWalls.isEmpty()) {
//            wall4 = potentialWalls.get(0);
//        }
//
//        if (wall3 != null) {
//            horizontalWall += Math.abs(wall3.start.x - wall3.end.x);
//            //special case
//            if (wall3.equals(wall4)) {
//                return horizontalWall - 1;
//            }
//        }
//        if (wall4 != null) {
//            horizontalWall += Math.abs(wall4.start.x - wall4.end.x);
//        }
//
//        return horizontalWall;
//    }

    private static List<Position> getDugBlocks(List<String> lines, boolean decode) {
        List<Position> dugBlocks = new ArrayList<>();

        int x = 0;
        int y = 0;

        for (String line : lines) {
            String[] splitLine = line.split(" ");
            String encoded = splitLine[2].replace("(", "").replace(")", "");

            int times = decode
                    ? Integer.parseInt(encoded.substring(1, encoded.length()-1), 16)
                    : Integer.parseInt(splitLine[1]);

            switch (decode ? encoded.substring(encoded.length()-1) : splitLine[0]) {
                case "U", "3" -> {
                    for (int i = 1; i < times + 1; i++) {
                        dugBlocks.add(new Position(x, y - i));
                    }

                    y = y - times;
                }

                case "D", "1" -> {
                    for (int i = 1; i < times + 1; i++) {
                        dugBlocks.add(new Position(x, y + i));
                    }

                    y = y + times;
                }

                case "L", "2" -> {
                    for (int i = 1; i < times + 1; i++) {
                        dugBlocks.add(new Position(x - i, y));
                    }

                    x = x - times;
                }

                case "R", "0" -> {
                    for (int i = 1; i < times + 1; i++) {
                        dugBlocks.add(new Position(x + i, y));
                    }

                    x = x + times;
                }
            }
        }

        return dugBlocks;
    }

    private static List<List<Boolean>> getDugMap(List<Position> dugBlocks) {
        int minX = Utils.min(dugBlocks.stream().map(dugBlock -> dugBlock.x).toList());
        int minY = Utils.min(dugBlocks.stream().map(dugBlock -> dugBlock.y).toList());
        int maxX = Utils.max(dugBlocks.stream().map(dugBlock -> dugBlock.x).toList());
        int maxY = Utils.max(dugBlocks.stream().map(dugBlock -> dugBlock.y).toList());

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        //normalize
        dugBlocks = dugBlocks.stream().map(pos -> new Position(pos.x - minX, pos.y - minY)).toList();
        List<List<Wall>> dugMap = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            List<Wall> dugMapLine = new ArrayList<>();

            for (int x = 0; x < width; x++) {
                dugMapLine.add(getWall(new Position(x, y), dugBlocks));
            }

            dugMap.add(dugMapLine);
        }

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {
                System.out.print(dugMap.get(y).get(x) == Wall.NONE ? "." : "#");
            }

            System.out.println();
        }

        return shrink(fill(expand(dugMap)), dugBlocks);
    }

    private static Wall getWall(Position dugBlock, List<Position> dugBlocks) {
        if (!dugBlocks.contains(dugBlock)) {
                return Wall.NONE;
        }

        int x = dugBlock.x;
        int y = dugBlock.y;

         if (dugBlocks.contains(new Position(x, y+1))) {
             if (dugBlocks.contains(new Position(x+1, y))) {
                 return Wall.UP_LEFT;
             } else if (dugBlocks.contains(new Position(x-1, y))) {
                 return Wall.UP_RIGHT;
             } else if (dugBlocks.contains(new Position(x, y-1))) {
                 return Wall.VERTICAL;
             }
         }

         if (dugBlocks.contains(new Position(x, y-1))) {
             if (dugBlocks.contains(new Position(x+1, y))) {
                 return Wall.DOWN_LEFT;
             } else if (dugBlocks.contains(new Position(x-1, y))) {
                 return Wall.DOWN_RIGHT;
             } else if (dugBlocks.contains(new Position(x, y+1))) {
                 return Wall.VERTICAL;
             }
        }

         if (dugBlocks.contains(new Position(x-1, y)) && dugBlocks.contains(new Position(x+1, y))) {
             return Wall.HORIZONTAL;
         }

         return Wall.NONE;
    }

    private static List<List<Wall>> expand(List<List<Wall>> dugMap) {
        List<List<Wall>> expandDugMap = new ArrayList<>();

        for (int y = 0; y < dugMap.size() * 2; y++) {
            List<Wall> expandDugMapLine = new ArrayList<>();

            for (int x = 0; x < dugMap.get(0).size() * 2; x++) {
                expandDugMapLine.add(Wall.NONE);
            }

            expandDugMap.add(expandDugMapLine);
        }

        for (int y = 0; y < dugMap.size(); y++) {
            for (int x = 0; x < dugMap.get(0).size(); x++) {
                int x2 = x*2;
                int y2 = y*2;

                switch (dugMap.get(y).get(x)) {
                    case UP_LEFT -> {
                        expandDugMap.get(y2).set(x2, Wall.HORIZONTAL);
                        expandDugMap.get(y2).set(x2+1, Wall.HORIZONTAL);
                        expandDugMap.get(y2+1).set(x2, Wall.VERTICAL);
                        expandDugMap.get(y2+1).set(x2+1, Wall.NONE);
                    }
                    case UP_RIGHT -> {
                        expandDugMap.get(y2).set(x2, Wall.HORIZONTAL);
                        expandDugMap.get(y2).set(x2+1, Wall.HORIZONTAL);
                        expandDugMap.get(y2+1).set(x2, Wall.NONE);
                        expandDugMap.get(y2+1).set(x2+1, Wall.VERTICAL);
                    }
                    case DOWN_LEFT -> {
                        expandDugMap.get(y2).set(x2, Wall.VERTICAL);
                        expandDugMap.get(y2).set(x2+1, Wall.NONE);
                        expandDugMap.get(y2+1).set(x2, Wall.HORIZONTAL);
                        expandDugMap.get(y2+1).set(x2+1, Wall.HORIZONTAL);
                    }
                    case DOWN_RIGHT -> {
                        expandDugMap.get(y2).set(x2, Wall.NONE);
                        expandDugMap.get(y2).set(x2+1, Wall.VERTICAL);
                        expandDugMap.get(y2+1).set(x2, Wall.HORIZONTAL);
                        expandDugMap.get(y2+1).set(x2+1, Wall.HORIZONTAL);
                    }
                    case HORIZONTAL -> {
                        expandDugMap.get(y2).set(x2, Wall.NONE);
                        expandDugMap.get(y2).set(x2+1, Wall.NONE);
                        expandDugMap.get(y2+1).set(x2, Wall.HORIZONTAL);
                        expandDugMap.get(y2+1).set(x2+1, Wall.HORIZONTAL);
                    }
                    case VERTICAL -> {
                        expandDugMap.get(y2).set(x2, Wall.VERTICAL);
                        expandDugMap.get(y2).set(x2+1, Wall.NONE);
                        expandDugMap.get(y2+1).set(x2, Wall.VERTICAL);
                        expandDugMap.get(y2+1).set(x2+1, Wall.NONE);
                    }
                }
            }
        }

        return expandDugMap;
    }

    private static List<List<Boolean>> fill(List<List<Wall>> dugMap) {
        List<List<Boolean>> filledDugMap = new ArrayList<>();

        for (List<Wall> walls : dugMap) {
            boolean inside = false;
            List<Boolean> line = new ArrayList<>();

            for (Wall wall : walls) {
                line.add(wall == Wall.NONE && inside);

                if (wall == Wall.VERTICAL) {
                    inside = !inside;
                }
            }

            filledDugMap.add(line);
        }

        return filledDugMap;
    }

    private static List<List<Boolean>> shrink(List<List<Boolean>> dugMap, List<Position> dugBlocks) {
        List<List<Boolean>> shrunkDugMap = new ArrayList<>();

        for (int y = 0; y < dugMap.size()/2; y++) {
            List<Boolean> line = new ArrayList<>();

            for (int x = 0; x < dugMap.get(0).size() / 2; x++) {
                line.add(dugBlocks.contains(new Position(x, y))
                        || dugMap.get(y*2).get(x*2)
                        && dugMap.get(y*2 + 1).get(x*2)
                        && dugMap.get(y*2).get(x*2 + 1)
                        && dugMap.get(y*2 + 1).get(x*2 + 1)
                );
            }

            shrunkDugMap.add(line);
        }

        return shrunkDugMap;
    }
}

