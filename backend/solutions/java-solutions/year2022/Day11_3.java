package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day11_3 {
    static class Monkey {
        List<Integer> items = new ArrayList<>();
        Function<Integer, Integer> operation;
        Function<Integer, Integer> test;
        int inspectedItems = 0;
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/11.txt"));
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Monkey monkey = new Monkey();
            String line = lines.get(i * 7 + 1).split(": ")[1];
            String[] list = line.split(", ");
            List<Integer> items = new ArrayList<>();

            for (String item : list) {
                items.add(Integer.parseInt(item));
            }

            monkey.items = items;


            line = lines.get(i * 7 + 2).split(": new = old ")[1];
            list = line.split(" ");
            String[] finalList = list;
            Function<Integer, Integer> function = null;

            switch (list[0]) {
                case "+" -> function = (old) -> (old + (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1]))) / 3;
                case "-" -> function = (old) -> (old - (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1]))) / 3;
                case "*" -> function = (old) -> (old * (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1]))) / 3;
                case "/" -> function = (old) -> (old / (finalList[1].equals("old") ? old : Integer.parseInt(finalList[1]))) / 3;
            }

            monkey.operation = function;


            line = lines.get(i * 7 + 3).split(": divisible by ")[1];
            String line1 = lines.get(i * 7 + 4).split("If true: throw to monkey ")[1];
            String line2 = lines.get(i * 7 + 5).split("If false: throw to monkey ")[1];
            String finalLine = line;

            function = (value) -> value % Integer.parseInt(finalLine) == 0 ? Integer.parseInt(line1) : Integer.parseInt(line2);

            monkey.test = function;


            monkeys.add(monkey);
        }

        for (int i = 0; i < 20; i++) {
            for (Monkey monkey : monkeys) {
                for (int item : monkey.items) {
                    item = monkey.operation.apply(item);
                    monkey.inspectedItems++;

                    int nextMonkey = monkey.test.apply(item);
                    monkeys.get(nextMonkey).items.add(item);
                }

                monkey.items = new ArrayList<>();
            }
        }

        monkeys.sort(Comparator.comparingInt((Monkey i) -> i.inspectedItems).reversed());
        System.out.println(monkeys.get(0).inspectedItems * monkeys.get(1).inspectedItems);
    }
}
