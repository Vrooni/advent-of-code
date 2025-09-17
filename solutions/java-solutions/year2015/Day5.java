package year2015;


import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Day5 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2015/files/05.txt"));

        int niceStrings = 0;
        for (String line : lines) {
            if (getVowels(line) < 3) {
                continue;
            }

            if (!hasDoubleLetters(line)) {
                continue;
            }

            if (line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")) {
                continue;
            }

            niceStrings++;
        }

        System.out.println(niceStrings);

        //Part two
        niceStrings = 0;
        for (String line : lines) {
            if (!hasTwoDoubles(line)) {
                continue;
            }

            if (!hasPalindrome(line)) {
                continue;
            }

            niceStrings++;
        }

        System.out.println(niceStrings);
    }

    private static int getVowels(String s) {
        int vowels = 0;
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }

            if (vowels == 3) {
                return vowels;
            }
        }

        return vowels;
    }

    private static boolean hasDoubleLetters(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasTwoDoubles(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i+2; j < s.length() - 1; j++) {
                if (s.charAt(i) == s.charAt(j) && s.charAt(i+1) == s.charAt(j+1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasPalindrome(String s) {
        for (int i = 0; i < s.length() - 2; i++) {
            for (int j = 0; j < s.length() - 1; j++) {
                if (s.charAt(i) == s.charAt(i+2)) {
                    return true;
                }
            }
        }

        return false;
    }
}
