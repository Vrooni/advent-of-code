package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day21 {
    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("21.txt");
        String password = "abcdefgh";

        for (String instruction : instructions) {
            password = execute(password, instruction, false);
        }

        System.out.println(password);


        //Part two
        password = "fbgdceah";

        for (int i = instructions.size() - 1; i >= 0; i--) {
            password = execute(password, instructions.get(i), true);
        }

        System.out.println(password);
    }

    private static String execute(String password, String instruction, boolean reserve) {

        if (instruction.startsWith("swap position")) {
            String[] splitInstruction = instruction.split(" ");

            int index1 = Integer.parseInt(splitInstruction[2]);
            int index2 = Integer.parseInt(splitInstruction[5]);

            password = swapLetters(password, index1, index2);
        }

        else if (instruction.startsWith("swap letter")) {
            String[] splitInstruction = instruction.split(" ");

            int index1 = password.indexOf(splitInstruction[2]);
            int index2 = password.indexOf(splitInstruction[5]);

            password = swapLetters(password, index1, index2);
        }

        else if (instruction.startsWith("rotate based")) {
            if (reserve) {
                password = findReverseRotation(password, instruction.split(" ")[6]);
            } else {
                int index = password.indexOf(instruction.split(" ")[6]);
                int offset = index >= 4 ? index + 2 : index + 1;

                password = rotate(password, offset, false);
            }
        }

        else if (instruction.startsWith("rotate")) {
            String[] splitInstruction = instruction.split(" ");
            password = rotate(password, Integer.parseInt(splitInstruction[2]), reserve != splitInstruction[1].equals("left"));
        }

        else if (instruction.startsWith("reverse")) {
            String[] splitInstruction = instruction.split(" ");
            StringBuilder builder = new StringBuilder(password);

            int index1 = Integer.parseInt(splitInstruction[2]);
            int index2 = Integer.parseInt(splitInstruction[4]);

            while (index1 < index2) {
                char temp = builder.charAt(index1);
                builder.setCharAt(index1, builder.charAt(index2));
                builder.setCharAt(index2, temp);

                index1++;
                index2--;
            }

            password = builder.toString();
        }

        else {
            String[] splitInstruction = instruction.split(" ");
            StringBuilder builder = new StringBuilder(password);

            int index1 = Integer.parseInt(splitInstruction[reserve ? 5 : 2]);
            int index2 = Integer.parseInt(splitInstruction[reserve ? 2 : 5]);

            char c = builder.charAt(index1);
            builder.deleteCharAt(index1);

            password = safeInsert(builder, index2, c);
        }

        return password;
    }

    private static String swapLetters(String password, int index1, int index2) {
        StringBuilder builder = new StringBuilder(password);

        char temp = builder.charAt(index1);
        builder.setCharAt(index1, builder.charAt(index2));
        builder.setCharAt(index2, temp);

        return builder.toString();
    }

    private static String rotate(String password, int offset, boolean backwards) {
        offset = offset % password.length();

        return backwards
                ? password.substring(offset) + password.substring(0, offset)
                : password.substring(password.length() - offset) + password.substring(0, password.length() - offset);
    }

    private static String findReverseRotation(String password, String letter) {
        for (int i = 1; true; i++) {
            password = rotate(password, 1, true);

            int index = password.indexOf(letter);
            int offset = index >= 4 ? index + 2 : index + 1;

            if (offset == i) {
                return password;
            }
        }
    }

    private static String safeInsert(StringBuilder builder, int index, char c) {
        if (builder.length() == index) {
            builder.append(c);
        } else {
            builder.insert(index, c);
        }

        return builder.toString();
    }
}
