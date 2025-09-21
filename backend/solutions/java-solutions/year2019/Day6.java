package year2019;

import org.jetbrains.annotations.NotNull;
import year2019.utils.Utils;

import java.util.*;

public class Day6 {
    private record SpaceObject(String name, int steps) implements Comparable<SpaceObject> {
        @Override
        public int compareTo(@NotNull SpaceObject o) {
            return Integer.compare(this.steps, o.steps);
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("06.txt");
        Map<String, String> objectOrbits = new HashMap<>();

        for (String line : input) {
            objectOrbits.put(line.split("\\)")[1], line.split("\\)")[0]);
        }

        int orbits = 0;
        for (Map.Entry<String, String> entry : objectOrbits.entrySet()) {
            String currentObject = entry.getValue();

            while (currentObject != null) {
                orbits++;
                currentObject = objectOrbits.get(currentObject);
            }
        }

        System.out.println(orbits);


        //Part two
        Map<String, List<String>> spaceObjects = new HashMap<>();
        for (String line : input) {
            String object1 = line.split("\\)")[0];
            String object2 = line.split("\\)")[1];

            spaceObjects.computeIfAbsent(object1, v -> new ArrayList<>()).add(object2);
            spaceObjects.computeIfAbsent(object2, v -> new ArrayList<>()).add(object1);
        }

        Queue<SpaceObject> queue = new PriorityQueue<>();
        Set<SpaceObject> visited = new HashSet<>();
        queue.add(new SpaceObject("YOU", 0));
        visited.add(new SpaceObject("YOU", 0));

        while (!queue.isEmpty()) {
            SpaceObject current = queue.poll();

            if (Objects.equals(current.name, "SAN")) {
                System.out.println(current.steps - 2);
                break;
            }
            for (String neighbour : spaceObjects.get(current.name)) {
                SpaceObject neighbourObject = new SpaceObject(neighbour, current.steps + 1);

                if (!visited.contains(neighbourObject)) {
                    visited.add(neighbourObject);
                    queue.add(neighbourObject);
                }
            }
        }
    }
}
