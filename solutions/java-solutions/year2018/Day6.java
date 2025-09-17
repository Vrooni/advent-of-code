package year2018;

import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("06.txt");
        Map<Position, Integer> coordinates = getCoordinates(input);

        Position from = new Position(
                coordinates.keySet().stream().mapToInt(Position::x).min().getAsInt(),
                coordinates.keySet().stream().mapToInt(Position::y).min().getAsInt()
        );

        Position to = new Position(
                coordinates.keySet().stream().mapToInt(Position::x).max().getAsInt(),
                coordinates.keySet().stream().mapToInt(Position::y).max().getAsInt()
        );

        Map<Integer, Integer> areaSizes = new HashMap<>();
        for (int i = from.y(); i <= to.y(); i++) {
            for (int j = from.x(); j <= to.x(); j++) {
                int coordinate = getNearestCoordinate(coordinates, new Position(j, i));
                areaSizes.put(coordinate, areaSizes.getOrDefault(coordinate, 0) + 1);
            }
        }

        Set<Integer> infiniteAreas = new HashSet<>();
        for (int i = from.x() - 1; i <= to.x() + 1; i++) {
            infiniteAreas.add(getNearestCoordinate(coordinates, new Position(i, from.y() - 1)));
            infiniteAreas.add(getNearestCoordinate(coordinates, new Position(i, to.y() + 1)));
        }

        for (int i = from.y() - 1; i <= to.y() + 1; i++) {
            infiniteAreas.add(getNearestCoordinate(coordinates, new Position(from.x() - 1, i)));
            infiniteAreas.add(getNearestCoordinate(coordinates, new Position(to.x() + 1, i)));
        }

        int maxFiniteArea = areaSizes.entrySet().stream()
                .filter(entry -> !infiniteAreas.contains(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .max().getAsInt();
        System.out.println(maxFiniteArea);


        //Part two
        int saveCoordinates = 0;
        for (int i = from.y(); i <= to.y(); i++) {
            for (int j = from.x(); j <= to.x(); j++) {
                int distance = 0;

                for (Position position : coordinates.keySet()) {
                    distance += Math.abs(position.x() - j) + Math.abs(position.y() - i);
                    if (distance >= 10000) {
                        break;
                    }
                }

                if (distance < 10000) {
                    saveCoordinates++;
                }
            }
        }

        System.out.println(saveCoordinates);
    }

    private static Map<Position, Integer> getCoordinates(List<String> input) {
        Map<Position, Integer> coordinates = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            String coordinate = input.get(i);
            String[] splitCoordinate = coordinate.split(", ");

            coordinates.put(new Position(
                    Integer.parseInt(splitCoordinate[0]),
                    Integer.parseInt(splitCoordinate[1])), i+1);
        }

        return coordinates;
    }

    private static int getNearestCoordinate(Map<Position, Integer> coordinates, Position current) {
        if (coordinates.getOrDefault(current, -1) > 0) {
            return coordinates.get(current);
        }

        for (int i = 1; true; i++) {
            Set<Integer> candidates = new HashSet<>();

            for (int j = -i; j <= i; j++) {
                int k = i - Math.abs(j);

                Position p1 = new Position(current.x() + j, current.y() + k);
                Position p2 = new Position(current.x() + j, current.y() - k);
                int c1 = coordinates.getOrDefault(p1, -1);
                int c2 = coordinates.getOrDefault(p2, -1);

                if (c1 > 0) {
                    candidates.add(c1);
                }
                if (c2 > 0) {
                    candidates.add(c2);
                }
            }

            if (candidates.size() == 1) {
                return new ArrayList<>(candidates).get(0);
            } else if (candidates.size() > 1) {
                return 0;
            }
        }
    }
}
