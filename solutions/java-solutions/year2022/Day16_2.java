package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day16_2 {
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
        //Part two
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
        System.out.println(assignMyTarget(0, "AA", "AA", null, null, valves, map));
    }

    private static int performWork(
            int minutes,
            String myPosition,
            String elephantPosition,
            String myTarget,
            String elephantTarget,
            Map<String, Valve> valves,
            Map<String, Map<String, String>> map
    ) {
        if (minutes <= 4) {
            for (int i = 0; i < minutes; i++) {
                System.out.print('\t');
            }
            System.out.println(myPosition);
        }

        if (minutes == 26) {
            return 0;
        }

        int release = 0;
        for (Valve valve1 : valves.values()) {
            if (valve1.open) {
                release += valve1.rate;
            }
        }

        Valve valve = valves.get(myPosition);
        String close1 = null;

        if (myPosition.equals(myTarget)) {
            valves.put(myPosition, valve.openIt());
            close1 = myPosition;
            myTarget = null;
        }

        if (myTarget != null) {
            myPosition = map.get(myPosition).get(myTarget);
        }

        valve = valves.get(elephantPosition);
        String close2 = null;

        if (elephantPosition.equals(elephantTarget)) {
            valves.put(elephantPosition, valve.openIt());
            close2 = elephantPosition;
            elephantTarget = null;
        }

        if (elephantTarget != null) {
            elephantPosition = map.get(elephantPosition).get(elephantTarget);
        }

        release += assignMyTarget(minutes + 1, myPosition, elephantPosition, myTarget, elephantTarget, valves, map);

        if (close1 != null) {
            valves.put(close1, valves.get(close1).closeIt());
        }
        if (close2 != null) {
            valves.put(close2, valves.get(close2).closeIt());
        }

        return release;
    }

    private static int assignMyTarget(int minutes,
                                    String myPosition,
                                    String elephantPosition,
                                    String myTarget,
                                    String elephantTarget,
                                    Map<String, Valve> valves,
                                    Map<String, Map<String, String>> map) {
        if (myTarget == null) {
            Set<String> closedValves = valves.entrySet().stream().filter(it -> !it.getValue().open && it.getValue().rate > 0 && !it.getKey().equals(elephantTarget)).map(Map.Entry::getKey).collect(Collectors.toSet());

            if (closedValves.isEmpty()) {
                return assignElephantTarget(minutes, myPosition, elephantPosition, myTarget, elephantTarget, valves, map);
            }

            if (minutes < 3) {
                return closedValves.parallelStream().mapToInt(newTarget -> assignElephantTarget(minutes, myPosition, elephantPosition, newTarget, elephantTarget, new HashMap<>(valves), map)).max().getAsInt();
            }
            return closedValves.stream().mapToInt(newTarget -> assignElephantTarget(minutes, myPosition, elephantPosition, newTarget, elephantTarget, valves, map)).max().getAsInt();

        } else {
            return assignElephantTarget(minutes, myPosition, elephantPosition, myTarget, elephantTarget, valves, map);
        }
    }

    private static int assignElephantTarget(int minutes,
                                    String myPosition,
                                    String elephantPosition,
                                    String myTarget,
                                    String elephantTarget,
                                    Map<String, Valve> valves,
                                    Map<String, Map<String, String>> map) {
        if (elephantTarget == null) {
            Set<String> closedValves = valves.entrySet().stream().filter(it -> !it.getValue().open && it.getValue().rate > 0 && !it.getKey().equals(myTarget)).map(Map.Entry::getKey).collect(Collectors.toSet());

            if (closedValves.isEmpty()) {
                return performWork(minutes, myPosition, elephantPosition, myTarget, elephantTarget, valves, map);
            }

            int release = 0;
            for (String newTarget : closedValves) {
                release = Math.max(release, performWork(minutes, myPosition, elephantPosition, myTarget, newTarget, valves, map));
            }

            return release;
        } else {
            return performWork(minutes, myPosition, elephantPosition, myTarget, elephantTarget, valves, map);
        }
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
