package year2018;

import org.jetbrains.annotations.NotNull;
import year2018.utils.Utils;

import java.util.*;

public class Day24 {
    private enum Army {
        IMMUNE_SYSTEM, INFECTION
    }

    private static class Group implements Comparable<Group> {
        Army army;
        int units;
        int hitPoints;
        int damage;
        int effectivePower;
        int initiative;
        Group target;
        List<String> immunities = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();
        String attackType;

        public Group(Army army, int units, int hitPoints, int damage, int initiative, String attackType, String immunitiesAndWeaknesses) {
            this.army = army;
            this.units = units;
            this.hitPoints = hitPoints;
            this.damage = damage;
            this.initiative = initiative;
            this.attackType = attackType;
            this.effectivePower = damage * units;

            if (!immunitiesAndWeaknesses.isEmpty()) {
                addImmunitiesAndWeaknesses(immunitiesAndWeaknesses);
            }
        }

        public void calculateTarget(List<Group> freeForSelection) {
            List<Group> possibles = new ArrayList<>(freeForSelection.stream().filter(possible -> possible.army != this.army && !possible.immunities.contains(this.attackType)).toList());

            possibles.sort((o1, o2) -> {
                int weaknessCompare = Boolean.compare(o2.weaknesses.contains(this.attackType), o1.weaknesses.contains(this.attackType));
                if (weaknessCompare != 0) {
                    return weaknessCompare;
                }

                int effectivePowerCompare = Integer.compare(o2.effectivePower, o1.effectivePower);
                if (effectivePowerCompare != 0) {
                    return effectivePowerCompare;
                }

                return Integer.compare(o2.initiative, o1.initiative);
            });

            if (possibles.isEmpty()) {
                this.target = null;
            } else {
                this.target = possibles.get(0);
                freeForSelection.remove(this.target);
            }
        }
        private void addImmunitiesAndWeaknesses(String immunitiesAndWeaknesses) {
            immunitiesAndWeaknesses = immunitiesAndWeaknesses.replace("(", "").replace(")", "");

            for (String part : immunitiesAndWeaknesses.split("; ")) {

                if (part.startsWith("immune to ")) {
                    String immunitiesString = part.replace("immune to ", "").trim();
                    this.immunities.addAll(List.of(immunitiesString.split(", ")));
                } else if (part.startsWith("weak to ")) {
                    String weaknessesString = part.replace("weak to ", "").trim();
                    this.weaknesses.addAll(List.of(weaknessesString.split(", ")));
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Group group)) return false;
            return units == group.units
                    && hitPoints == group.hitPoints
                    && damage == group.damage
                    && effectivePower == group.effectivePower
                    && initiative == group.initiative
                    && army == group.army
                    && Objects.equals(immunities, group.immunities)
                    && Objects.equals(weaknesses, group.weaknesses)
                    && Objects.equals(attackType, group.attackType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(army, units, hitPoints, damage, effectivePower, initiative, immunities, weaknesses, attackType);
        }

        @Override
        public int compareTo(@NotNull Group o) {
            int comparePower = Integer.compare(o.effectivePower, this.effectivePower);

            if (comparePower != 0) {
                return comparePower;
            }

            return Integer.compare(o.initiative, this.initiative);
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("24.txt");
        List<Group> groups = readInput(input, 0);
        Collections.sort(groups);

        fight(groups);
        System.out.println(groups.stream().mapToInt(group -> group.units).sum());


        //Part two
        for (int i = 1; true; i++) {
            groups = readInput(input, i);
            Collections.sort(groups);

            if (fight(groups) && groups.get(0).army == Army.IMMUNE_SYSTEM) {
                break;
            }
        }

        System.out.println(groups.stream().mapToInt(group -> group.units).sum());
    }

    private static List<Group> readInput(List<String> input, int boost) {
        List<Group> groups = new ArrayList<>();

        addGroups(groups, Army.IMMUNE_SYSTEM, boost, input.subList(1, input.indexOf("")));
        addGroups(groups, Army.INFECTION, 0, input.subList(input.indexOf("Infection:") + 1, input.size()));

        return groups;
    }

    private static void addGroups(List<Group> groups, Army army, int boost, List<String> input) {
        for (String group : input) {
            String[] groupInformation = group
                    .replace(" units each with ", "|")
                    .replace(" hit points", "|")
                    .replace("with an attack that does ", "|")
                    .replace(" damage at initiative ", "|")
                    .split("\\|");

            groups.add(new Group(army,
                    Integer.parseInt(groupInformation[0]),
                    Integer.parseInt(groupInformation[1]),
                    Integer.parseInt(groupInformation[3].split(" ")[0]) + boost,
                    Integer.parseInt(groupInformation[4]),
                    groupInformation[3].split(" ")[1],
                    groupInformation[2].trim()
            ));
        }
    }

    private static boolean fight(List<Group> groups) {
        while (groups.stream().anyMatch(group -> group.army == Army.IMMUNE_SYSTEM) && groups.stream().anyMatch(group -> group.army == Army.INFECTION)) {
            //target selection
            List<Group> freeForSelection = new ArrayList<>(groups);
            for (Group group : groups) {
                group.calculateTarget(freeForSelection);
            }

            //attacking
            int killedUnits = 0;
            groups.sort((o1, o2) -> Integer.compare(o2.initiative, o1.initiative));
            for (Group group : groups) {
                if (group.units <= 0 || group.target == null) {
                    continue;
                }

                Group target = group.target;
                int totalDamage = target.weaknesses.contains(group.attackType) ? group.effectivePower * 2 : group.effectivePower;
                target.units -=  totalDamage / target.hitPoints;
                target.effectivePower = target.units * target.damage;
                killedUnits += totalDamage / target.hitPoints;
            }

            if (killedUnits == 0) {
                return false;
            }
            groups.removeIf(group -> group.units <= 0);
            Collections.sort(groups);
        }

        return true;
    }
}
