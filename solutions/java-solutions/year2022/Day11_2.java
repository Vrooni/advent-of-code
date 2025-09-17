package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day11_2 {
    static class Monkey {
        List<Long> items = new ArrayList<>();
        Function<Long, Long> operation;
        Function<Long, Integer> test;
        long inspectedItems = 0;
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/11.txt"));
        List<Monkey> monkeys = new ArrayList<>();
        int modulo = 1;

        for (int i = 0; i < (lines.size() + 1) / 7; i++) {
            Monkey monkey = new Monkey();
            String line = lines.get(i * 7 + 1).split(": ")[1];
            String[] list = line.split(", ");
            List<Long> items = new ArrayList<>();

            for (String item : list) {
                items.add(Long.parseLong(item));
            }

            monkey.items = items;


            line = lines.get(i * 7 + 2).split(": new = old ")[1];
            list = line.split(" ");
            String[] finalList = list;
            Function<Long, Long> function = null;

            switch (list[0]) {
                case "+" -> function = (old) -> (old + (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1])));
                case "-" -> function = (old) -> (old - (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1])));
                case "*" -> function = (old) -> (old * (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1])));
                case "/" -> function = (old) -> (old / (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1])));
            }

            monkey.operation = function;


            line = lines.get(i * 7 + 3).split(": divisible by ")[1];
            String line1 = lines.get(i * 7 + 4).split("If true: throw to monkey ")[1];
            String line2 = lines.get(i * 7 + 5).split("If false: throw to monkey ")[1];
            String finalLine = line;

            Function<Long, Integer> test = (value) -> value % Integer.parseInt(finalLine) == 0 ? Integer.parseInt(line1) : Integer.parseInt(line2);

            modulo *= Integer.parseInt(line);
            monkey.test = test;


            monkeys.add(monkey);
        }

        for (int i = 0; i < 10000; i++) {
            for (Monkey monkey : monkeys) {
                for (long item : monkey.items) {
                    item = monkey.operation.apply(item) % modulo;
                    monkey.inspectedItems++;

                    int nextMonkey = monkey.test.apply(item);
                    monkeys.get(nextMonkey).items.add(item);
                }

                monkey.items = new ArrayList<>();
            }
        }

        monkeys.sort(Comparator.comparingLong((Monkey i) -> i.inspectedItems).reversed());
        System.out.println(monkeys.get(0).inspectedItems * monkeys.get(1).inspectedItems);
    }
}
