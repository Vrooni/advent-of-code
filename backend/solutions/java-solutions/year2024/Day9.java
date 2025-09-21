package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day9 {
    private static class File {
        int id;
        int index;
        int size;

        public File(int id, int index, int size) {
            this.id = id;
            this.index = index;
            this.size = size;
        }
    }

    private static class EmptySpace {
        int index;
        int size;

        public EmptySpace(int index, int size) {
            this.index = index;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        //Part one
        part1();

        //Part two
        part2();
    }

    private static void part1() {
        String input = Utils.readString("09.txt");
        LinkedList<Integer> blocks = new LinkedList<>();

        for (int i = 0; i < input.length(); i++) {
            int numberOfBlocks = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                int id = i/2;
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(id);
                }
            } else {
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(-1);
                }
            }
        }

        long checksum = 0;
        int index = 0;
        while (!blocks.isEmpty()) {
            int id = blocks.pollFirst();

            while (id == -1) {
                id = blocks.pollLast();
            }

            checksum += (long) index * id;
            index++;

        }

        System.out.println(checksum);
    }

    private static void part2() {
        String input = Utils.readString("09.txt");
        List<Integer> blocks = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            int numberOfBlocks = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                int id = i/2;
                files.add(new File(id, blocks.size(), numberOfBlocks));
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(id);
                }
            } else {
                for (int j = 0; j < numberOfBlocks; j++) {
                    blocks.add(-1);
                }
            }
        }

        Collections.reverse(files);
        for (File file : files) {
            List<Integer> space = Collections.nCopies(file.size, -1);
            int index = Collections.indexOfSubList(blocks, space);
            if (index == -1 || index >= file.index) {
                continue;
            }

            for (int i = index; i < index + file.size; i++) {
                blocks.set(i, file.id);
            }

            for (int i = file.index; i < file.index + file.size; i++) {
                blocks.set(i, -1);
            }
        }

        long checksum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int block = blocks.get(i);
            checksum += i * (block == -1 ? 0 : block);
        }
        System.out.println(checksum);
    }

    private static void print(List<Integer> blocks) {
        for (Integer block : blocks) {
            if (block == -1) {
                System.out.print(".");
            } else {
                System.out.print(block);
            }
        }
        System.out.println();
    }
}
