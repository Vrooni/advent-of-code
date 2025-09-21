package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day23_2 {
    record Position(int x, int y) {
        public boolean hasNorthNeighbours(Set<Position> elves) {
            return elves.contains(new Position(this.x-1, this.y-1))
                    || elves.contains(new Position(this.x, this.y-1))
                    || elves.contains(new Position(this.x+1, this.y-1));
        }

        public boolean hasSouthNeighbours(Set<Position> elves) {
            return elves.contains(new Position(this.x-1, this.y+1))
                    || elves.contains(new Position(this.x, this.y+1))
                    || elves.contains(new Position(this.x+1, this.y+1));
        }

        public boolean hasWestNeighbours(Set<Position> elves) {
            return elves.contains(new Position(this.x-1, this.y-1))
                    || elves.contains(new Position(this.x-1, this.y))
                    || elves.contains(new Position(this.x-1, this.y+1));
        }

        public boolean hasEastNeighbours(Set<Position> elves) {
            return elves.contains(new Position(this.x+1, this.y-1))
                    || elves.contains(new Position(this.x+1, this.y))
                    || elves.contains(new Position(this.x+1, this.y+1));
        }

        public boolean hasNeighbours(Set<Position> elves) {
            return hasNorthNeighbours(elves)
                    || hasSouthNeighbours(elves)
                    || hasWestNeighbours(elves)
                    || hasEastNeighbours(elves);
        }

        public Position getNewPosition(Set<Position> elves, List<Direction> directions) {
            Position newPosition = null;

            if (this.hasNeighbours(elves)) {

                search:
                for (Direction direction : directions) {
                    switch (direction) {
                        case NORTH:
                            if (!this.hasNorthNeighbours(elves)) {
                                newPosition = new Position(this.x, this.y - 1);
                                break search;
                            }
                            break;
                        case SOUTH:
                            if (!this.hasSouthNeighbours(elves)) {
                                newPosition = new Position(this.x, this.y + 1);
                                break search;
                            }
                            break;
                        case WEST:
                            if (!this.hasWestNeighbours(elves)) {
                                newPosition = new Position(this.x - 1, this.y);
                                break search;
                            }
                            break;
                        case EAST:
                            if (!this.hasEastNeighbours(elves)) {
                                newPosition = new Position(this.x + 1, this.y);
                                break search;
                            }
                            break;
                    }
                }
            }

            return newPosition;

        }
    }
    enum Direction { NORTH, SOUTH, WEST, EAST }

    public static void main(String[] args) throws IOException {
        //Part two
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/23.txt"));
        Set<Position> elves = new HashSet<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);

            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    elves.add(new Position(x, y));
                }
            }
        }

        List<Direction> directions = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Map<Position, List<Position>> moves = new HashMap<>();

            //get new Positions
            for (Position elv : elves) {
                Position newPosition = elv.getNewPosition(elves, directions);

                if (newPosition != null) {
                    moves.computeIfAbsent(newPosition, k -> new ArrayList<>()).add(elv);
                }
            }

            //move elves
            boolean hasChanged = false;
            for (Position move : moves.keySet()) {
                if (moves.get(move).size() == 1) {
                    Position elv = moves.get(move).get(0);

                    elves.remove(elv);
                    elves.add(move);

                    hasChanged = true;
                }
            }

            if (!hasChanged) {
                System.out.println(i+1);
                System.exit(0);
            }

            //rotate directions
            switch (directions.get(0)) {
                case NORTH -> directions = List.of(Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.NORTH);
                case SOUTH -> directions = List.of(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
                case WEST -> directions = List.of(Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST);
                case EAST -> directions = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
            }
        }
    }
}
