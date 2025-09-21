package year2019;

public class Day4 {
    private static final String RANGE = "168630-718098";
    
    public static void main(String[] args) {
        //Part one
        int from = Integer.parseInt(RANGE.split("-")[0]);
        int to = Integer.parseInt(RANGE.split("-")[1]);

        int validPasswords = 0;

        for (int i = from; i <= to; i++) {
            if (isValid(i, true)) {
                validPasswords++;
            }
        }

        System.out.println(validPasswords);


        //Part two
        validPasswords = 0;

        for (int i = from; i <= to; i++) {
            if (isValid(i, false)) {
                validPasswords++;
            }
        }

        System.out.println(validPasswords);
    }

    private static boolean isValid(int i, boolean multipleAdjacent) {
        boolean adjacent = false;
        char[] password = String.valueOf(i).toCharArray();

        for (int j = 1; j < password.length; j++) {
            if (password[j-1] > password[j]) {
                return false;
            }

            if (multipleAdjacent) {
                if (password[j-1] == password[j]) {
                    adjacent = true;
                }
            } else {
                if (password[j-1] == password[j]
                        && (j == 1 || password[j-2] != password[j])
                        && (j == password.length-1 || password[j+1] != password[j])) {
                    adjacent = true;
                }
            }
        }

        return adjacent;
    }
}
