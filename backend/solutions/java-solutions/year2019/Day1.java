package year2019;

import year2019.utils.Utils;

import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        //Part one
        List<Integer> masses = Utils.readLines("01.txt").stream().map(Integer::parseInt).toList();
        System.out.println(masses.stream().mapToInt(mass -> mass/3 - 2).sum());


        //Part two
        int fuel = 0;

        for (Integer mass : masses) {
            int fuelForMass = 0;

            for (int i = mass/3 - 2; i > 0; i = i/3 - 2) {
                fuelForMass += i;
            }

            fuel += fuelForMass;
        }

        System.out.println(fuel);
    }
}
