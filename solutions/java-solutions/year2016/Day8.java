package year2016;

import java.util.List;

public class Day8 {
    private static final int HEIGHT = 6;
    private static final int WIDTH = 50;

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("08.txt");
        char[][] screen = new char[HEIGHT][WIDTH];

        for (String line : input) {
            String[] splitLine = line.split(" ");

            switch (splitLine[0]) {
                case "rect" -> createRectangle(screen, splitLine[1].split("x"));
                case "rotate" -> rotate(screen, splitLine[1], splitLine[2], splitLine[4]);
            }
        }

        int lidPixels = 0;

        for (char[] lines : screen) {
            for (char pixel : lines) {

                if (pixel == '#') {
                    lidPixels++;
                }
            }
        }

        System.out.println(lidPixels);


        //Part two
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (screen[y][x] == '#') {
                    System.out.print('8');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    private static void createRectangle(char[][] screen, String[] size) {
        int width = Integer.parseInt(size[0]);
        int height = Integer.parseInt(size[1]);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                screen[y][x] = '#';
            }
        }
    }

    private static void rotate(char[][] screen, String type, String positionString, String timesString) {
        int position = Integer.parseInt(positionString.split("=")[1]);
        int times = Integer.parseInt(timesString);

        switch (type) {
            case "row" -> rotateRow(screen, position, times);
            case "column" -> rotateColumn(screen, position, times);
        }
    }

    private static void rotateRow(char[][] screen, int y, int times) {
        char[] rowCopy = new char[WIDTH];

        for (int i = 0; i < WIDTH; i++) {
            rowCopy[i] = screen[y][i];
        }

        for (int i = 0; i < WIDTH; i++) {
            int x = i - times >= 0
                    ? i - times
                    : WIDTH - (times % WIDTH) + i;
            screen[y][i] = rowCopy[x];
        }
    }

    private static void rotateColumn(char[][] screen, int x, int times) {
        char[] columnCopy = new char[HEIGHT];

        for (int i = 0; i < HEIGHT; i++) {
            columnCopy[i] = screen[i][x];
        }

        for (int i = 0; i < HEIGHT; i++) {
            int y = i - times >= 0
                    ? i - times
                    : HEIGHT - (times % HEIGHT) + i;
            screen[i][x] = columnCopy[y];
        }
    }

}
