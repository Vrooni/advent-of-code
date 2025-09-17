package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day19_2 {
    record Blueprint(int id, int oreCosts, int clayCosts, int[] obsidianCosts, int[] geodeCosts) {}
    record Ressource(int ore, int clay, int obsidian, int geodes) { }
    enum Robot {
        ORE, CLAY, OBSIDIAN, GEODE, NONE
    }
    public static void main(String[] args) throws IOException {
        //Part two
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/19.txt"));
        List<Blueprint> blueprints = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String line = lines.get(i);
            line = line.replaceAll("[^0-9]", " ");
            line = line.trim();
            line = line.replaceAll(" +", " ");

            String[] numbersAsStr = line.split(" ");
            int[] numbers = Arrays.stream(numbersAsStr).mapToInt(Integer::parseInt).toArray();

            blueprints.add(new Blueprint(numbers[0], numbers[1], numbers[2], new int[]{numbers[3], numbers[4]}, new int[]{numbers[5], numbers[6]}));
        }

        Map<Robot, Integer> activeRobots = new HashMap<>();
        activeRobots.put(Robot.ORE, 1);
        activeRobots.put(Robot.CLAY, 0);
        activeRobots.put(Robot.OBSIDIAN, 0);
        activeRobots.put(Robot.GEODE, 0);

        int total = IntStream.range(0, blueprints.size()).parallel().map(index -> {
            Blueprint blueprint = blueprints.get(index);
            int result = getObsidian(blueprint, activeRobots, new HashSet<>(), new Ressource(0, 0, 0, 0), 0, 0);
            System.out.println(index + ": " + result);

            return result;
        }).reduce(1, (a, b) -> a*b);

        System.out.println(total);
    }

    private static int getObsidian(Blueprint blueprint, Map<Robot, Integer> activeRobots, Set<Robot> possibleRobot, Ressource ressource, int minutes, int bestOverall) {
        if (minutes == 32) {
            return ressource.geodes;
        }

        int remaining = 32 - minutes;
        int bestCase = ressource.geodes + activeRobots.get(Robot.GEODE) * remaining + (remaining * (remaining + 1)) / 2;
        if (bestCase <= bestOverall) {
            return 0;
        }

        //possible robots
        Set<Robot> newPossibleRobots = new HashSet<>();

        if (ressource.ore >= blueprint.oreCosts) {
            newPossibleRobots.add(Robot.ORE);
        }
        if (ressource.ore >= blueprint.clayCosts) {
            newPossibleRobots.add(Robot.CLAY);
        }
        if (ressource.ore >= blueprint.obsidianCosts[0] && ressource.clay >= blueprint.obsidianCosts[1]) {
            newPossibleRobots.add(Robot.OBSIDIAN);
        }
        if (ressource.ore >= blueprint.geodeCosts[0] && ressource.obsidian >= blueprint.geodeCosts[1]) {
            newPossibleRobots.add(Robot.GEODE);
        }

        //optimizing
        int possibles = newPossibleRobots.size();
        newPossibleRobots.removeAll(possibleRobot);

        if (possibles != 4) {
            newPossibleRobots.add(Robot.NONE);
        }

        //increase resources
        Ressource newResources = new Ressource(
                ressource.ore + activeRobots.get(Robot.ORE),
                ressource.clay + activeRobots.get(Robot.CLAY),
                ressource.obsidian + activeRobots.get(Robot.OBSIDIAN),
                ressource.geodes + activeRobots.get(Robot.GEODE)
        );

        //create new robot
        int geode = -1;
        for (Robot robotToBuild : newPossibleRobots) {
            Map<Robot, Integer> newActiveRobots = new HashMap<>(activeRobots);
            Ressource currentResources = newResources;

            if (robotToBuild != Robot.NONE) {
                newActiveRobots.put(robotToBuild, activeRobots.get(robotToBuild) + 1);

                //decrease ores
                switch (robotToBuild) {
                    case ORE -> currentResources = new Ressource(
                            newResources.ore - blueprint.oreCosts,
                            newResources.clay, newResources.obsidian,
                            newResources.geodes);
                    case CLAY -> currentResources = new Ressource(
                            newResources.ore - blueprint.clayCosts,
                            newResources.clay,
                            newResources.obsidian,
                            newResources.geodes);
                    case OBSIDIAN -> currentResources = new Ressource(
                            newResources.ore - blueprint.obsidianCosts[0],
                            newResources.clay - blueprint.obsidianCosts[1],
                            newResources.obsidian,
                            newResources.geodes);
                    case GEODE -> currentResources = new Ressource(
                            newResources.ore - blueprint.geodeCosts[0],
                            newResources.clay,
                            newResources.obsidian - blueprint.geodeCosts[1],
                            newResources.geodes);
                }

                geode = Math.max(geode, getObsidian(blueprint, newActiveRobots, new HashSet<>(), currentResources, minutes + 1, Math.max(geode, bestOverall)));
            } else {
                geode = Math.max(geode, getObsidian(blueprint, newActiveRobots, newPossibleRobots, currentResources, minutes + 1, Math.max(geode, bestOverall)));

            }
        }

        return geode;
    }
}
