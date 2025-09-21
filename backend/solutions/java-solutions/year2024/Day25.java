package year2024;

import year2024.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day25 {
    private record Lock(List<Integer> heights) {}
    private record Key(List<Integer> heights) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("25.txt");
        List<List<String>> locksAndKeys = readInput(input);

        List<Lock> locks = readLocks(locksAndKeys);
        List<Key> keys =  readKeys(locksAndKeys);

        int keyLockPairs = 0;
        for (Lock lock : locks) {
            for (Key key : keys) {
                if (matches(lock, key)) {
                    keyLockPairs++;
                }
            }
        }

        System.out.println(keyLockPairs);
    }

    private static boolean matches(Lock lock, Key key) {
        for (int i = 0; i < lock.heights.size(); i++) {
            if (lock.heights.get(i) + key.heights.get(i) > 5) {
                return false;
            }
        }

        return true;
    }

    private static List<List<String>> readInput(List<String> input) {
        List<List<String>> locksAndKeys = new ArrayList<>();
        int startIndex = 0;

        for (int i = 0; i < input.size(); i++) {
            if (i == input.size()-1) {
                locksAndKeys.add(input.subList(startIndex, input.size()));
            }
            if (input.get(i).isEmpty()) {
                locksAndKeys.add(input.subList(startIndex, i));
                startIndex = i + 1;
            }
        }

        return locksAndKeys;
    }

    private static List<Lock> readLocks(List<List<String>> locksAndKeys) {
        List<Lock> locks = new ArrayList<>();

        for (List<String> lockOrKey : locksAndKeys) {
            if (lockOrKey.get(lockOrKey.size()-1).contains(".")) {
                List<Integer> heights = new ArrayList<>();

                for (int x = 0; x < lockOrKey.get(0).length(); x++) {
                    for (int y = lockOrKey.size() - 1; y >= 0; y--) {
                        if (lockOrKey.get(y).charAt(x) == '#') {
                            heights.add(y);
                            break;
                        }
                    }
                }

                locks.add(new Lock(heights));
            }
        }

        return locks;
    }

    private static List<Key> readKeys(List<List<String>> locksAndKeys) {
        List<Key> keys = new ArrayList<>();

        for (List<String> lockOrKey : locksAndKeys) {
            if (lockOrKey.get(0).contains(".")) {
                List<Integer> heights = new ArrayList<>();

                for (int x = 0; x < lockOrKey.get(0).length(); x++) {
                    for (int y = 0; y < lockOrKey.size(); y++) {
                        if (lockOrKey.get(y).charAt(x) == '#') {
                            heights.add(lockOrKey.size() - 1 - y);
                            break;
                        }
                    }
                }

                keys.add(new Key(heights));
            }
        }

        return keys;
    }
}
