package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day21 {
    private record Item(int cost, int damage, int armor) {}
    private record Player(int hit, int damage, int armor) {}

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/21.txt"));

        List<Item> weapons = List.of(
                new Item(8, 4, 0),
                new Item(10, 5, 0),
                new Item(25, 6, 0),
                new Item(40, 7, 0),
                new Item(74, 8, 0)
        );
        List<Item> armors = List.of(
                new Item(13, 0, 1),
                new Item(31, 0, 2),
                new Item(53, 0, 3),
                new Item(75, 0, 4),
                new Item(102, 0, 5)
        );
        List<Item> rings = List.of(
                new Item(25, 1, 0),
                new Item(50, 2, 0),
                new Item(100, 3, 0),
                new Item(20, 0, 1),
                new Item(40, 0, 2),
                new Item(80, 0, 3)
        );

        Player boss = new Player(
                Integer.parseInt(input.get(0).replace("Hit Points: ", "")),
                Integer.parseInt(input.get(1).replace("Damage: ", "")),
                Integer.parseInt(input.get(2).replace("Armor: ", ""))
        );


        System.out.println(getGold(weapons, armors, rings, boss, false));

        //Part two
        System.out.println(getGold(weapons, armors, rings, boss, true));
    }

    private static int getGold(List<Item> weapons, List<Item> armors, List<Item> rings, Player boss, boolean max) {
        int spentGold = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Item weapon : weapons) {

            for (int i = -1; i < armors.size(); i++) {
                Item armor = i == -1 ? new Item(0, 0, 0) : armors.get(i);

                for (int j1 = -1; j1 < rings.size(); j1++) {
                    Item ring1 = j1 == -1 ? new Item(0, 0, 0) : rings.get(j1);

                    for (int j2 = -1; j2 < rings.size(); j2++) {
                        Item ring2 = j2 == -1 || j1 == j2 ? new Item(0, 0, 0) : rings.get(j2);


                        Player player = new Player(
                                100,
                                weapon.damage + ring1.damage + ring2.damage,
                                armor.armor + ring1.armor + ring2.armor
                        );

                        if (max) {
                            if (lose(player, boss)) {
                                spentGold = Math.max(spentGold, weapon.cost + armor.cost + ring1.cost + ring2.cost);
                            }
                        } else {
                            if (wins(player, boss)) {
                                spentGold = Math.min(spentGold, weapon.cost + armor.cost + ring1.cost + ring2.cost);
                            }
                        }
                    }
                }
            }
        }

        return spentGold;
    }

    private static boolean wins(Player player, Player boss) {
        int playerDamage = player.damage - boss.armor;
        int bossDamage = boss.damage - player.armor;

        if (playerDamage == 0) {
            return false;
        } else if (bossDamage == 0) {
            return true;
        }

        double playerDealTimes = Math.ceil((float) boss.hit / playerDamage);
        double bossDealTimes = Math.ceil((float) player.hit / bossDamage);

        return playerDealTimes <= bossDealTimes;
    }

    private static boolean lose(Player player, Player boss) {
        int playerDamage = Math.max(0, player.damage - boss.armor);
        int bossDamage = Math.max(0, boss.damage - player.armor);

        if (playerDamage == 0) {
            return true;
        } else if (bossDamage == 0) {
            return false;
        }

        double playerDealTimes = Math.ceil((float) boss.hit / playerDamage);
        double bossDealTimes = Math.ceil((float) player.hit / bossDamage);

        return playerDealTimes > bossDealTimes;
    }
}
