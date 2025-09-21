package year2015;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        //Part one
        String keyPrefix = "iwrupvqb";
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        int number = getNumber(keyPrefix, 0, 5);
        System.out.println(number);

        //Part two
        System.out.println(getNumber(keyPrefix, number, 6));
    }

    private static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    private static int getNumber(String keyPrefix, int startNumber, int zeros) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        boolean notFiveZeros = true;

        while (notFiveZeros) {
            String key = keyPrefix + startNumber;
            String output = bytesToHex(md5.digest(key.getBytes(StandardCharsets.UTF_8)));

            notFiveZeros = false;
            for (int j = 0; j < zeros; j++) {
                if (output.charAt(j) != '0') {
                    notFiveZeros = true;
                    break;
                }
            }

            startNumber++;
        }

        return startNumber - 1;
    }
}
