public class Day1_1 {

    public static void main(String[] args) {
        int sum = 0;

        for (String line : args) {
            line = line.replaceAll("[^0-9]", "");
            int n = line.length();

            String stringNumber = line.charAt(0) + line.substring(n - 1);
            sum += Integer.parseInt(stringNumber);
        }
        System.out.println(sum);
    }
}
