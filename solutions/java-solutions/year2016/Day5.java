package year2016;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5 {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Part one
        String roomId = "wtnhxymk";
        StringBuilder password = new StringBuilder();

        MessageDigest md = MessageDigest.getInstance("MD5");

        for (int i = 0; password.length() != 8; i++) {
            byte[] roomIdIndex = (roomId + i).getBytes("UTF-8");
            String hash = bytesToHex(md.digest(roomIdIndex));

            if (hash.startsWith("00000")) {
                password.append(hash.charAt(5));
            }
        }

        System.out.println(password);


        //Part two
        password = new StringBuilder("________");

        for (int i = 0; password.toString().contains("_"); i++) {
            byte[] roomIdIndex = (roomId + i).getBytes("UTF-8");
            String hash = bytesToHex(md.digest(roomIdIndex));

            if (hash.startsWith("00000")) {
                if (hash.charAt(5) <= '7') {
                    int index = Integer.parseInt(String.valueOf(hash.charAt(5)));

                    if (password.charAt(index) == '_') {
                        password.setCharAt(index, hash.charAt(6));
                    }
                }
            }
        }

        System.out.println(password);
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
