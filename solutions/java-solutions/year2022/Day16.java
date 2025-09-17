package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    record Valve(
            boolean open,
            int rate,
            List<String> adjacent
    ) {
        Valve openIt() {
            return new Valve(true, rate, adjacent);
        }

        Valve closeIt() {
            return new Valve(false, rate, adjacent);
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/16.txt"));
        Map<String, Valve> valves = new HashMap<>();

        for (String line : lines) {
            String key = line.substring(6, 8);
            int rate = Integer.parseInt(line.substring(23, line.indexOf(';')));
            List<String> adjacent = new ArrayList<>();

            String[] splittedLine = line.split(",");
            for (String valve : splittedLine) {
                adjacent.add(valve.substring(valve.length() - 2));
            }

            valves.put(key, new Valve(false, rate, adjacent));
        }

        Map<String, Map<String, String>> map = getMap(valves);
        System.out.println(searchBestWay(0, "AA", null, valves, map));
    }

    private static int searchBestWay(int minutes, String position, String target, Map<String, Valve> valves, Map<String, Map<String, String>> map) {
        if (minutes == 30) {
            return 0;
        }

        Valve valve = valves.get(position);
        int release = 0;

        boolean timePassed = true;

        if (target == null) {
            Set<String> closedValves = valves.entrySet().stream().filter(it -> !it.getValue().open && it.getValue().rate > 0).map(Map.Entry::getKey).collect(Collectors.toSet());
            for (String newTarget : closedValves) {
                release = Math.max(release, searchBestWay(minutes, position, newTarget, valves, map));
            }

            if (closedValves.isEmpty()) {
                release = searchBestWay(minutes + 1, position, null, valves, map);
            } else {
                timePassed = false;
            }

        } else if (position.equals(target)) {
            valves.put(position, valve.openIt());
            release = searchBestWay(minutes + 1, position, null, valves, map);
            valves.put(position, valve.closeIt());
        } else {
            release = searchBestWay(minutes + 1, map.get(position).get(target), target, valves, map);
        }

        if (timePassed) {
            for (Valve valve1 : valves.values()) {
                if (valve1.open) {
                    release += valve1.rate;
                }
            }
        }

        return release;
    }


    record Edge(String origin, String to) {
    }

    private static Map<String, Map<String, String>> getMap(Map<String, Valve> valves) {
        Map<String, Map<String, String>> map = new HashMap<>();

        for (String from : valves.keySet()) {
            ArrayDeque<Edge> valveDeque = new ArrayDeque<>();
            map.put(from, new HashMap<>());

            for (String neighbor : valves.get(from).adjacent) {
                valveDeque.addLast(new Edge(neighbor, neighbor));
                map.get(from).put(neighbor, neighbor);
            }

            while (!valveDeque.isEmpty()) {
                Edge walk = valveDeque.removeFirst();

                for (String neighbor : valves.get(walk.to).adjacent) {
                    if (!map.get(from).containsKey(neighbor)) {
                        valveDeque.addLast(new Edge(walk.origin, neighbor));
                        map.get(from).put(neighbor, walk.origin);
                    }
                }
            }
        }

        return map;
    }
}