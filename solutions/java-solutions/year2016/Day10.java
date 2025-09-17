package year2016;

import java.util.*;

public class Day10 {
    private static final List<Integer> VALUES = List.of(61, 17);

    private static class Bot {
        String id;
        String lower;
        String higher;
        List<Integer> values = new ArrayList<>();
    }

    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("10.txt");
        Map<String, Bot> bots = initBots(instructions);

        outer: while (true) {
            List<Bot> botsList = new ArrayList<>((bots.values().stream().filter(bot -> bot.values.size() == 2).toList()));

            for (Bot bot : botsList) {
                if (bot.values.containsAll(VALUES)) {
                    System.out.println(bot.id);
                    break outer;
                }

                moveChip(bots, bot.lower, bot.values.get(0) < bot.values.get(1) ? bot.values.get(0) : bot.values.get(1));
                moveChip(bots, bot.higher, bot.values.get(0) > bot.values.get(1) ? bot.values.get(0) : bot.values.get(1));
                bots.get(bot.id).values = new ArrayList<>();
            }
        }


        //Part two
        bots = initBots(instructions);
        List<Integer> output1 = new ArrayList<>();
        List<Integer> output2 = new ArrayList<>();
        List<Integer> output3 = new ArrayList<>();

        while (true) {
            List<Bot> botsList = new ArrayList<>((bots.values().stream().filter(bot -> bot.values.size() == 2).toList()));

            if (botsList.isEmpty()) {
                break;
            }

            for (Bot bot : botsList) {
                int low = bot.values.get(0) < bot.values.get(1) ? bot.values.get(0) : bot.values.get(1);
                int high = bot.values.get(0) > bot.values.get(1) ? bot.values.get(0) : bot.values.get(1);

                moveChip(bots, bot.lower, low, output1, output2, output3);
                moveChip(bots, bot.higher, high, output1, output2, output3);
                bots.get(bot.id).values = new ArrayList<>();
            }
        }

        System.out.println(output1.get(0) * output2.get(0) * output3.get(0));
    }

    private static Map<String, Bot> initBots(List<String> instructions) {
        Map<String, Bot> bots = new HashMap<>();

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" ");

            switch (splitInstruction[0]) {
                case "bot" -> {
                    Bot bot = bots.computeIfAbsent(splitInstruction[1], key -> new Bot());
                    bot.id = splitInstruction[1];
                    bot.lower = splitInstruction[5] + " " + splitInstruction[6];
                    bot.higher = splitInstruction[10] + " " + splitInstruction[11];
                }
                case "value" -> {
                    Bot bot = bots.computeIfAbsent(splitInstruction[5], key -> new Bot());
                    bot.id = splitInstruction[5];
                    bot.values.add(Integer.parseInt(splitInstruction[1]));
                }
            }
        }

        return bots;
    }

    private static void moveChip(Map<String, Bot> bots, String to, int value) {
        if (to.split(" ")[0].equals("bot")) {
            bots.get(to.split(" ")[1]).values.add(value);
        }
    }

    private static void moveChip(Map<String, Bot> bots, String to, int value, List<Integer> output1, List<Integer> output2, List<Integer> output3) {
        switch (to.split(" ")[0]) {
            case "bot" -> bots.get(to.split(" ")[1]).values.add(value);
            case "output" -> {
                switch (to.split(" ")[1]) {
                    case "0" -> output1.add(value);
                    case "1" -> output2.add(value);
                    case "2" -> output3.add(value);
                }
            }
        }
    }
}
