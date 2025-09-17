package year2015;


import year2022.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day7 {
    record Func(String a, String b, Function function) { }

    enum Function {
        NOT, AND, OR, RSHIFT, LSHIFT, VALUE
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2015/files/07.txt"));
        Map<String, Integer> values = new HashMap<>();
        Map<String, Func> functions = new HashMap<>();

        for (String line : lines) {
            String[] splittedLine = line.split(" -> ");
            String unknown = splittedLine[1];
            String function = splittedLine[0];

            if (isNumeric(function)) {
                values.put(unknown, Integer.parseInt(function));
            } else {
                functions.put(unknown, getFunc(function));
            }
        }

        while (!functions.isEmpty()) {
            for (Map.Entry<String, Func> entry : functions.entrySet()) {

                int a = 0;
                int b = 0;
                boolean readyToCalculate = true;

                Func func = entry.getValue();

                if (func.a != null) {
                    if (isNumeric(func.a)) {
                        a = Integer.parseInt(func.a);
                    } else if (values.containsKey(func.a)) {
                        a = values.get(func.a);
                    } else {
                        readyToCalculate = false;
                    }
                }

                if (func.b != null) {
                    if (isNumeric(func.b)) {
                        b = Integer.parseInt(func.b);
                    } else if (values.containsKey(func.b)) {
                        b = values.get(func.b);
                    } else {
                        readyToCalculate = false;
                    }
                }

                if (readyToCalculate) {
                    int result = switch (func.function) {
                        case NOT -> (~a) & 0xffff;
                        case OR -> (a | b) & 0xffff;
                        case AND -> (a & b) & 0xffff;
                        case LSHIFT -> (a << b) & 0xffff;
                        case RSHIFT -> (a >> b) & 0xffff;
                        case VALUE -> a;
                    };

                    values.put(entry.getKey() , result);
                    functions.remove(entry.getKey());

                    break;
                }
            }
        }

        int wireA = values.get("a");
        System.out.println(wireA);


        //Part two
        lines = Utils.readLines(Path.of("src/year2015/files/07.txt"));
        values = new HashMap<>();
        functions = new HashMap<>();

        for (String line : lines) {
            String[] splittedLine = line.split(" -> ");
            String unknown = splittedLine[1];
            String function = splittedLine[0];

            if (isNumeric(function)) {
                values.put(unknown, Integer.parseInt(function));
            } else {
                functions.put(unknown, getFunc(function));
            }
        }

        functions.remove("b");
        values.put("b", wireA);


        while (!functions.isEmpty()) {
            for (Map.Entry<String, Func> entry : functions.entrySet()) {

                int a = 0;
                int b = 0;
                boolean readyToCalculate = true;

                Func func = entry.getValue();

                if (func.a != null) {
                    if (isNumeric(func.a)) {
                        a = Integer.parseInt(func.a);
                    } else if (values.containsKey(func.a)) {
                        a = values.get(func.a);
                    } else {
                        readyToCalculate = false;
                    }
                }

                if (func.b != null) {
                    if (isNumeric(func.b)) {
                        b = Integer.parseInt(func.b);
                    } else if (values.containsKey(func.b)) {
                        b = values.get(func.b);
                    } else {
                        readyToCalculate = false;
                    }
                }

                if (readyToCalculate) {
                    int result = switch (func.function) {
                        case NOT -> (~a) & 0xffff;
                        case OR -> (a | b) & 0xffff;
                        case AND -> (a & b) & 0xffff;
                        case LSHIFT -> (a << b) & 0xffff;
                        case RSHIFT -> (a >> b) & 0xffff;
                        case VALUE -> a;
                    };

                    values.put(entry.getKey() , result);
                    functions.remove(entry.getKey());

                    break;
                }
            }
        }

        System.out.println(values.get("a"));
    }

    private static boolean isNumeric(String s) {
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static Func getFunc(String function) {
        Func func = null;

        if (function.contains("NOT")) {
            String[] splittedFunction = function.split("NOT ");
            String a = splittedFunction[1];

            func = new Func(a, null, Function.NOT);
        }

        else if (function.contains("AND")) {
            String[] splittedFunction = function.split(" AND ");
            String a = splittedFunction[0];
            String b = splittedFunction[1];

            func = new Func(a, b, Function.AND);
        }

        else if (function.contains("OR")) {
            String[] splittedFunction = function.split(" OR ");
            String a = splittedFunction[0];
            String b = splittedFunction[1];

            func = new Func(a, b, Function.OR);
        }

        else if (function.contains("LSHIFT")) {
            String[] splittedFunction = function.split(" LSHIFT ");
            String a = splittedFunction[0];
            String b = splittedFunction[1];

            func = new Func(a, b, Function.LSHIFT);
        }

        else if (function.contains("RSHIFT")) {
            String[] splittedFunction = function.split(" RSHIFT ");
            String a = splittedFunction[0];
            String b = splittedFunction[1];

            func = new Func(a, b, Function.RSHIFT);
        }

        else if (!function.contains(" ")) {
            func = new Func(function, null, Function.VALUE);
        }

        if (func == null) {
            throw new RuntimeException("No func " + function);
        }

        return func;
    }
}
