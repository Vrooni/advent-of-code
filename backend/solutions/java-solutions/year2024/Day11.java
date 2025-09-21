package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day11 {
    private record Stone(long value, int blinks) {}

    public static void main(String[] args) {
        //Part one
        List<Long> stones = Arrays.stream(Utils.readString("11.txt").split(" "))
                .map(Long::parseLong)
                .toList();

        for (int i = 0; i < 25; i++) {
            List<Long> newStones = new ArrayList<>();
            for (long stone : stones) {
                String stoneStr = String.valueOf(stone);

                if (stone == 0) {
                    newStones.add(1L);
                } else if (stoneStr.length() % 2 == 0) {
                    long firstStone = Long.parseLong(stoneStr.substring(0, stoneStr.length()/2));
                    long secondStone = Long.parseLong(stoneStr.substring(stoneStr.length()/2));
                    newStones.add(firstStone);
                    newStones.add(secondStone);
                } else {
                    newStones.add(stone * 2024);
                }
            }

            stones = newStones;
        }

        System.out.println(stones.size());


        //Part two
        stones = Arrays.stream(Utils.readString("11.txt").split(" "))
                .map(Long::parseLong)
                .toList();

        Map<Long, Long> stonesCount = new HashMap<>();
        for (Long stone : stones) {
            stonesCount.merge(stone, 1L, Long::sum);
        }

        for (int i = 0; i < 75; i++) {
            Map<Long, Long> newStonesCount = new HashMap<>();

            for (Map.Entry<Long, Long> entry : stonesCount.entrySet()) {
                long stone = entry.getKey();
                long count = entry.getValue();
                String stoneStr = String.valueOf(stone);

                if (stone == 0) {
                    newStonesCount.merge(1L, count, Long::sum);
                } else if (stoneStr.length() % 2 == 0) {
                    long firstStone = Long.parseLong(stoneStr.substring(0, stoneStr.length()/2));
                    long secondStone = Long.parseLong(stoneStr.substring(stoneStr.length()/2));
                    newStonesCount.merge(firstStone, count, Long::sum);
                    newStonesCount.merge(secondStone, count, Long::sum);
                } else {
                    newStonesCount.merge(stone * 2024, count, Long::sum);
                }
            }

            stonesCount = newStonesCount;
        }

        System.out.println(stonesCount.values().stream().mapToLong(v -> v).sum());
    }
}
