package year2018;

import year2018.utils.Utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        //Part one
        String[] input = Utils.readString("09.txt").split(" ");

        int players = Integer.parseInt(input[0]);
        int lastMarble = Integer.parseInt(input[6]);

        System.out.println(getMaxScore(players, lastMarble));

        //Part two
        System.out.println(getMaxScore(players, lastMarble * 100));
    }

    private static long getMaxScore(int players, int lastMarble) {
        List<Long> scores = new ArrayList<>(Collections.nCopies(players, 0L));
        ArrayDeque<Long> circle = new ArrayDeque<>();
        circle.addLast(0L);

        for (long marble = 1; marble <= lastMarble; marble++) {
            if (marble % 23 == 0) {
                rotate(circle, -7);
                int player = (int) marble % players;
                long score = scores.get(player) + marble + circle.removeFirst();
                scores.set(player, score);
            } else {
                rotate(circle, 2);
                circle.addFirst(marble);
            }
        }

        return scores.stream().mapToLong(i -> i).max().getAsLong();
    }

    private static void rotate(ArrayDeque<Long> circle, int steps) {
        if (steps < 0) {
            for (int i = steps; i < 0; i++) {
                long e = circle.removeLast();
                circle.addFirst(e);
            }
        } else {
            for (int i = 0; i < steps; i++) {
                long e = circle.removeFirst();
                circle.addLast(e);
            }
        }
    }
}
