package year2018;

import org.jetbrains.annotations.NotNull;
import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;

public class Day15 {
    private enum Type {
        ELF, GOBLIN
    }

    private static class Entity implements Comparable<Entity> {
        Position position;
        Type type;
        int hp = 200;
        int attackPower = 3;
        boolean alive = true;

        public Entity(Position position, Type type) {
            this.position = position;
            this.type = type;
        }

        public Position getMove(char[][] map) {
            List<Position> neighbours = getNeighbours(this.position, map);
            if (neighbours.stream().anyMatch(neighbour -> map[neighbour.y()][neighbour.x()] == this.getEnemyType())) {
                return this.position;
            }

            neighbours = neighbours.stream().filter(neighbour -> map[neighbour.y()][neighbour.x()] == '.').toList();
            if (neighbours.isEmpty()) {
                return this.position;
            }

            List<Move> moves = new ArrayList<>(neighbours.stream().map(neighbour -> new Move(neighbour, getDistance(neighbour, map))).toList());
            moves.sort((o1, o2) -> {
                int compareDist = Integer.compare(o1.dist, o2.dist);
                if (compareDist != 0) return compareDist;

                int compareY = Integer.compare(o1.position.y(), o2.position.y());
                return compareY == 0 ? Integer.compare(o1.position.x(), o2.position.x()) : compareY;
            });

            return moves.get(0).dist == Integer.MAX_VALUE ? this.position : moves.get(0).position;
        }

        public int getDistance(Position start, char[][] map) {
            Queue<Move> queue = new PriorityQueue<>();
            queue.add(new Move(new Position(start.x(), start.y()), 0));

            Map<Position, Integer> distances = new HashMap<>();
            distances.put(new Position(start.x(), start.y()), 0);

            while (!queue.isEmpty()) {
                Move current = queue.poll();
                List<Position> neighbours = getNeighbours(current.position, map);

                for (Position neighbour : neighbours) {
                    if (map[neighbour.y()][neighbour.x()] == this.getEnemyType()) {
                        return current.dist + 1;
                    } else if (map[neighbour.y()][neighbour.x()] == '.' && current.dist + 1 < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                        queue.add(new Move(neighbour, current.dist + 1));
                        distances.put(neighbour, current.dist + 1);
                    }
                }
            }

            return Integer.MAX_VALUE;
        }

        public Entity getEnemy(List<Entity> entities, char[][] map) {
            int x = this.position.x();
            int y = this.position.y();
            char enemy = this.getEnemyType();

            List<Entity> enemies = new ArrayList<>();
            if (x > 0 && map[y][x-1] == enemy) {
                enemies.add(getEntity(entities, x-1, y));
            }
            if (y > 0 && map[y-1][x] == enemy) {
                enemies.add(getEntity(entities, x, y-1));
            }
            if (x < map[0].length-1 && map[y][x+1] == enemy) {
                enemies.add(getEntity(entities, x+1, y));
            }
            if (y < map.length-1 && map[y+1][x] == enemy) {
                enemies.add(getEntity(entities, x, y+1));
            }

            if (enemies.isEmpty()) {
                return null;
            }

            enemies.sort((o1, o2) -> {
                int compareHP = Integer.compare(o1.hp, o2.hp);
                if (compareHP != 0) return compareHP;

                int compareY = Integer.compare(o1.position.y(), o2.position.y());
                return compareY == 0 ? Integer.compare(o1.position.x(), o2.position.x()) : compareY;
            });

            return enemies.get(0);
        }

        public char getEnemyType() {
            return this.type == Type.GOBLIN ? 'E' : 'G';
        }

        public boolean dealDamage(char[][] map, int attackPower) {
            this.hp -= attackPower;

            if (this.hp <= 0) {
                this.alive = false;
                map[this.position.y()][this.position.x()] = '.';
                return false;
            }

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entity entity)) return false;
            return Objects.equals(position, entity.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position);
        }

        @Override
        public int compareTo(@NotNull Entity o) {
            int compareY = Integer.compare(this.position.y(), o.position.y());
            return compareY == 0 ? Integer.compare(this.position.x(), o.position.x()) : compareY;
        }
    }

    private record Move(Position position, int dist) implements Comparable<Move> {
        @Override
        public int compareTo(@NotNull Move o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("15.txt");

        char[][] map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        List<Entity> entities = getEntities(input);

        System.out.println(fight(entities, map, false) * entities.stream().mapToInt(entity -> entity.hp).sum());


        //Part two
        for (int attackPower = 4; true; attackPower++) {
            map = input.stream().map(String::toCharArray).toArray(char[][]::new);
            entities = getEntities(input);

            for (Entity entity : entities) {
                if (entity.type == Type.ELF) {
                    entity.attackPower = attackPower;
                }
            }

            int totalElves = (int) entities.stream().filter(entity -> entity.type == Type.ELF).count();
            int rounds = fight(entities, map, true);
            int aliveElves = (int) entities.stream().filter(entity -> entity.type == Type.ELF).count();

            if (totalElves == aliveElves) {
                System.out.println(rounds * entities.stream().mapToInt(entity -> entity.hp).sum());
                break;
            }
        }
    }

    private static int fight(List<Entity> entities, char[][] map, boolean breakEarly) {
        for (int round = 0; true; round++) {
            for (Entity entity : entities) {

                if (hasTargets(entities)) {
                    entities.removeIf(e -> !e.alive);
                    return round;
                }

                if (!entity.alive) {
                    continue;
                }

                //move
                Position target = entity.getMove(map);
                map[entity.position.y()][entity.position.x()] = '.';

                entity.position = new Position(target.x(), target.y());
                map[target.y()][target.x()] = entity.type == Type.GOBLIN ? 'G' : 'E';

                //attack
                Entity enemy = entity.getEnemy(entities, map);
                if (enemy != null) {
                    boolean survive = enemy.dealDamage(map, entity.attackPower);

                    if (!survive && enemy.type == Type.ELF && breakEarly) {
                        entities.removeIf(e -> !e.alive);
                        return round;
                    }
                }
            }

            entities.removeIf(entity -> !entity.alive);
            Collections.sort(entities);
        }
    }

    private static List<Entity> getEntities(List<String> input) {
        List<Entity> entities = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                char c = input.get(i).charAt(j);

                switch (c) {
                    case 'G' -> entities.add(new Entity(new Position(j, i), Type.GOBLIN));
                    case 'E' -> entities.add(new Entity(new Position(j, i), Type.ELF));
                }
            }
        }

        Collections.sort(entities);
        return entities;
    }

    private static boolean hasTargets(List<Entity> entities) {
        entities = entities.stream().filter(entity -> entity.alive).toList();
        long goblins = entities.stream().filter(entity -> entity.type == Type.GOBLIN).count();
        return entities.size() == goblins || goblins == 0;
    }

    private static Entity getEntity(List<Entity> entities, int x, int y) {
        return entities.stream()
                .filter(entity -> entity.position.x() == x && entity.position.y() == y)
                .findFirst().get();
    }

    private static List<Position> getNeighbours(Position pos, char[][] map) {
        int x = pos.x();
        int y = pos.y();

        List<Position> neighbours = new ArrayList<>();
        if (x > 0) {
            neighbours.add(new Position(x - 1, y));
        }
        if (y > 0) {
            neighbours.add(new Position(x, y - 1));
        }
        if (x < map[0].length - 1) {
            neighbours.add(new Position(x + 1, y));
        }
        if (y < map.length - 1) {
            neighbours.add(new Position(x, y + 1));
        }

        return neighbours;
    }
}
