package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15 {
    record Position(int x, int y) { }

    record Range(Position pos, int radius) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/15.txt"));
        Set<Position> cave = new HashSet<>();
        Set<Position> beacons = new HashSet<>();

        for (String line : lines) {
            line = line.replaceAll("Sensor at ", "");
            line = line.replaceAll("closest beacon is at ", "");

            String[] positions = line.split(": ");
            Position sensorPosition = getPosition(positions[0]);
            Position beaconPosition = getPosition((positions[1]));
            if (beaconPosition.y == 2000000) {
                beacons.add(beaconPosition);
            }

            int radius = Math.abs(sensorPosition.x - beaconPosition.x) + Math.abs(sensorPosition.y - beaconPosition.y);

            for (int y = -radius; y <= radius; y++) {
                if (sensorPosition.y + y == 2000000) {
                    int width = radius - Math.abs(y);

                    for (int x = -width; x <= width; x++) {
                        cave.add(new Position(sensorPosition.x+x, sensorPosition.y+y));
                    }
                }
            }
        }

        System.out.println(cave.size() - beacons.size());

        //Part two
        lines = Utils.readLines(Path.of("src/year2022/files/15.txt"));
        Set<Range> sensors = new HashSet<>();

        for (String line : lines) {
            line = line.replaceAll("Sensor at ", "");
            line = line.replaceAll("closest beacon is at ", "");

            String[] positions = line.split(": ");
            Position sensorPosition = getPosition(positions[0]);
            Position beaconPosition = getPosition((positions[1]));

            int radius = Math.abs(sensorPosition.x - beaconPosition.x) + Math.abs(sensorPosition.y - beaconPosition.y);

            sensors.add(new Range(sensorPosition, radius));
        }

        for (int y = 0; y <= 4000000; y++) {
            for (int x = 0; x <= 4000000; x++) {
                boolean isInRange = false;

                for (Range sensor : sensors) {
                    int dist = Math.abs(x - sensor.pos.x) + Math.abs(y - sensor.pos.y);

                    if (dist <= sensor.radius) {
                        int distX = x - sensor.pos.x;
                        int distY = y - sensor.pos.y;
                        x += sensor.radius - distX - Math.abs(distY);

                        isInRange = true;
                        break;
                    }
                }

                if (!isInRange) {
                    System.out.println(x*4000000L + y);
                    System.exit(0);
                }
            }
        }
    }

    private static Position getPosition(String s) {
        s = s.replaceAll("x=", "");

        String[] position = s.split(", y=");
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);

        return new Position(x, y);
    }
}