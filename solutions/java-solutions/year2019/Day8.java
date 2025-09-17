package year2019;

import year2019.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("08.txt");
        List<String> layers = getLayers(input);

        String wantedLayer = layers.get(0);
        int minZeros = Integer.MAX_VALUE;

        for (String layer : layers) {
            int zeros = countDigits(layer, '0');
            if (zeros < minZeros) {
                minZeros = zeros;
                wantedLayer = layer;
            }
        }

        System.out.println(countDigits(wantedLayer, '1') * countDigits(wantedLayer, '2'));


        //Part two
        List<Boolean> pixels = new ArrayList<>();
        for (int i = 0; i < 25*6; i++) {
            for (String layer : layers) {
                char pixel = layer.charAt(i);

                if (pixel != '2') {
                    pixels.add(pixel == '0');
                    break;
                }
            }
        }

        for (int i = 0; i < pixels.size(); i++) {
            if (i != 0 && i % 25 == 0) {
                System.out.println();
            }
            System.out.print(pixels.get(i) ? ' ' : '#');
        }
    }

    private static int countDigits(String layer, char digit) {
        int digits = 0;

        for (char color : layer.toCharArray()) {
            if (color == digit) {
                digits++;
            }
        }

        return digits;
    }

    private static List<String> getLayers(String input) {
        List<String> layers = new ArrayList<>();
        int width = 25;
        int height = 6;
        int length = width * height;

        while (!input.isEmpty()) {
            layers.add(input.substring(0, length));
            input = input.substring(length);
        }

        return layers;
    }
}
