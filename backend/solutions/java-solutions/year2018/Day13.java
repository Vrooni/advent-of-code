package year2018;

import org.jetbrains.annotations.NotNull;
import year2018.utils.Direction;
import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;

public class Day13 {
    private enum Decision {
        LEFT, STRAIGHT, RIGHT;

        public Decision next() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    private static class Cart implements Comparable<Cart> {
        Position position;
        Direction direction;
        Decision decision = Decision.LEFT;
        boolean crashed = false;

        public Cart(Position position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public Cart(Position position, Direction direction, Decision decision) {
            this.position = position;
            this.direction = direction;
            this.decision = decision;
        }

        @Override
        public int compareTo(@NotNull Cart o) {
            int compareY = Integer.compare(this.position.y(), o.position.y());
            return compareY == 0 ? Integer.compare(this.position.x(), o.position.x()) : compareY;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("13.txt");

        List<List<Character>> rails = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            List<Character> railLine = new ArrayList<>();

            for (int j = 0; j < input.get(0).length(); j++) {
                char rail = input.get(i).charAt(j);

                switch (rail) {
                    case '^' -> {
                        carts.add(new Cart(new Position(j, i), Direction.UP));
                        railLine.add('|');
                    }
                    case 'v' -> {
                        carts.add(new Cart(new Position(j, i), Direction.DOWN));
                        railLine.add('|');
                    }
                    case '<' -> {
                        carts.add(new Cart(new Position(j, i), Direction.LEFT));
                        railLine.add('-');
                    }
                    case '>' -> {
                        carts.add(new Cart(new Position(j, i), Direction.RIGHT));
                        railLine.add('-');
                    }
                    default -> railLine.add(rail);
                }
            }

            rails.add(railLine);
        }

        List<Cart> initial = copyOf(carts);
        Position collision = null;

        while (collision == null) {
            for (Cart cart : carts) {
                move(cart, rails);
                collision = checkCollision(carts);

                if (collision != null) {
                    break;
                }
            }

            Collections.sort(carts);
        }

        System.out.println(collision.x() + "," + collision.y());


        //Part two
        carts = initial;

        while (true) {
            for (Cart cart : carts) {
                move(cart, rails);
                checkCollision(carts);
            }

            carts = new ArrayList<>(carts.stream().filter(cart -> !cart.crashed).toList());

            if (carts.size() == 1) {
                Position lastCart = carts.get(0).position;
                System.out.println(lastCart.x() + "," + lastCart.y());
                break;
            }

            Collections.sort(carts);
        }
    }

    private static void move(Cart cart, List<List<Character>> rails) {
        cart.position = switch (cart.direction) {
            case UP -> new Position(cart.position.x(), cart.position.y()-1);
            case DOWN -> new Position(cart.position.x(), cart.position.y()+1);
            case LEFT -> new Position(cart.position.x()-1, cart.position.y());
            case RIGHT -> new Position(cart.position.x()+1, cart.position.y());
        };

        char rail = rails.get(cart.position.y()).get(cart.position.x());
        switch (rail) {
            case '/' -> {
                switch (cart.direction) {
                    case UP, DOWN -> cart.direction = cart.direction.next();
                    case LEFT, RIGHT -> cart.direction = cart.direction.previous();
                }
            }
            case '\\' -> {
                switch (cart.direction) {
                    case UP, DOWN -> cart.direction = cart.direction.previous();
                    case LEFT, RIGHT -> cart.direction = cart.direction.next();
                }
            }
            case '+' -> {
                cart.direction = switch (cart.decision) {
                    case LEFT -> cart.direction.previous();
                    case STRAIGHT -> cart.direction;
                    case RIGHT -> cart.direction.next();
                };
                cart.decision = cart.decision.next();
            }
        }
    }

    private static Position checkCollision(List<Cart> carts) {
        for (int i = 0; i < carts.size()-1; i++) {
            for (int j = i+1; j < carts.size(); j++) {
                Position pos1 = carts.get(i).position;
                Position pos2 = carts.get(j).position;

                if (pos1.x() == pos2.x() && pos1.y() == pos2.y()) {
                    carts.get(i).crashed = true;
                    carts.get(j).crashed = true;
                    return pos1;
                }
            }
        }

        return null;
    }

    private static List<Cart> copyOf(List<Cart> carts) {
        List<Cart> copy = new ArrayList<>();

        for (Cart cart : carts) {
            copy.add(new Cart(
                    new Position(cart.position.x(), cart.position.y()),
                    cart.direction,
                    cart.decision)
            );
        }

        return copy;
    }
}
