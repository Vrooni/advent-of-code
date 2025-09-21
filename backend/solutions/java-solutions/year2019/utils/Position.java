package year2019.utils;

import org.jetbrains.annotations.NotNull;

public record Position(int x, int y) implements Comparable<Position> {

    @Override
    public int compareTo(@NotNull Position o) {
        int compareX = Integer.compare(this.x, o.x);
        if (compareX != 0) {
            return compareX;
        }

        return Integer.compare(this.y, o.y);
    }
}
