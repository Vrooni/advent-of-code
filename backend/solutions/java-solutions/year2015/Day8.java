package year2015;

import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2015/files/08.txt"));
        int charCount = 0;
        int memCount = 0;

        for (String line : lines) {

            int chars = line.length();
            int mems = line.length() - 2;

            for (int i = 0; i < line.length()-1; i++) {
                if (line.charAt(i) == '\\') {
                    switch (line.charAt(i+1)) {
                        case 'x' -> {
                            mems -= 3;
                            i += 3;
                        }
                        case '\\', '\"' -> {
                            mems -= 1;
                            i += 1;
                        }
                        default -> System.out.println("You forgo" + line);
                    }
                }
            }

            charCount += chars;
            memCount += mems;
        }

        System.out.println(charCount - memCount);


        //Part two
        charCount = 0;
        int extendCount = 0;

        for (String line : lines) {

            int chars = line.length();
            int ext = line.length() + 4;

            for (int i = 0; i < line.length()-1; i++) {
                if (line.charAt(i) == '\\') {
                    switch (line.charAt(i+1)) {
                        case 'x' -> {
                            ext += 1;
                            i += 3;
                        }
                        case '\\', '\"' -> {
                            ext += 2;
                            i += 1;
                        }
                        default -> System.out.println("You forgo" + line);
                    }
                }
            }

            charCount += chars;
            extendCount += ext;
        }

        System.out.println(extendCount - charCount);
    }
}
