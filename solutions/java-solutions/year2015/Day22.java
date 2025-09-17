package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day22 {
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        //TODO IMPORTANT!!! Run 3 times to insure output is right, because random
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/22.txt"));

        int bossHitPoints = Integer.parseInt(input.get(0).replace("Hit Points: ", ""));
        int damage = Integer.parseInt(input.get(1).replace("Damage: ", ""));
        int spentMana = Integer.MAX_VALUE;

        for (int i = 0; i < 100000; i++) {
            spentMana = Math.min(spentMana, doFight(bossHitPoints, damage, false));
        }

        System.out.println(spentMana);


        //Part two
        spentMana = Integer.MAX_VALUE;

        for (int i = 0; i < 100000; i++) {
            spentMana = Math.min(spentMana, doFight(bossHitPoints, damage, true));
        }

        System.out.println(spentMana);

    }

    private static int doFight(int bossHP, int damage, boolean hard) {
        int playerHP = 50;
        int mana = 500;
        boolean turn = true;

        int shieldTime = -1;
        int poisonTime = -1;
        int rechargeTime = -1;

        int spentMana = 0;

        while (true) {
            //effects
            mana += rechargeTime >= 0 ? 101 : 0;
            bossHP -= poisonTime >= 0 ? 3 : 0;

            //player turn
            if (turn) {
                List<Integer> possibleSpells = getPossibleSpells(shieldTime, poisonTime, rechargeTime, mana);
                playerHP -= hard ? 1 : 0;

                if (possibleSpells.isEmpty() || playerHP <= 0) {
                    return Integer.MAX_VALUE;
                }

                int index = random.nextInt(possibleSpells.size());

                switch (possibleSpells.get(index)) {
                    case 0 -> { bossHP -= 4; spentMana += 53; mana -= 53; }
                    case 1 -> { bossHP -= 2; playerHP += 2; spentMana += 73; mana -= 73; }
                    case 2 -> { shieldTime = 6; spentMana += 113; mana -= 113; }
                    case 3 -> { poisonTime = 6; spentMana += 173; mana -= 173; }
                    case 4 -> { rechargeTime = 5; spentMana += 229; mana -= 229; }
                }
            }

            //boss turn
            else {
                playerHP -= shieldTime >= 0 ? Math.max(0, damage - 7) : damage;
            }

            //win codition
            if (bossHP <= 0) {
                return spentMana;
            } else if (playerHP <= 0) {
                return Integer.MAX_VALUE;
            }

            //update
            shieldTime--;
            poisonTime--;
            rechargeTime--;
            turn = !turn;
        }
    }

    private static List<Integer> getPossibleSpells(int shield, int poison, int recharge, int mana) {
        List<Integer> possibleSpells = new ArrayList<>();

        if (mana >= 53) possibleSpells.add(0);
        if (mana >= 73) possibleSpells.add(1);

        if (mana >= 113 && shield <= 0) possibleSpells.add(2);
        if (mana >= 173 && poison <= 0) possibleSpells.add(3);
        if (mana >= 229 && recharge <= 0) possibleSpells.add(4);

        return possibleSpells;
    }
}
