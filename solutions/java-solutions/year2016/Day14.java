package year2016;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day14 {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static final String salt = "qzyelonm";
    private static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //Part one
        Map<String, String> hashes = new HashMap<>();
        int keys = 0;
        int index;

        for (index = 0; keys < 64; index++) {
            String key = salt + index;

            if (!hashes.containsKey(key)) {
                hashes.put(key, getHash(key, false));
            }

            if (isValid(key, index, hashes, false)) {
                keys++;
            }
        }

        System.out.println(index - 1);


        //Part two
        hashes = new HashMap<>();
        keys = 0;

        for (index = 0; keys < 64; index++) {
            String key = salt + index;

            if (!hashes.containsKey(key)) {
                hashes.put(key, getHash(key, true));
            }

            if (isValid(key, index, hashes, true)) {
                keys++;
            }
        }

        System.out.println(index - 1);
    }

    private static boolean isValid(String key, int index, Map<String, String> hashes, boolean multiple) throws UnsupportedEncodingException {
        String hash = hashes.get(key);
        char triple = getTriple(hash);

        if (triple == ' ') {
            return false;
        }

        for (int i = index + 1; i <= index + 1000; i++) {
            String otherKey = salt + i;

            if (!hashes.containsKey(otherKey)) {
                hashes.put(otherKey, getHash(otherKey, multiple));
            }

            if (hashes.get(otherKey).contains(String.valueOf(triple).repeat(5))) {
                return true;
            }
        }

        return false;
    }

    private static char getTriple(String hash) {
        for (int i = 0; i < hash.length() - 2; i++) {
            char c1 = hash.charAt(i);
            char c2 = hash.charAt(i + 1);
            char c3 = hash.charAt(i + 2);

            if (c1 == c2 && c1 == c3) {
                return c1;
            }
        }

        return ' ';
    }

    private static String getHash(String key, boolean multiple) throws UnsupportedEncodingException {
        if (multiple) {
            String hash = bytesToHex(md.digest(key.getBytes("UTF-8"))).toLowerCase();

            for (int i = 0; i < 2016; i++) {
                hash = bytesToHex(md.digest(hash.getBytes("UTF-8"))).toLowerCase();
            }

            return hash;
        }

        return bytesToHex(md.digest(key.getBytes("UTF-8"))).toLowerCase();
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