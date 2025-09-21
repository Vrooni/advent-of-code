package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day2 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2015/files/02.txt"));

        int wrappingPaper = 0;
        for (String line : lines) {
            String[] dimensions = line.split("x");

            int l = Integer.parseInt(dimensions[0]);
            int w = Integer.parseInt(dimensions[1]);
            int h = Integer.parseInt(dimensions[2]);

            wrappingPaper += area(l, w, h) + extraArea(l, w, h);
        }

        System.out.println(wrappingPaper);

        //Part two
        int ribbon = 0;
        for (String line : lines) {
            int[] dimensions = Arrays.stream(line.split("x")).mapToInt(Integer::parseInt).sorted().toArray();

            ribbon += perimeter(dimensions[0], dimensions[1]) + dimensions[0]*dimensions[1]*dimensions[2];
        }

        System.out.println(ribbon);
    }

    private static int area(int l, int w, int h) {
        return 2*l*w + 2*w*h + 2*h*l;
    }

    private static int extraArea(int l, int w, int h) {
        return Math.min(Math.min(l*w, l*h), w*h);
    }

    private static int perimeter(int a, int b) {
        return a+a+b+b;
    }
}
