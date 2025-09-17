package year2017;

public class Day9 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("09.txt");

        int score = 0;
        int depth = 0;
        boolean garbage = false;

        for (int i = 0; i < input.length(); i++) {
            if (garbage) {
                switch (input.charAt(i)) {
                    case '!' -> i++;
                    case '>' -> garbage = false;
                }
            } else {
                switch (input.charAt(i)) {
                    case '{' -> depth++;
                    case '}' -> score += depth--;
                    case '<' -> garbage = true;
                }
            }
        }

        System.out.println(score);


        //Part two
        score = 0;
        garbage = false;

        for (int i = 0; i < input.length(); i++) {
            if (garbage) {
                switch (input.charAt(i)) {
                    case '!' -> i++;
                    case '>' -> garbage = false;
                    default -> score++;
                }
            } else {
                if (input.charAt(i) == '<') {
                    garbage = true;
                }
            }
        }

        System.out.println(score);
    }
}
