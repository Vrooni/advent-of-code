package year2016;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day17 {
    private static final String PASSCODE = "dmypynyp";
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private record Position(int x, int y, String path) implements Comparable<Position> {

        @Override
        public int compareTo(@NotNull Position o) {
            return Integer.compare(this.path.length(), o.path.length());
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //Part one
        Queue<Position> queue = new PriorityQueue<>();
        queue.offer(new Position(0, 0, ""));

        while (!queue.isEmpty()) {
            Position position = queue.poll();

            if (position.x == 3 && position.y == 3) {
                System.out.println(position.path);
                break;
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            String hash = bytesToHex(md.digest((PASSCODE + position.path).getBytes("UTF-8"))).toLowerCase();

            if (hash.charAt(0) > 'a' && position.y > 0) {
                queue.offer(new Position(position.x, position.y-1, position.path + "U"));
            }

            if (hash.charAt(1) > 'a' && position.y < 3) {
                queue.offer(new Position(position.x, position.y+1, position.path + "D"));
            }

            if (hash.charAt(2) > 'a' && position.x > 0) {
                queue.offer(new Position(position.x-1, position.y, position.path + "L"));
            }

            if (hash.charAt(3) > 'a' && position.x < 3) {
                queue.offer(new Position(position.x+1, position.y, position.path + "R"));
            }
        }


        //Part two
        int maxPath = Integer.MIN_VALUE;
        queue = new PriorityQueue<>();
        queue.offer(new Position(0, 0, ""));

        while (!queue.isEmpty()) {
            Position position = queue.poll();

            if (position.x == 3 && position.y == 3) {
                maxPath = Math.max(maxPath, position.path.length());
                continue;
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            String hash = bytesToHex(md.digest((PASSCODE + position.path).getBytes("UTF-8"))).toLowerCase();

            if (hash.charAt(0) > 'a' && position.y > 0) {
                queue.offer(new Position(position.x, position.y-1, position.path + "U"));
            }

            if (hash.charAt(1) > 'a' && position.y < 3) {
                queue.offer(new Position(position.x, position.y+1, position.path + "D"));
            }

            if (hash.charAt(2) > 'a' && position.x > 0) {
                queue.offer(new Position(position.x-1, position.y, position.path + "L"));
            }

            if (hash.charAt(3) > 'a' && position.x < 3) {
                queue.offer(new Position(position.x+1, position.y, position.path + "R"));
            }
        }

        System.out.println(maxPath);
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
