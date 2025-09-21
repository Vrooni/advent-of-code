package year2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {
    private record Position(int x, int y) {
    }

    private enum State {
        WEAKENED, INFECTED, FLAGGED, CLEAN;

        public State next() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }
    }

    private enum Direction {
        UP, RIGHT, DOWN, LEFT;

        public Direction next() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }

        public Direction previous() {
            int previous = this.ordinal() == 0 ? values().length - 1 : this.ordinal() - 1;
            return values()[previous];
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("22.txt");
        Map<Position, Boolean> map = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                map.put(new Position(j, i), input.get(i).charAt(j) == '#');
            }
        }

        Direction direction = Direction.UP;
        Position position = new Position(
                input.get(0).length() / 2,
                input.size() / 2
        );
        int causedInfections = 0;

        for (int i = 0; i < 10000; i++) {
            boolean infected = map.containsKey(position) && map.get(position);
            direction = infected ? direction.next() : direction.previous();
            map.put(position, !infected);
            causedInfections += infected ? 0 : 1;

            position = switch (direction) {
                case UP -> new Position(position.x, position.y - 1);
                case DOWN -> new Position(position.x, position.y + 1);
                case LEFT -> new Position(position.x - 1, position.y);
                case RIGHT -> new Position(position.x + 1, position.y);
            };
        }

        System.out.println(causedInfections);


        //Part two
        Map<Position, State> stateMap = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                stateMap.put(
                        new Position(j, i),
                        input.get(i).charAt(j) == '#'
                                ? State.INFECTED
                                : State.CLEAN
                );
            }
        }

        direction = Direction.UP;
        position = new Position(
                input.get(0).length() / 2,
                input.size() / 2
        );
        causedInfections = 0;

        for (int i = 0; i < 10000000; i++) {
            State state = stateMap.getOrDefault(position, State.CLEAN);

            direction = switch (state) {
                case INFECTED -> direction.next();
                case WEAKENED -> direction;
                case FLAGGED -> direction.next().next();
                case CLEAN -> direction.previous();
            };

            stateMap.put(position, state.next());
            causedInfections += state == State.WEAKENED ? 1 : 0;

            position = switch (direction) {
                case UP -> new Position(position.x, position.y - 1);
                case DOWN -> new Position(position.x, position.y + 1);
                case LEFT -> new Position(position.x - 1, position.y);
                case RIGHT -> new Position(position.x + 1, position.y);
            };
        }

        System.out.println(causedInfections);
    }
}
