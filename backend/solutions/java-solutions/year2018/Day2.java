package year2018;

import year2018.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        //Part one
        List<String> ids = Utils.readLines("02.txt");
        int lettersTwice = 0;
        int lettersThreeTimes = 0;

        for (String id : ids) {
            Map<Character, Long> frequencies = id.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));

            lettersTwice += frequencies.values().stream().anyMatch(frequency -> frequency == 2L) ? 1 : 0;
            lettersThreeTimes += frequencies.values().stream().anyMatch(frequency -> frequency == 3L) ? 1 : 0;
        }

        System.out.println(lettersTwice * lettersThreeTimes);


        //Part two
        for (int i = 0; i < ids.size() - 1; i++) {
            for (int j = i+1; j < ids.size(); j++) {
                String id1 = ids.get(i);
                String id2 = ids.get(j);

                if (id1.length() != id2.length()) {
                    continue;
                }

                int differs = 0;
                int differsIndex = 0;
                for (int k = 0; k < id1.length(); k++) {
                    if (id1.charAt(k) != id2.charAt(k)) {
                        differs++;
                        differsIndex = k;
                    }
                }

                if (differs == 1) {
                    System.out.println(id1.substring(0, differsIndex) + id1.substring(differsIndex+1));
                    return;
                }
            }
        }
    }
}
