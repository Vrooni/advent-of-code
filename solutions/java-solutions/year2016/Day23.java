package year2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 {
    private static final Map<String, Integer> register = new HashMap<>();

    /**
     * LOOP is existing: cpy b c, inc a, dec c, jnz c -2, dec d, jnz d -5
     * which always leads to a += b * d and c,d = 0
     **/
    //THOUGHTS:
    //cpy b c, inc a, dec c, jnz c -2, dec d, jnz d -5
    //start: 1, 2, 3, 4
    //3, 2, 0, 4 //inc a, dec c, jnz c-2 LOOP -> a += b
    //3, 2, 0, 3 //dec d, jnz d -5
    //5, 2, 0, 3 //inc a, dec c, jnz c-2 LOOP -> a += b
    //3, 2, 0, 2 //dec d, jnz d -5
    //7, 2, 0, 2 //inc a, dec c, jnz c-2 LOOP -> a += b
    //7, 2, 0, 1 //dec d, jnz d -5
    //9, 2, 0, 1 //inc a, dec c, jnz c-2 LOOP -> a += b
    //finish: 9, 2, 0, 0 //dec d, jnz d -5
    //=> a += b*d, b = b, c = 0, d = 0
    private static final List<String> LOOP = List.of("cpy b c", "inc a", "dec c", "jnz c -2", "dec d", "jnz d -5");

    public static void main(String[] args) {
        //Part one
        List<String> instructions = Utils.readLines("23.txt");
        register.put("a", 7);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            try {
                switch (instruction[0]) {
                    case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                    case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                    case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                    case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
                    case "tgl" -> {
                        int index = i + getValue(instruction[1]);

                        if (index > 0 && index < instructions.size()) {
                            String[] instructionToToggle = instructions.get(index).split(" ");

                            switch (instructionToToggle.length) {
                                case 2 -> instructions.set(index, (instructionToToggle[0].equals("inc") ? "dec " : "inc ") + instructionToToggle[1]);
                                case 3 -> instructions.set(index, (instructionToToggle[0].equals("jnz") ? "cpy " : "jnz ") + instructionToToggle[1] + " " + instructionToToggle[2]);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(instructions.get(i) + "is not valid");
            }
        }

        System.out.println(register.get("a"));


        //Part two
        instructions = Utils.readLines("23.txt");
        register.put("a", 12);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);

        for (int i = 0; i < instructions.size(); i++) {
            String[] instruction = instructions.get(i).split(" ");

            if (i + LOOP.size()-1 < instructions.size() && instructions.subList(i, i+LOOP.size()).equals(LOOP)) {
                register.put("a", register.get("a") + register.get("b") * register.get("d"));
                register.put("c", 0);
                register.put("d", 0);
                i += LOOP.size()-1;
                continue;
            }

            try {
                switch (instruction[0]) {
                    case "cpy" -> register.put(instruction[2], getValue(instruction[1]));
                    case "inc" -> register.put(instruction[1], getValue(instruction[1]) + 1);
                    case "dec" -> register.put(instruction[1], getValue(instruction[1]) - 1);
                    case "jnz" -> i += getValue(instruction[1]) == 0 ? 0 : getValue(instruction[2]) - 1;
                    case "tgl" -> {
                        int index = i + getValue(instruction[1]);

                        if (index > 0 && index < instructions.size()) {
                            String[] instructionToToggle = instructions.get(index).split(" ");

                            switch (instructionToToggle.length) {
                                case 2 -> instructions.set(index, (instructionToToggle[0].equals("inc") ? "dec " : "inc ") + instructionToToggle[1]);
                                case 3 -> instructions.set(index, (instructionToToggle[0].equals("jnz") ? "cpy " : "jnz ") + instructionToToggle[1] + " " + instructionToToggle[2]);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(instructions.get(i) + "is not valid");
            }
        }

        System.out.println(register.get("a"));
    }

    private static int getValue(String s) {
        return register.containsKey(s) ? register.get(s) : Integer.parseInt(s);
    }
}
