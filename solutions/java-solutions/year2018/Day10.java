package year2018;

import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {

    private record Light(Position position, Position velocity) {}

    public static void main(String[] args) {
        //Part one
        List<Light> lights = Utils.readLines("10.txt").stream()
                .map(line -> {
                    String[] light = line
                            .replace("position=", "")
                            .split(" velocity=");
                    return new Light(getPosition(light[0]), getPosition(light[1]));
                }).toList();

        for (int i = 0; true; i++) {
            List<Light> newLights = new ArrayList<>();

            for (Light light : lights) {
                newLights.add(new Light(new Position(
                        light.position.x() + light.velocity.x(),
                        light.position.y() + light.velocity.y()),
                        light.velocity));
            }

            if (getSize(newLights) > getSize(lights)) {
                print(lights);
                //Part two
                System.out.println(i);
                break;
            }

            lights = newLights;
        }


        //Part two
    }

    private static Position getPosition(String s) {
        String[] position = s
                .replaceAll("\\s+","")
                .replace("<", "")
                .replace(">", "")
                .split(",");

        return new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    private static long getSize(List<Light> lights) {
        int minX = lights.stream().mapToInt(light -> light.position.x()).min().getAsInt();
        int maxX = lights.stream().mapToInt(light -> light.position.x()).max().getAsInt();
        int minY = lights.stream().mapToInt(light -> light.position.y()).min().getAsInt();
        int maxY = lights.stream().mapToInt(light -> light.position.y()).max().getAsInt();

        return (long) Math.abs(maxX - minX) * Math.abs(maxY - minY);
    }

    private static void print(List<Light> lights) {
        Map<Position, Boolean> sky = new HashMap<>();

        for (Light light : lights) {
            sky.put(light.position, true);
        }

        int minX = lights.stream().mapToInt(light -> light.position.x()).min().getAsInt();
        int maxX = lights.stream().mapToInt(light -> light.position.x()).max().getAsInt();
        int minY = lights.stream().mapToInt(light -> light.position.y()).min().getAsInt();
        int maxY = lights.stream().mapToInt(light -> light.position.y()).max().getAsInt();

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                Position position = new Position(j, i);
                System.out.print(sky.getOrDefault(position, false) ? "#" : " ");
            }

            System.out.println();
        }
    }
}
