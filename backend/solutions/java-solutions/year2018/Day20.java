package year2018;

import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;

public class Day20 {
    private static class Room {
        int x;
        int y;
        int distance;

        public Room(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("20.txt").replace("^", "").replace("$", "");
        Map<Position, Room> map = getMap(input);

        System.out.println(map.values().stream().mapToInt(room -> room.distance).max().getAsInt());


        //Part two
        System.out.println(map.values().stream().filter(room -> room.distance >= 1000).count());
    }

    private static Map<Position, Room> getMap(String path) {
        Room current = new Room(0, 0, 0);
        LinkedList<Room> stack = new LinkedList<>();

        Map<Position, Room> map = new HashMap<>();
        map.put(new Position(0, 0), current);

        for (char c : path.toCharArray()) {
            switch (c) {
                case 'N' -> {
                    int x = current.x;
                    int y = current.y - 1;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), k -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'S' -> {
                    int x = current.x;
                    int y = current.y + 1;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), k -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'W' -> {
                    int x = current.x - 1;
                    int y = current.y;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), k -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case 'E' -> {
                    int x = current.x + 1;
                    int y = current.y;
                    int distance = current.distance;

                    current = map.computeIfAbsent(new Position(x, y), k -> new Room(x, y, Integer.MAX_VALUE));
                    current.distance = Math.min(distance + 1, current.distance);
                }
                case '(' -> stack.addFirst(current);
                case ')' -> current = stack.removeFirst();
                case '|' -> current = stack.peek();
            }
        }

        return map;
    }
}
