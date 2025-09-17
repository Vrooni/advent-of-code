package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day16 {
    public static void main(String[] args) {
        //Part one
        String input = Utils.readString("16.txt");
        String programs = "abcdefghijklmnop";

        for (String move : input.split(",")) {

            switch (move.charAt(0)) {
                case 's' -> {
                    int size = Integer.parseInt(move.substring(1));
                    int index = programs.length() - size;
                    programs = programs.substring(index) + programs.substring(0, index);
                }
                case 'x' -> {
                    String[] indexes = move.substring(1).split("/");
                    int index1 = Integer.parseInt(indexes[0]);
                    int index2 = Integer.parseInt(indexes[1]);

                    programs = Utils.swap(programs, index1, index2);
                }
                case 'p' -> {
                    String[] programNames = move.substring(1).split("/");
                    int index1 = programs.indexOf(programNames[0]);
                    int index2 = programs.indexOf(programNames[1]);

                    programs = Utils.swap(programs, index1, index2);
                }
            }
        }

        System.out.println(programs);


        //Part two
        programs = "abcdefghijklmnop";

        //Sounds crazy, but there's a loop
        for (int i = 0; i < 1_000_000_000 % 60; i++) {
            for (String move : input.split(",")) {

                switch (move.charAt(0)) {
                    case 's' -> {
                        int size = Integer.parseInt(move.substring(1));
                        int index = programs.length() - size;
                        programs = programs.substring(index) + programs.substring(0, index);
                    }
                    case 'x' -> {
                        String[] indexes = move.substring(1).split("/");
                        int index1 = Integer.parseInt(indexes[0]);
                        int index2 = Integer.parseInt(indexes[1]);

                        programs = Utils.swap(programs, index1, index2);
                    }
                    case 'p' -> {
                        String[] programNames = move.substring(1).split("/");
                        int index1 = programs.indexOf(programNames[0]);
                        int index2 = programs.indexOf(programNames[1]);

                        programs = Utils.swap(programs, index1, index2);
                    }
                }
            }
        }

        System.out.println(programs);
    }
}
