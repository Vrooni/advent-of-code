package year2018;

import org.jetbrains.annotations.NotNull;
import year2018.utils.Utils;

import java.util.*;

public class Day7 {
    record Step(String step, List<String> previousSteps, List<Step> nextSteps) implements Comparable<Step> {
        @Override
        public int compareTo(@NotNull Step o) {
            return this.step.compareTo(o.step);
        }
    }

    private static class Worker {
        Step step = null;
        int time = -1;

        public boolean isWorking() {
            return step != null;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("07.txt");
        Map<String, Step> steps = new HashMap<>();

        for (String instruction : instructions) {
            String[] splitInstruction = instruction.split(" ");
            Step step1 = steps.computeIfAbsent(splitInstruction[1], k ->
                    new Step(
                            splitInstruction[1],
                            new ArrayList<>(),
                            new ArrayList<>()
                    )
            );

            Step step2 = steps.computeIfAbsent(splitInstruction[7], k ->
                    new Step(
                            splitInstruction[7],
                            new ArrayList<>(),
                            new ArrayList<>()
                    )
            );

            step1.nextSteps.add(step2);
            step2.previousSteps.add(step1.step);
        }

        List<String> order = new ArrayList<>();
        Queue<Step> queue = new PriorityQueue<>();

        List<Step> firstSteps = steps.values().stream().filter(step -> step.previousSteps.isEmpty()).toList();
        queue.addAll(firstSteps);

        while (!queue.isEmpty()) {
            Step current = queue.poll();
            order.add(current.step);
            System.out.print(current.step);

            for (Step nextStep : current.nextSteps) {
                if (new HashSet<>(order).containsAll(nextStep.previousSteps)) {
                    queue.add(nextStep);
                }
            }
        }

        System.out.println();


        //Part two
        int time = 0;
        order = new ArrayList<>();
        queue = new PriorityQueue<>();
        List<Worker> workers = List.of(
                new Worker(),
                new Worker(),
                new Worker(),
                new Worker(),
                new Worker()
        );

        queue.addAll(firstSteps);
        while (!queue.isEmpty() || workers.stream().anyMatch(Worker::isWorking)) {
            List<String> finishedSteps = new ArrayList<>();

            for (Worker worker : workers) {
                //give him a new job
                if (!worker.isWorking() && !queue.isEmpty()) {
                    Step current = queue.poll();
                    worker.step = current;
                    worker.time = (char) (current.step.charAt(0) - 64) + 60;
                }

                //let him work
                if (worker.isWorking()) {
                    worker.time--;
                }

                //he finished working
                if (worker.time == 0) {
                    order.add(worker.step.step);
                    finishedSteps.add(worker.step.step);
                    worker.step = null;
                    worker.time = -1;
                }
            }

            for (String finishedStep : finishedSteps) {
                for (Step nextStep : steps.get(finishedStep).nextSteps) {
                    if (new HashSet<>(order).containsAll(nextStep.previousSteps)) {
                        queue.add(nextStep);
                    }
                }
            }

            time++;
        }

        System.out.println(time);
    }
}
