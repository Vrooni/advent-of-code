package year2018;

import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;
import java.util.stream.IntStream;

public class Day17 {
    private static final char SAND = '.';
    private static final char CLAY = '#';
    private static final char FLOWING_WATER = '|';
    private static final char STILL_WATER = '~';

    public static void main(String[] args) {
        //Part one
        List<Position> positions = readInput(Utils.readLines("17.txt"));

        int fromX = positions.stream().map(Position::x).min(Integer::compareTo).get() - 1;
        int toX = positions.stream().map(Position::x).max(Integer::compareTo).get() + 1;
        int fromY = positions.stream().map(Position::y).min(Integer::compareTo).get();
        int toY = positions.stream().map(Position::y).max(Integer::compareTo).get();

        char[][] map = getMap(positions, toX + 1, toY + 1);
        flow(map, 500, 0, fromX, toX, toY);

        System.out.println(getWater(map, fromX, toX, fromY, toY, true));


        //Part two
        System.out.println(getWater(map, fromX, toX, fromY, toY, false));
    }

    private static List<Position> readInput(List<String> input) {
        List<Position> positions = new ArrayList<>();

        for (String line : input) {
            String[] splitLine = line.split(", ");

            if (splitLine[0].charAt(0) == 'x') {
                int x = Integer.parseInt(splitLine[0].replace("x=", ""));
                int[] splitCoordinate = getRange(splitLine[1], "y=");
                int[] yRange = IntStream.rangeClosed(splitCoordinate[0], splitCoordinate[1]).toArray();

                for (int y : yRange) {
                    positions.add(new Position(x, y));
                }
            } else {
                int y = Integer.parseInt(splitLine[0].replace("y=", ""));
                int[] splitCoordinate = getRange(splitLine[1], "x=");
                int[] xRange = IntStream.rangeClosed(splitCoordinate[0], splitCoordinate[1]).toArray();

                for (int x : xRange) {
                    positions.add(new Position(x, y));
                }
            }
        }

        return positions;
    }

    private static int[] getRange(String coordinate, String replace) {
        return Arrays.stream(coordinate
                        .replace(replace, "")
                        .split("\\.\\."))
                .mapToInt(Integer::parseInt).toArray();
    }

    private static char[][] getMap(List<Position> positions, int width, int height) {
        char[][] map = new char[height][width];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                map[y][x] = SAND;
            }
        }

        for (Position position : positions) {
            map[position.y()][position.x()] = CLAY;
        }

        return map;
    }

    private static void flow(char[][] map, int x, int y, int fromX, int toX, int toY) {
        if (y >= toY) {
            return;
        }

        //water flows down
        if (map[y+1][x] == SAND) {
            map[y+1][x] = FLOWING_WATER;
            flow(map, x, y+1, fromX, toX, toY);
        }

        //water flows to left
        if (map[y][x-1] == SAND && (map[y+1][x] == STILL_WATER || map[y+1][x] == CLAY)) {
            map[y][x-1] = FLOWING_WATER;
            flow(map, x-1, y, fromX, toX, toY);
        }

        //water flows to left
        if (map[y][x+1] == SAND && (map[y+1][x] == STILL_WATER || map[y+1][x] == CLAY)) {
            map[y][x+1] = FLOWING_WATER;
            flow(map, x+1, y, fromX, toX, toY);
        }

        if (map[y][x] == STILL_WATER) {
            return;
        }

        //water fills
        int left = getLeftWall(map, fromX, x, y);
        int right = getRightWall(map, toX, x, y);
        if (left != -1 && right != -1 && hasFloor(map, left, right, y)) {
            for (x = left; x <= right; x++) {
                map[y][x] = STILL_WATER;
            }
        }
    }

    private static int getLeftWall(char[][] map, int fromX, int x, int y) {
        for (; x > fromX; x--) {
            if (map[y][x-1] == CLAY) {
                return x;
            }
        }

        return -1;
    }

    private static int getRightWall(char[][] map, int toX, int x, int y) {
        for (; x < toX; x++) {
            if (map[y][x+1] == CLAY) {
                return x;
            }
        }

        return -1;
    }

    private static boolean hasFloor(char[][] map, int fromX, int toX, int y) {
        for (int x = fromX; x <= toX; x++) {
            if (map[y+1][x] == SAND) {
                return false;
            }
        }

        return true;
    }

    private static int getWater(char[][] map, int fromX, int toX, int fromY, int toY, boolean flowingWater) {
        int water = 0;

        for (int y = fromY; y <= toY; y++) {
            for (int x = fromX; x <= toX; x++) {
                if (map[y][x] == STILL_WATER || flowingWater && map[y][x] == FLOWING_WATER) {
                    water++;
                }
            }
        }

        return water;
    }
}
