package year2015;

import java.util.HashMap;
import java.util.Map;

public class Day20 {

    public static void main(String[] args) {
        //Part one
        int input = 33100000;
        for (int houseNr = 1; ; houseNr++) {
            int presents = 0;

            for (int i = 1; i <= Math.sqrt(houseNr); i++) {
                if (houseNr % i == 0) {
                    presents += i * 10;
                    if (houseNr/i != i) {
                        presents += (houseNr/i) * 10;
                    }
                }
            }

            if (presents >= input) {
                System.out.println(houseNr);
                break;
            }
        }

        //Part two
        Map<Integer, Integer> deliverInformation = new HashMap<>(); //Information on how many houses the elf has already supplied.
        for (int houseNr = 1; ; houseNr++) {
            int presents = 0;

            for (int i = 1; i <= Math.sqrt(houseNr); i++) {

                if (houseNr % i == 0) {
                    int delivers = deliverInformation.computeIfAbsent(i, k -> 0)+1;

                    if (delivers <= 50) {
                        presents += i * 11;
                        deliverInformation.put(i, delivers);
                    }

                    delivers = deliverInformation.computeIfAbsent(houseNr/i, k -> 0)+1;

                    if (houseNr/i != i && delivers <= 50) {
                        presents += (houseNr/i) * 11;
                        deliverInformation.put(houseNr/i, delivers);
                    }
                }
            }

            if (presents >= input) {
                System.out.println(houseNr);
                break;
            }
        }
    }
}
