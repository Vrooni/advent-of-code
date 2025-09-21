package year2024.utils;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    private static final Direction[] values = values();

    public Direction next() {
        return values[(this.ordinal() + 1) % values.length];
    }

    public Direction prev() {
        return values[(this.ordinal() - 1  + values.length) % values.length];
    }
}
