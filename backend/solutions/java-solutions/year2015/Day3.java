package year2015;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Position other)) {
                return false;
            }

            return this.x == other.x && this.y == other.y;
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        String moves = Files.readString(Path.of("src/year2015/files/03.txt"), StandardCharsets.UTF_8);

        Position actualPosition = new Position(0, 0);
        List<Position> positions = new ArrayList<>();
        positions.add(actualPosition);

        for (char move : moves.toCharArray()) {
            actualPosition = addPositionAfterMove(move, actualPosition, positions);
        }

        System.out.println(positions.size());

        //Part two
        actualPosition = new Position(0, 0);
        positions = new ArrayList<>();
        positions.add(actualPosition);

        for (int i = 0; i < moves.toCharArray().length; i+=2) {
            actualPosition = addPositionAfterMove(moves.charAt(i), actualPosition, positions);
        }

        actualPosition = new Position(0, 0);
        for (int i = 1; i < moves.toCharArray().length; i+=2) {
            actualPosition = addPositionAfterMove(moves.charAt(i), actualPosition, positions);
        }

        System.out.println(positions.size());
    }

    private static Position addPositionAfterMove(char move, Position position, List<Position> positions) {
        int x = position.x;
        int y = position.y;

        switch (move) {
            case '^' -> x++;
            case 'v' -> x--;
            case '>' -> y++;
            case '<' -> y--;
            default -> throw new RuntimeException("No move detected");
        }

        position = new Position(x, y);
        if (!positions.contains(position)) {
            positions.add(position);
        }

        return position;
    }
}
