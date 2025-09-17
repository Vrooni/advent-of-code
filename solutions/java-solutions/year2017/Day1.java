package year2017;

public class Day1 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("01.txt");
        int result = input.charAt(0) == input.charAt(input.length() - 1)
                ? Integer.parseInt(String.valueOf(input.charAt(0)))
                : 0;

        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                result += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        System.out.println(result);


        //Part two
        int steps = input.length() / 2;
        result = 0;

        for (int i = 0; i < input.length(); i++) {
            int next = (i + steps) % input.length();

            if (input.charAt(i) == input.charAt(next)) {
                result += Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }

        System.out.println(result);
    }
}
