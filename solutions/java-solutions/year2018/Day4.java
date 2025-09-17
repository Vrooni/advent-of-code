package year2018;


import year2018.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Day4 {
    private record Record(LocalDateTime time, String note) {}

    private record SleepTime(int from, int to) {}

    private record StrategyInformation(int mostSleptMinute, int count) {}

    private static class Guard {
        String id;
        List<SleepTime> sleepTimes = new ArrayList<>();
        int sleepTime = 0;
        StrategyInformation strategyInformation = new StrategyInformation(-1, 0);

        public Guard(String id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<Record> records = new ArrayList<>(
                Utils.readLines("04.txt").stream()
                        .map(Day4::getRecord)
                        .toList()
        );
        records.sort((record1, record2) -> {
            LocalDateTime time1 = record1.time;
            LocalDateTime time2 = record2.time;

            return time1.compareTo(time2);
        });

        List<Guard> guards = new ArrayList<>(getGuards(records));
        calculateMostSleptMinutes(guards);

        Guard sleepyGuard = getSleepiest(guards);
        System.out.println(Integer.parseInt(sleepyGuard.id) * sleepyGuard.strategyInformation.mostSleptMinute);


        //Part two
        guards.sort((guard1, guard2) -> Integer.compare(guard2.strategyInformation.count, guard1.strategyInformation.count));
        System.out.println(Integer.parseInt(guards.get(0).id) * guards.get(0).strategyInformation.mostSleptMinute);
    }

    private static Record getRecord(String record) {
        String[] splitRecord = record.split("] ");
        String time = splitRecord[0].replace("[", "");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new Record(LocalDateTime.parse(time, dtf), splitRecord[1]);
    }

    private static List<Guard> getGuards(List<Record> records) {
        Map<String, Guard> guards = new HashMap<>();

        int index = 0;
        while (true) {
            String id = records.get(index).note.split(" ")[1].replace("#", "");
            Guard guard = guards.getOrDefault(id, new Guard(id));
            int asleepTime = 0;

            outer: for (int j = index + 1; true; j++) {
                Record record = records.get(j);
                String note = record.note;

                switch (note.split(" ")[0]) {
                    case "Guard" -> { index = j; break outer; }
                    case "falls" -> asleepTime = record.time.getMinute();
                    case "wakes" -> {
                        guard.sleepTimes.add(new SleepTime(asleepTime, record.time.getMinute() - 1));
                        guard.sleepTime += record.time.getMinute() - asleepTime;
                    }
                }

                if (j == records.size() - 1) {
                    return guards.values().stream().toList();
                }
            }

            guards.put(id, guard);
        }
    }

    private static void calculateMostSleptMinutes(Collection<Guard> guards) {
        for (Guard guard : guards) {
            if (guard.sleepTimes.isEmpty()) {
                continue;
            }

            Map<Integer, Integer> minutesCount = new HashMap<>();
            for (SleepTime sleepTime : guard.sleepTimes) {
                for (int i = sleepTime.from; i <= sleepTime.to; i++) {
                    minutesCount.put(i, minutesCount.getOrDefault(i, 0) + 1);
                }
            }

            int maxCount = Utils.max(minutesCount.values());
            guard.strategyInformation = new StrategyInformation(Utils.getKeyFromValue(minutesCount, maxCount), maxCount);
        }
    }

    private static Guard getSleepiest(Collection<Guard> guards) {
        Guard sleepyGuard = null;
        int maxSleepTime = 0;

        for (Guard guard : guards) {
            if (guard.sleepTime > maxSleepTime) {
                sleepyGuard = guard;
                maxSleepTime = guard.sleepTime;
            }
        }

        return sleepyGuard;
    }
}
