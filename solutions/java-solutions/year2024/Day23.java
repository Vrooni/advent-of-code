package year2024;

import year2024.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 {
    private static final Map<String, List<String>> computers = new HashMap<>();
    private static final Set<String> seenComputers = new HashSet<>();
    private static Set<Connection> connections = new HashSet<>();

    private record Connection(List<String> computers) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Connection that = (Connection) o;
            return new HashSet<>(computers).equals(new HashSet<>(that.computers));
        }

        @Override
        public int hashCode() {
            return Objects.hash(new HashSet<>(computers));
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("23.txt");

        for (String line : input) {
            String[] splitLines = line.split("-");
            String computer1 = splitLines[0];
            String computer2 = splitLines[1];

            computers.computeIfAbsent(computer1, c -> new ArrayList<>()).add(computer2);
            computers.computeIfAbsent(computer2, c -> new ArrayList<>()).add(computer1);
        }

        for (Map.Entry<String, List<String>> entry : computers.entrySet()) {
            String computer1 = entry.getKey();
            for (String computer2 : entry.getValue()) {
                for (String computer3 : computers.get(computer2)) {
                    if (entry.getValue().contains(computer3)) {
                        connections.add(new Connection(List.of(computer1, computer2, computer3)));
                    }
                }
            }
        }

        System.out.println(connections.stream()
                .filter(connection -> connection.computers.get(0).startsWith("t")
                        || connection.computers.get(1).startsWith("t")
                        || connection.computers.get(2).startsWith("t"))
                .count()
        );


        //Part two
        connections = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : computers.entrySet()) {
            addConnections(entry.getKey(), entry.getValue(), new ArrayList<>());
        }

       Connection biggestConnection = connections.stream()
               .sorted((o1, o2) -> Integer.compare(o2.computers.size(), o1.computers.size()))
               .toList().get(0);

        String answer = biggestConnection.computers.stream().sorted().collect(Collectors.joining(","));
        System.out.println(answer);
    }

    private static void addConnections(String computer, List<String> neighbours, List<String> connection) {
        if (connection.stream().allMatch(computer1 -> computers.get(computer1).contains(computer))) {
            connection.add(computer);
            seenComputers.add(computer);

            for (String computer1 : neighbours) {
                if (seenComputers.contains(computer1)) {
                    connections.add(new Connection(connection));
                } else {
                    List<String> connectionCopy = new ArrayList<>(connection);
                    addConnections(computer1, computers.get(computer1), connectionCopy);
                }
            }
        } else {
            connections.add(new Connection(connection));
        }
    }
}
