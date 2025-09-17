package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    private record Reindeer(int speed, int duration, int pause) { }
    private static final int TOTAL_DURATION = 2503;

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/14.txt"));
        List<Reindeer> reindeer = parseInput(input);
        int longestDistance = getLongestDistance(reindeer);

        System.out.println(longestDistance);

        //Part two
        int maxPoints = getMaxPoints(reindeer);
        System.out.println(maxPoints);
    }

    private static List<Reindeer> parseInput(List<String> input) {
        List<Reindeer> reindeer = new ArrayList<>();

        for (String line : input) {
            String[] splittedLine = line.split(" ");

            int speed = Integer.parseInt(splittedLine[3]);
            int duration = Integer.parseInt(splittedLine[6]);
            int pause = Integer.parseInt(splittedLine[13]);

            reindeer.add(new Reindeer(speed, duration, pause));
        }

        return reindeer;
    }

    private static int getLongestDistance(List<Reindeer> reindeer) {
        int maxDistance = Integer.MIN_VALUE;

        for (Reindeer deer : reindeer) {
            int duration = deer.duration + deer.pause;
            int iterations = TOTAL_DURATION / duration;

            int distance = iterations * deer.speed * deer.duration;
            int remainingTime = TOTAL_DURATION - iterations * duration;

            int flyingTime = Math.min(remainingTime, deer.duration);
            distance += flyingTime * deer.speed;

            maxDistance = Math.max(distance, maxDistance);
        }

        return maxDistance;
    }

    private static int getMaxPoints(List<Reindeer> reindeer) {
        List<Integer> allPoints = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();

        for (int i = 0; i < reindeer.size(); i++) {
            allPoints.add(0);
            distances.add(0);
        }

        for (int i = 0; i < TOTAL_DURATION; i++) {
            int maxDistanceThisRound = Integer.MIN_VALUE;

            for (int j = 0; j < reindeer.size(); j++) {
                int distance = distances.get(j);

                if (i % (reindeer.get(j).duration + reindeer.get(j).pause) < reindeer.get(j).duration) {
                    distance += reindeer.get(j).speed;
                }

                maxDistanceThisRound = Math.max(distance, maxDistanceThisRound);
                distances.set(j, distance);
            }

            for (int j = 0; j < reindeer.size(); j++) {
                if (distances.get(j) == maxDistanceThisRound) {
                    allPoints.set(j, allPoints.get(j)+1);
                }
            }
        }

        int maxPoints = Integer.MIN_VALUE;

        for (int points : allPoints) {
            maxPoints = Math.max(points, maxPoints);
        }

        return maxPoints;
    }
}
