package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day9 {
    record Distance(String from, String to) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2015/files/09.txt"));

        Set<String> locations = new HashSet<>();
        Map<Distance, Integer> pathCosts = new HashMap<>();

        for (String line : lines) {
            String[] splittedLine = line.split(" = ");

            String[] path = splittedLine[0].split(" to ");
            int distance = Integer.parseInt(splittedLine[1]);

            locations.add(path[0]);
            locations.add(path[1]);
            pathCosts.put(new Distance(path[0], path[1]), distance);
        }

        List<List<String>> possiblePaths = new ArrayList<>();
        getAllPossiblePaths(locations, possiblePaths, new Stack<>(), locations.size());

        int bestWay = Integer.MAX_VALUE;

        for (List<String> path : possiblePaths) {
            bestWay = Integer.min(bestWay, getCostFromPath(path, pathCosts));
        }

        System.out.println(bestWay);

        //Part two
        bestWay = Integer.MIN_VALUE;

        for (List<String> path : possiblePaths) {
            bestWay = Integer.max(bestWay, getCostFromPath(path, pathCosts));
        }

        System.out.println(bestWay);
    }

    private static void getAllPossiblePaths(Set<String> locations, List<List<String>> possiblePaths, Stack<String> thisPath, int size) {
        if (thisPath.size() == size) {
            possiblePaths.add(new ArrayList<>(thisPath));
        }

        String[] locationsCopy = locations.toArray(new String[0]);

        for(String loc : locationsCopy) {
            thisPath.push(loc);

            locations.remove(loc);
            getAllPossiblePaths(locations, possiblePaths, thisPath, size);
            locations.add(thisPath.pop());
        }
    }

    private static int getCostFromPath(List<String> path, Map<Distance, Integer> pathCosts) {
        int costs = 0;

        for (int i = 0; i <= path.size() - 2; i++) {

            Integer cost = pathCosts.get(new Distance(path.get(i), path.get(i+1)));
            if (cost == null) {
                cost = pathCosts.get(new Distance(path.get(i+1), path.get(i)));
            }

            costs += cost;
        }

        return costs;
    }
}
