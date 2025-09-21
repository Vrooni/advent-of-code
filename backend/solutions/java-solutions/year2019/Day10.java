package year2019;

import org.jetbrains.annotations.NotNull;
import year2019.utils.Position;
import year2019.utils.Utils;

import java.util.*;

public class Day10 {
    private record Asteroid(Position position, List<Position> asteroidsInSight) {}

    private record AsteroidWithDistance(Position position, Double distance) implements Comparable<AsteroidWithDistance> {
        @Override
        public int compareTo(@NotNull AsteroidWithDistance o) {
            return Double.compare(distance, o.distance);
        }
    }

    private record DistancesForAngle(double angle, List<AsteroidWithDistance> distances) implements Comparable<DistancesForAngle> {
        @Override
        public int compareTo(@NotNull DistancesForAngle o) {
            return Double.compare(this.angle, o.angle);
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> map = Utils.readLines("10.txt");
        Map<Position, Asteroid> asteroids = getAsteroids(map);

        Position monitoringStation = getMonitoringStation(asteroids);
        System.out.println(asteroids.get(monitoringStation).asteroidsInSight.size());

        //Part two
        List<DistancesForAngle> distancesForAngles = getDistancesForAngles(asteroids, monitoringStation);
        Collections.sort(distancesForAngles);
        distancesForAngles.forEach(asteroids1 -> Collections.sort(asteroids1.distances));

        int vaporizedAsteroids = 0;
        while (true) {
            for (DistancesForAngle distancesForAngle : distancesForAngles) {
                if (!distancesForAngle.distances.isEmpty()) {
                    vaporizedAsteroids++;
                    AsteroidWithDistance vaporized = distancesForAngle.distances.remove(0);

                    if (vaporizedAsteroids == 200) {
                        System.out.println(vaporized.position.x() * 100 + vaporized.position.y());
                        return;
                    }
                }
            }
        }
    }

    private static List<DistancesForAngle> getDistancesForAngles(Map<Position, Asteroid> asteroids, Position monitoringStation) {
        Map<Double, DistancesForAngle> distancesForAngles = new HashMap<>();
        for (Position asteroid : asteroids.keySet()) {
            if (asteroid.equals(monitoringStation)) {
                continue;
            }

            double angle = getAngle(monitoringStation, asteroid);
            Position vector = new Position(asteroid.x() - monitoringStation.x(), asteroid.y() - monitoringStation.y());
            double distance = Math.sqrt(Math.pow(vector.x(), 2) + Math.pow(vector.y(), 2));

            distancesForAngles.computeIfAbsent(angle, v -> new DistancesForAngle(angle, new ArrayList<>()))
                    .distances.add(new AsteroidWithDistance(new Position(asteroid.x(), asteroid.y()), distance));
        }

        return new ArrayList<>(distancesForAngles.values().stream().toList());
    }

    private static double getAngle(Position from, Position to) {
        double angle = Math.toDegrees(Math.atan2((double) from.y() - to.y(), (double) from.x() - to.x()));
        angle -= 90;
        return angle < 0 ? angle + 360 : angle;
    }

    private static Position getMonitoringStation(Map<Position, Asteroid> asteroids) {
        Position monitoringStation = new Position(0, 0);
        int maxAsteriods = Integer.MIN_VALUE;

        for (Map.Entry<Position, Asteroid> entry : asteroids.entrySet()) {
            if (entry.getValue().asteroidsInSight.size() > maxAsteriods) {
                monitoringStation = entry.getKey();
                maxAsteriods = entry.getValue().asteroidsInSight.size();
            }
        }

        return monitoringStation;
    }

    private static Map<Position, Asteroid> getAsteroids(List<String> map) {
        Map<Position, Asteroid> asteroids = new HashMap<>();

        for (int y = 0; y < map.size(); y++) {
            String line = map.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    asteroids.put(new Position(x, y), new Asteroid(new Position(x, y), new ArrayList<>()));
                }
            }
        }

        for (Map.Entry<Position, Asteroid> entry1 : asteroids.entrySet()) {
            Position p1 = entry1.getKey();
            List<Position> asteroidsInsight = entry1.getValue().asteroidsInSight;

            for (Map.Entry<Position, Asteroid> entry2 : asteroids.entrySet()) {
                Position p2 = entry2.getKey();

                if (inSight(p1, p2, map)) {
                    asteroidsInsight.add(p2);
                }
            }
        }

        return asteroids;
    }

    private static boolean inSight(Position p1, Position p2, List<String> map) {
        if (p1.equals(p2)) {
            return false;
        }

        Position vector = new Position(p2.x() - p1.x(), p2.y() - p1.y());
        int gcd = Utils.gcd(vector.x(), vector.y());
        if (Math.abs(gcd) == 1) {
            return true;
        }

        vector = new Position(
                vector.y() == 0 ? vector.x()/Math.abs(vector.x()) : vector.x() / gcd,
                vector.x() == 0 ? vector.y()/Math.abs(vector.y()) : vector.y() / gcd
        );

        for (int i = 1; ; i++) {
            Position asteroid = new Position(p1.x() + i*vector.x(), p1.y() + i*vector.y());
            if (asteroid.equals(p2)) {
                return true;
            }

            if (map.get(asteroid.y()).charAt(asteroid.x()) == '#') {
                return false;
            }
        }
    }
}
