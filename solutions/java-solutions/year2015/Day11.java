package year2015;

public class Day11 {
    private static final String PASSWORD = "cqjxxyzz";

    public static void main(String[] args) {
        //Part one and two
        String password = increasePassword(PASSWORD, PASSWORD.length()-1);

        while (!isValid(password)) {
            password = increasePassword(password, password.length()-1);
        }

        System.out.println(password);
    }

    private static String increasePassword(String password, int index) {
        char newLetter = password.charAt(index);
        newLetter++;

        if (newLetter <= 'z') {
            return replaceAt(password, index, newLetter);
        }

        newLetter = 'a';
        password = replaceAt(password, index, newLetter);
        return increasePassword(password, index-1);
    }

    private static String replaceAt(String password, int index, char letter) {
        StringBuilder result = new StringBuilder(password);
        result.setCharAt(index, letter);

        return String.valueOf(result);
    }

    private static boolean isValid(String password) {
        return hasIncreasingSequence(password) && hasNoMistakenLetters(password) && hasTwoPairs(password);
    }

    private static boolean hasIncreasingSequence(String password) {
        boolean increasingSequence = false;

        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i+2) - password.charAt(i+1) == 1 && password.charAt(i+1) - password.charAt(i) == 1) {
                increasingSequence = true;
                break;
            }
        }

        return increasingSequence;
    }

    private static boolean hasNoMistakenLetters(String password) {
        return !password.contains("i") && !password.contains("o") && !password.contains("l");
    }

    private static boolean hasTwoPairs(String password) {
        int pairs = 0;

        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i+1) == password.charAt(i)) {
                pairs++;
                i++;
            }
        }

        return pairs >= 2;
    }
}
