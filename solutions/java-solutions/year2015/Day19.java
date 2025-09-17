package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day19 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> input = Utils.readLines(Path.of("src/year2015/files/19.txt"));

        Map<String, String> maps = new HashMap<>();
        String sentenceToMap = input.remove(input.size()-1);
        input.remove(input.size()-1); //empty line

        for (String line : input) {
            String[] splitLine = line.split(" => ");
            maps.put(splitLine[1], splitLine[0]);
        }

        System.out.println(getSolutions(maps, sentenceToMap, true).size());

        //Part two
        int count = 0;

        while (true) {
            boolean done = true;

            for (Map.Entry<String, String> entry : maps.entrySet()) {
                int index = sentenceToMap.indexOf(entry.getKey());

                if (index != -1) {
                    sentenceToMap = sentenceToMap.substring(0, index) + entry.getValue() + sentenceToMap.substring(index + entry.getKey().length());
                    done = false;
                    count++;
                }
            }

            if (done) {
                break;
            }
        }

        System.out.println(count);
    }

    private static Set<String> getSolutions(Map<String, String> maps, String sentenceToMap, boolean invert) {
        Set<String> molecules = new HashSet<>();

        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = invert ? entry.getValue() : entry.getKey();
            String value = invert ? entry.getKey() : entry.getValue();

            for (int i = sentenceToMap.indexOf(key); i != -1; i = sentenceToMap.indexOf(key, i+1)) {
                String mappedSentence = sentenceToMap.substring(0, i) + value + sentenceToMap.substring(i + key.length());
                molecules.add(mappedSentence);
            }
        }

        return molecules;
    }
}
