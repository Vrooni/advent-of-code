package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day18 {
    record Coordinate(int x, int y, int z) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/18.txt"));
        Set<Coordinate> cubes = new HashSet<>();
        int allShownSides = 0;

        for (String line : lines) {
            String[] coorindate = line.split(",");

            int x = Integer.parseInt(coorindate[0]);
            int y = Integer.parseInt(coorindate[1]);
            int z = Integer.parseInt(coorindate[2]);

            cubes.add(new Coordinate(x, y, z));
        }

        for (Coordinate cube : cubes) {
            Coordinate left = new Coordinate(cube.x-1, cube.y, cube.z);
            Coordinate right = new Coordinate(cube.x+1, cube.y, cube.z);
            Coordinate up = new Coordinate(cube.x, cube.y+1, cube.z);
            Coordinate down = new Coordinate(cube.x, cube.y-1, cube.z);
            Coordinate front = new Coordinate(cube.x, cube.y, cube.z-1);
            Coordinate back = new Coordinate(cube.x, cube.y, cube.z+1);

            int shownSides = 6;

            if (cubes.contains(left)) {
                shownSides--;
            }
            if (cubes.contains(right)) {
                shownSides--;
            }
            if (cubes.contains(up)) {
                shownSides--;
            }
            if (cubes.contains(down)) {
                shownSides--;
            }
            if (cubes.contains(front)) {
                shownSides--;
            }
            if (cubes.contains(back)) {
                shownSides--;
            }

            allShownSides += shownSides;
        }

        System.out.println(allShownSides);

        //Part two
        Set<Coordinate> freeCubes = new HashSet<>();

        int fromX = cubes.stream().mapToInt(c -> c.x).min().getAsInt() - 1;
        int fromY = cubes.stream().mapToInt(c -> c.y).min().getAsInt() - 1;
        int fromZ = cubes.stream().mapToInt(c -> c.z).min().getAsInt() - 1;
        int toX = cubes.stream().mapToInt(c -> c.x).max().getAsInt() + 1;
        int toY = cubes.stream().mapToInt(c -> c.y).max().getAsInt() + 1;
        int toZ = cubes.stream().mapToInt(c -> c.z).max().getAsInt() + 1;
        
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                freeCubes.add(new Coordinate(x, y, fromZ));
                freeCubes.add(new Coordinate(x, y, toZ));
            }
        }

        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                freeCubes.add(new Coordinate(x, fromY, z));
                freeCubes.add(new Coordinate(x, toY, z));
            }
        }

        for (int y = fromY; y <= toY; y++) {
            for (int z = fromZ; z <= toZ; z++) {
                freeCubes.add(new Coordinate(fromX, y, z));
                freeCubes.add(new Coordinate(toX, y, z));
            }
        }

        Queue<Coordinate> cubesToGo = new ArrayDeque<>(freeCubes);
        while (!cubesToGo.isEmpty()) {
            Coordinate cube = cubesToGo.poll();

            Coordinate[] candidates = {
                    new Coordinate(cube.x-1, cube.y, cube.z),
                    new Coordinate(cube.x+1, cube.y, cube.z),
                    new Coordinate(cube.x, cube.y+1, cube.z),
                    new Coordinate(cube.x, cube.y-1, cube.z),
                    new Coordinate(cube.x, cube.y, cube.z-1),
                    new Coordinate(cube.x, cube.y, cube.z+1)
            };

            for (Coordinate c : candidates) {
                if (isInBounds(c, fromX, toX, fromY, toY, fromZ, toZ) && !cubes.contains(c) && !freeCubes.contains(c)) {
                    cubesToGo.add(c);
                    freeCubes.add(c);
                }
            }
        }

        allShownSides = 0;
        for (Coordinate cube : cubes) {
            Coordinate left = new Coordinate(cube.x-1, cube.y, cube.z);
            Coordinate right = new Coordinate(cube.x+1, cube.y, cube.z);
            Coordinate up = new Coordinate(cube.x, cube.y+1, cube.z);
            Coordinate down = new Coordinate(cube.x, cube.y-1, cube.z);
            Coordinate front = new Coordinate(cube.x, cube.y, cube.z-1);
            Coordinate back = new Coordinate(cube.x, cube.y, cube.z+1);

            if (freeCubes.contains(left)) {
                allShownSides++;
            }
            if (freeCubes.contains(right)) {
                allShownSides++;
            }
            if (freeCubes.contains(up)) {
                allShownSides++;
            }
            if (freeCubes.contains(down)) {
                allShownSides++;
            }
            if (freeCubes.contains(front)) {
                allShownSides++;
            }
            if (freeCubes.contains(back)) {
                allShownSides++;
            }
        }

        System.out.println(allShownSides);
    }

    private static boolean isInBounds(Coordinate cube, int fromX, int toX, int fromY, int toY, int fromZ, int toZ) {
        return cube.x >= fromX && cube.x <= toX
                && cube.y >= fromY && cube.y <= toY
                && cube.z >= fromZ && cube.z <= toZ;
    }
}
