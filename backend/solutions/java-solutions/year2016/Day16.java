package year2016;

public class Day16 {
    private static final String INPUT = "10011111011011001";

    public static void main(String[] args) {
        //Part one
        StringBuilder a = new StringBuilder(INPUT);
        int length = 272;

        while (a.length() < length) {
            String b = new StringBuilder(a).reverse().toString();
            b = flipBits(b);
            a.append("0").append(b);
        }

        String data = a.substring(0, length);
        String checksum = getChecksum(data);

        while (checksum.length() % 2 == 0) {
            checksum = getChecksum(checksum);
        }

        System.out.println(checksum);

        a = new StringBuilder(INPUT);
        length = 35651584;

        while (a.length() < length) {
            String b = new StringBuilder(a).reverse().toString();
            b = flipBits(b);
            a.append("0").append(b);
        }

        data = a.substring(0, length);
        checksum = getChecksum(data);

        while (checksum.length() % 2 == 0) {
            checksum = getChecksum(checksum);
        }

        System.out.println(checksum);
    }

    private static String flipBits(String b) {
        StringBuilder flipped = new StringBuilder();

        for (char bit : b.toCharArray()) {
            flipped.append(bit == '0' ? "1" : "0");
        }

        return flipped.toString();
    }

    private static String getChecksum(String data) {
        StringBuilder checksum = new StringBuilder();

        for (int i = 0; i < data.length() - 1; i += 2) {
            checksum.append(data.charAt(i) == data.charAt(i+1) ? 1 : 0);
        }

        return checksum.toString();
    }
}
