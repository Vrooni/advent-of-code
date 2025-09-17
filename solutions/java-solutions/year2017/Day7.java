package year2017;

import java.util.*;

public class Day7 {
    private record ProgramInformation(List<String> balances, int weight) {}

    private record Result(boolean balanced, int weight) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("07.txt");
        List<String> programNames = new ArrayList<>(input.stream().map(line -> line.split(" ")[0]).toList());

        for (String line : input) {
            String[] splitLine = line.split("->");

            if (splitLine.length > 1) {
                String[] balances = splitLine[1].replaceAll(" ", "").split(",");

                for (String balancedProgram : balances) {
                    programNames.remove(balancedProgram);
                }
            }
        }

        System.out.println(programNames.get(0));


        //Part two
        Map<String, ProgramInformation> programs = new HashMap<>();

        for (String line : input) {
            String name = line.split(" ")[0];
            int weight = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(")")));
            List<String> balances = new ArrayList<>();

            String[] splitLine = line.split("->");
            if (splitLine.length > 1) {
                balances = Arrays.stream(splitLine[1].replaceAll(" ", "").split(",")).toList();
            }

            programs.put(name, new ProgramInformation(balances, weight));
        }

        System.out.println(getOffBalanced(programNames.get(0), programs).weight);
    }

    /**
     * THOUGHTS:
     * tknk -> compare ugml, padx, fwft -> compare 251, 243, 243 => false | uqml - (251 - 243)
     * <p>
     * ugml -> compare gyxo, ebii, jptl -> compare 61, 61, 61 => true | ugml + gyxo + ebii + jptl = 251
     * padx -> compare pbga, havc, qoyq -> compare 66, 66, 66 => true | padx + pbga + havc + qoyq = 243
     * fwft -> compare ktlj, cntj, xhth -> compare 57, 57, 57 => true | fwft + ktlj + cntj + xhth = 243
     * <p>
     * gyxo -> compare nothing => true | 61
     * ebii -> compare nothing => true | 61
     * jptl -> compare nothing => true | 61
     * <p>
     * pbga -> compare nothing => true | 66
     * havc -> compare nothing => true | 66
     * qoyq -> compare nothing => true | 66
     * <p>
     * ktlj -> compare nothing => true | 57
     * cntj -> compare nothing => true | 57
     * xhth -> compare nothing => true | 57
     */
    private static Result getOffBalanced(String program, Map<String, ProgramInformation> programs) {
        ProgramInformation information = programs.get(program);
        List<Integer> weights = new ArrayList<>();

        for (String balanced : information.balances) {
            Result result = getOffBalanced(balanced, programs);
            weights.add(result.weight);

            if (!result.balanced) {
                return result;
            }
        }

        if (weights.isEmpty()) {
            return new Result(true, information.weight);
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        weights.forEach(i -> frequencyMap.put(i, frequencyMap.getOrDefault(i, 0) + 1));

        Optional<Integer> offWeight = weights.stream().filter(i -> frequencyMap.get(i) == 1).findFirst();
        Optional<Integer> supposedWeight = weights.stream().filter(i -> frequencyMap.get(i) > 1).findFirst();

        String programOffWeight = offWeight.isPresent() ? information.balances.get(weights.indexOf(offWeight.get())) : null;

        return new Result(offWeight.isEmpty(), offWeight.isPresent()
                        ? programs.get(programOffWeight).weight - (offWeight.get() - supposedWeight.get())
                        : information.weight + Utils.sum(weights)
        );
    }
}
