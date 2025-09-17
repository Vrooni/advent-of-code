package year2018.utils;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public Direction next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Direction previous() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
    }
}
