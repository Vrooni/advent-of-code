package year2016;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {

    public static void main(String[] args) {
        //Part one
        List<String> rooms = Utils.readLines("04.txt");
        int sum = 0;

        room: for (String room : rooms) {
            String end = room.substring(room.lastIndexOf("-") + 1);

            String[] letters = room.substring(0, room.lastIndexOf("-")).replaceAll("-", "").split("");
            String[] checksum = end.substring(end.indexOf("[")+1, end.indexOf("]")).split("");
            int id = Integer.parseInt(end.substring(0, end.indexOf("[")));

            Map<String, Integer> lettersCount = getLetterCounts(letters);

            for (String letter : checksum) {
                int letterCount = lettersCount.containsKey(letter) ? lettersCount.remove(letter) : 0;

                if (letterCount < Collections.max(lettersCount.values())) {
                    continue room;
                }
            }

            sum += id;
        }

        System.out.println(sum);


        //Part two
        sum = 0;

        for (String room : rooms) {
            String end = room.substring(room.lastIndexOf("-") + 1);

            int id = Integer.parseInt(end.substring(0, end.indexOf("[")));
            String letters = room.substring(0, room.lastIndexOf("-")).replaceAll("-", "");
            letters = shiftLetters(letters, id);

            if (letters.contains("north")) {
                System.out.println(id + " (" + letters + ")");
            }
        }
    }

    private static Map<String, Integer> getLetterCounts(String[] letters) {
        Map<String, Integer> lettersCount = new HashMap<>();

        for (String letter : letters) {
            int count = lettersCount.computeIfAbsent(letter, key -> 0);
            lettersCount.put(letter, count + 1);
        }

        return lettersCount;
    }

    private static String shiftLetters(String letters, int id) {
        StringBuilder incrementedLetters = new StringBuilder();
        for (int i = 0; i < letters.length(); i++) {
            int asciiCode = letters.charAt(i) + (id % 26);

            if (asciiCode > 'z') {
                asciiCode -= 26;
            }

            incrementedLetters.append((char) asciiCode);
        }

        return incrementedLetters.toString();
    }
}
