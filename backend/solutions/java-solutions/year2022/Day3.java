package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day3 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/03.txt"));

        int priorities = 0;
        for (String line : lines) {
            String compartment1 = line.substring(0, line.length()/2);
            String compartment2 = line.substring(line.length()/2);

            char sameItem = getSameChar(compartment1, compartment2);

            priorities += Character.isLowerCase(sameItem)
                    ? (int) sameItem - 96
                    : (int) sameItem - 38;


        }

        System.out.println(priorities);

        //Part two
        priorities = 0;
        for (int i = 0; i < lines.size(); i+=3) {
            char sameItem = getSameChar(lines.get(i), lines.get(i+1), lines.get(i+2));

            priorities += Character.isLowerCase(sameItem)
                    ? (int) sameItem - 96
                    : (int) sameItem - 38;
        }

        System.out.println(priorities);

    }

    private static char getSameChar(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    return s1.charAt(i);
                }
            }
        }

        throw new RuntimeException("No similar character");
    }

    private static char getSameChar(String s1, String s2, String s3) {
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                for (int k = 0; k < s3.length(); k++) {
                    if (s1.charAt(i) == s2.charAt(j) && s2.charAt(j) == s3.charAt(k)) {
                        return s1.charAt(i);
                    }
                }
            }
        }

        throw new RuntimeException("No similar character");
    }
}
