package year2018;

import year2018.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("12.txt");

        String initial = input
                .remove(0)
                .replace("initial state: ", "");

        Map<String, String> notes = new HashMap<>();
        for (String note : input.subList(1, input.size())) {
            String[] splitNote = note.split(" => ");
            notes.put(splitNote[0], splitNote[1]);
        }

        System.out.println(getPlants(initial, notes, 20));


        //Part two
        System.out.println(getPlants(initial, notes, 50000000000L));
    }

    private static long getPlants(String initial, Map<String, String> notes, long generations) {
        StringBuilder pots = new StringBuilder(initial);
        long firstIndex = 0;

        for (long i = 0; i < generations; i++) {
            if (!canSpread(pots)) {
                firstIndex += generations - i;
                break;
            }

            //padding
            while (pots.indexOf("#") < 4) {
                pots.insert(0, ".");
                firstIndex--;
            }
            while (pots.length() - pots.lastIndexOf("#") < 5) {
                pots.append(".");
            }

            pots = getNextGen(pots, notes);
        }

        long plants = 0;

        for (int i = 0; i < pots.length(); i++) {
            plants += pots.charAt(i) == '#' ? i + firstIndex : 0;
        }

        return plants;
    }


    private static StringBuilder getNextGen(StringBuilder pots, Map<String, String> notes) {
        StringBuilder nextGen = new StringBuilder();
        nextGen.append('.').append('.');

        for (int j = 2; j < pots.length() - 2; j++) {
            nextGen.append(notes.get(pots.substring(j-2, j+3)));
        }

        nextGen.append('.').append('.');
        return nextGen;
    }

    private static boolean canSpread(StringBuilder pots) {
        int lastPlant = -5;

        for (int i = 0; i < pots.length(); i++) {
            if (pots.charAt(i) == '#') {
                if (i - lastPlant <= 4) {
                    return true;
                }
                lastPlant = i;
            }
        }

        return false;
    }
}
