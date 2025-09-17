package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day22 {
    private record Price(int amount, int diff) {}
    private record Sequence(int change1, int change2, int change3, int change4) {}
    private record Buyer(List<Sequence> sequences) {}

    public static void main(String[] args) {
        //Part one
        List<Integer> secretNumbers = Utils.readLines("22.txt").stream().map(Integer::parseInt).toList();
        long result = 0;

        for (long secretNumber : secretNumbers) {
            for (int i = 0; i < 2000; i++) {
                secretNumber = getNextSecretNumber(secretNumber);
            }

            result += secretNumber;
        }

        System.out.println(result);


        //Part two
        List<List<Price>> buyers = getBuyers(secretNumbers);
        Map<Sequence, List<Integer>> sequencePrices = getSequencesWithPrices(buyers);

        int bananas = sequencePrices.values().stream().mapToInt(Day22::getBananas).max().getAsInt();
        System.out.println(bananas);
    }

    private static int getBananas(List<Integer> prices) {
        return prices.stream().mapToInt(price -> price).sum();
    }

    private static Map<Sequence, List<Integer>> getSequencesWithPrices(List<List<Price>> buyers) {
        Map<Sequence, List<Integer>> sequencePrices = new HashMap<>();

        for (List<Price> buyer : buyers) {
            Set<Sequence> sequences = new HashSet<>();

            for (int i = 0; i <= buyer.size() - 4; i++) {
                Sequence sequence = new Sequence(buyer.get(i).diff,
                        buyer.get(i+1).diff,
                        buyer.get(i+2).diff,
                        buyer.get(i+3).diff
                );

                if (!sequences.contains(sequence)) {
                    sequences.add(sequence);
                    sequencePrices.computeIfAbsent(sequence, s -> new ArrayList<>()).add(buyer.get(i+3).amount);
                }
            }
        }

        return sequencePrices;
    }

    private static List<List<Price>> getBuyers(List<Integer> secretNumbers) {
        List<List<Price>> buyers = new ArrayList<>();

        for (long secretNumber : secretNumbers) {
            List<Price> buyer = new ArrayList<>();

            for (int i = 0; i < 2000; i++) {
                int price = (int) (secretNumber % 10);
                secretNumber = getNextSecretNumber(secretNumber);
                int newPrice = (int) (secretNumber % 10);

                buyer.add(new Price(newPrice, newPrice - price));
            }

            buyers.add(buyer);
        }

        return buyers;
    }

    private static long getNextSecretNumber(long secretNumber) {
        long value = secretNumber * 64;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        value = secretNumber / 32;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        value = secretNumber * 2048;
        secretNumber = mix(secretNumber, value);
        secretNumber = prune(secretNumber);

        return secretNumber;
    }

    private static long mix(long secretNumber, long number) {
        return number ^ secretNumber;
    }

    private static long prune(long secretNumber) {
        return secretNumber % 16777216;
    }
}
