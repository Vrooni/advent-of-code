package year2024;

import year2024.utils.Position;
import year2024.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private record Robot(int x, int y, int vx, int vy) {}
    public static void main(String[] args) throws InterruptedException, IOException {
        //Part one
        List<String> input = Utils.readLines("14.txt");
        List<Robot> robots = new ArrayList<>();

        for (String line : input) {
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            matcher.find();

            robots.add(new Robot(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
            ));
        }

        List<Position> positionOfRobots = move(robots, 101, 103, 100);
        int width = 101;
        int height = 103;

        List<Position> firstQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() < width/2 && position.y() < height/2)
                .toList();

        List<Position> secondQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() > width/2 && position.y() < height/2)
                .toList();


        List<Position> thirdQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() < width/2 && position.y() > height/2)
                .toList();

        List<Position> fourthQuadrant = positionOfRobots.stream()
                .filter(position -> position.x() > width/2 && position.y() > height/2)
                .toList();

        System.out.println(firstQuadrant.size() * secondQuadrant.size() * thirdQuadrant.size() * fourthQuadrant.size());


        //Part two
        for (int i = 1; ; i++) {
            positionOfRobots = move(robots, width, height, i);

            for (int y = 0; y < height; y++) {
                int finalY = y;
                List<Integer> line = new ArrayList<>(positionOfRobots.stream()
                        .filter(pos -> pos.y() == finalY)
                        .map(Position::x)
                        .toList());

                line.sort(Integer::compare);

                if (maxSequence(line) >= 10) {
                    System.out.println(i);
                    print(positionOfRobots, width, height);
                    return;
                }

            }
        }
    }

    private static List<Position> move(List<Robot> robots, int width, int height, int seconds) {
       return robots.stream().map(robot -> {
            int newX = (robot.x + robot.vx * seconds) % width;
            int newY = (robot.y + robot.vy * seconds) % height;

            newX = newX < 0 ? width + newX : newX;
            newY = newY < 0 ? height + newY : newY;
            return new Position(newX, newY);
        }).toList();
    }

    private static int maxSequence(List<Integer> line) {
        int maxSequence = 0;
        int sequence = 0;

        for (int i = 0; i < line.size()-1; i++) {
            if (line.get(i+1) - line.get(i) == 1) {
                sequence++;
            } else {
                maxSequence = Math.max(sequence, maxSequence);
                sequence = 0;
            }
        }

        return maxSequence;
    }

    private static void print(List<Position> positionOfRobots, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(positionOfRobots.contains(new Position(x, y)) ? "#" : " ");
            }

            System.out.println();
        }
    }
}
