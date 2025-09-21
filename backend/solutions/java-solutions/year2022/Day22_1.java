package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day22 {
    private enum Element { NONE, EMPTY, WALL }
    private enum Direction { RIGHT , LEFT, DOWN, UP }
    private static class MyPosition {
        int x;
        int y;
        Direction direction;

        MyPosition(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        MyPosition(MyPosition position) {
            this.x = position.x;
            this.y = position.y;
            this.direction = position.direction;
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/22.txt"));
        List<List<Element>> map = createMap(lines);
        String moves = lines.get(lines.size() - 1);

        MyPosition position = moveThrowMap(map, moves);

        int directionPoint = -1;
        switch (position.direction) {
            case RIGHT -> directionPoint = 0;
            case DOWN -> directionPoint = 1;
            case LEFT -> directionPoint = 2;
            case UP -> directionPoint = 3;
        }

        System.out.println(1000*(position.y+1) + 4*(position.x+1) + directionPoint);
    }

    private static List<List<Element>> createMap(List<String> lines) {
        List<List<Element>> map = new ArrayList<>();
        List<String> mapLines = lines.subList(0, lines.size() - 2);

        for (String mapLine : mapLines) {
            List<Element> row = new ArrayList<>();

            for (int x = 0; x < mapLine.length(); x++) {
                switch (mapLine.charAt(x)) {
                    case '#' -> row.add(x, Element.WALL);
                    case '.' -> row.add(x, Element.EMPTY);
                    default -> row.add(x, Element.NONE);
                }
            }

            map.add(row);
        }

        return map;
    }

    private static MyPosition moveThrowMap(List<List<Element>> map, String moves) {
        MyPosition currentPosition = new MyPosition(getStartX(map), 0, Direction.RIGHT);

        while (true) {
            if (moves.isEmpty()) {
                break;
            }

            //steps
            StringBuilder stepCount = new StringBuilder();
            for (int i = 0; i < moves.length(); i++) {
                if (!Character.isDigit(moves.charAt(i))) {
                    moves = moves.substring(i);
                    break;
                }
                stepCount.append(moves.charAt(i));
            }

            currentPosition = doSteps(map, currentPosition, Integer.parseInt(String.valueOf(stepCount)));

            if (onlyDigits(moves)) {
                break;
            }

            //direction
            char turn = moves.charAt(0);
            moves = moves.substring(1);
            switch (currentPosition.direction) {
                case RIGHT -> currentPosition.direction = turn == 'L' ? Direction.UP : Direction.DOWN;
                case LEFT -> currentPosition.direction = turn == 'L' ? Direction.DOWN : Direction.UP;
                case DOWN -> currentPosition.direction = turn == 'L' ? Direction.RIGHT : Direction.LEFT;
                case UP -> currentPosition.direction = turn == 'L' ? Direction.LEFT : Direction.RIGHT;
            }
        }

        return currentPosition;
    }

    private static int getStartX(List<List<Element>> map) {
        for (int i = 0; i < map.get(0).size(); i++) {
            if (map.get(0).get(i) == Element.EMPTY) {
                return i;
            }
        }

        return -1;
    }

    private static MyPosition doSteps(List<List<Element>> map, MyPosition myPosition, int steps) {
        MyPosition newPosition = new MyPosition(myPosition);

        for (int i = 1; i <= steps; i++) {
            //new position
            switch (myPosition.direction) {
                case RIGHT -> {
                    newPosition.x += 1;
                    Element el = getElementOrNone(map, newPosition.x, newPosition.y);

                    if (el == Element.NONE) {
                        for (int x = 0; x < map.get(newPosition.y).size(); x++) {
                            if (getElementOrNone(map, x, newPosition.y) != Element.NONE) {
                                newPosition.x = x;
                                break;
                            }
                        }
                    }
                }

                case LEFT -> {
                    newPosition.x -= 1;
                    Element el = getElementOrNone(map, newPosition.x, newPosition.y);

                    if (el == Element.NONE) {
                        for (int x = map.get(newPosition.y).size() - 1; x >= 0; x--) {
                            if (getElementOrNone(map, x, newPosition.y) != Element.NONE) {
                                newPosition.x = x;
                                break;
                            }
                        }
                    }
                }

                case DOWN -> {
                    newPosition.y += 1;
                    Element el = getElementOrNone(map, newPosition.x, newPosition.y);

                    if (el == Element.NONE) {
                        for (int y = 0; y < map.size(); y++) {
                            if (getElementOrNone(map, newPosition.x, y) != Element.NONE) {
                                newPosition.y = y;
                                break;
                            }
                        }
                    }
                }

                case UP -> {
                    newPosition.y -= 1;
                    Element el = getElementOrNone(map, newPosition.x, newPosition.y);

                    if (el == Element.NONE) {
                        for (int y = map.size()-1; y >= 0; y--) {
                            if (getElementOrNone(map, newPosition.x, y) != Element.NONE) {
                                newPosition.y = y;
                                break;
                            }
                        }
                    }
                }
            }

            //evaluate new position
            Element el = map.get(newPosition.y).get(newPosition.x);
            if (el == Element.WALL) {
                return myPosition;
            } else if (el == Element.EMPTY) {
                myPosition = new MyPosition(newPosition);
                //printMap(map, myPosition);
            }
        }

        return myPosition;
    }

    private static void printMap(List<List<Element>> map, MyPosition position) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {

                if (position.x == x && position.y == y) {
                    System.out.print("X");
                    continue;
                }

                Element element = map.get(y).get(x);
                switch (element) {
                    case NONE -> System.out.print(" ");
                    case EMPTY -> System.out.print(".");
                    case WALL -> System.out.print("#");
                }
            }

            System.out.println();
        }

        System.out.println();
    }

    private static boolean onlyDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static Element getElementOrNone(List<List<Element>> map, int x, int y) {
        if (y < 0 || y >= map.size()) {
            return Element.NONE;
        }
        if (x < 0 || x >= map.get(y).size()) {
            return Element.NONE;
        }

        return map.get(y).get(x);
    }
}