package year2017;

import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("13.txt");
        int last = Integer.parseInt(input.get(input.size()-1).split(": ")[0]);

        int[] layers = new int[last+1];

        for (String line : input) {
            String[] splitLine = line.split(": ");
            layers[Integer.parseInt(splitLine[0])] = Integer.parseInt(splitLine[1]);
        }

        int caughtLayers = 0;

        for (int i = 0; i < layers.length; i++) {
            int steps = layers[i] * 2 - 2;

            if (layers[i] != 0 && i % steps == 0) {
                caughtLayers += i * layers[i];
            }
        }

        System.out.println(caughtLayers);


        //Part two
        for (int j = 0; true; j++) {
            boolean caught = false;

            for (int i = 0; i < layers.length; i++) {
                int steps = layers[i] * 2 - 2;

                if (layers[i] != 0 && (i+j) % steps == 0) {
                    caught = true;
                    break;
                }
            }

            if (!caught) {
                System.out.println(j);
                return;
            }
        }
    }
}
