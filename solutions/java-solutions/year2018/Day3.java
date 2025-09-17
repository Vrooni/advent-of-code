package year2018;

import year2018.utils.Position;
import year2018.utils.Utils;

import java.util.*;

public class Day3 {
    private record Claim(String id, List<Position> squares) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("03.txt");
        List<Claim> claims = input.stream().map(Day3::getClaim).toList();
        Map<Position, Integer> usageCount = new HashMap<>();

        for (Claim claim : claims) {
            for (Position square : claim.squares) {
                usageCount.put(square, usageCount.getOrDefault(square, 0) + 1);
            }
        }

        System.out.println(usageCount.values().stream().filter(usage -> usage > 1).count());


        //Part two
        for (Claim claim : claims) {
            if (claim.squares.stream().allMatch(square -> usageCount.get(square) == 1)) {
                System.out.println(claim.id);
            }
        }
    }

    private static Claim getClaim(String claim) {
        String[] splitClaim = claim.split(" ");

        String id = splitClaim[0].replace("#", "");
        String[] distance = splitClaim[2].replace(":", "").split(",");
        String[] size = splitClaim[3].split("x");

        Position from = new Position(
                Integer.parseInt(distance[0]),
                Integer.parseInt(distance[1])
        );
        Position to = new Position(
                from.x() + Integer.parseInt(size[0]),
                from.y() + Integer.parseInt(size[1])
        );

        List<Position> squares = new ArrayList<>();
        for (int i = from.y(); i < to.y(); i++) {
            for (int j = from.x(); j < to.x(); j++) {
                squares.add(new Position(j, i));
            }
        }

        return new Claim(id, squares);
    }
}
