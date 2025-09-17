package year2017;

import java.util.*;

public class Day12 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("12.txt");
        Map<String, List<String>> programs = new HashMap<>();

        for (String line : input) {
            String[] splitLine = line.split( " <-> " );
            programs.put(splitLine[0], List.of(splitLine[1].split(", ")));
        }

        System.out.println(getGroupFor("0", programs).size());


        //Part two
        List<List<String>> groups = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String programName = String.valueOf(i);
            if (groups.stream().anyMatch(g -> g.contains(programName))) {
                continue;
            }

            groups.add(getGroupFor(programName, programs));
        }

        System.out.println(groups.size());
    }

    private static List<String> getGroupFor(String programName, Map<String, List<String>> programs) {
        Queue<String> queue = new LinkedList<>();
        List<String> group = new ArrayList<>();

        queue.offer(programName);
        group.add(programName);

        while (!queue.isEmpty()) {
            String program = queue.poll();
            List<String> neighbours = programs.getOrDefault(program, new ArrayList<>());

            for (String neighbour : neighbours) {
                if (!group.contains(neighbour)) {
                    queue.offer(neighbour);
                    group.add(neighbour);
                }
            }
        }

        return group;
    }
}
