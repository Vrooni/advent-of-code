package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/21.txt"));
        Map<String, String> monkeys = new HashMap<>();

        for (String line : lines) {
            String[] splittedLine = line.split(": ");

            String name = splittedLine[0];
            String number = splittedLine[1];

            monkeys.put(name, number);
        }

        System.out.println(getNumberOfRoot(monkeys, "root"));

        //Part two
        for (String line : lines) {
            String[] splittedLine = line.split(": ");

            String name = splittedLine[0];
            String number = splittedLine[1];

            monkeys.put(name, number);
        }

        monkeys.put("root", getEqualsCheck(monkeys.get("root")));
        monkeys.put("humn", "humn");

        String equation = (getNumberOfRoot2(monkeys, "root"));
        System.out.println(getResult(equation));
    }

    private static long getNumberOfRoot(Map<String, String> monkeys, String monkey) {
        String number = monkeys.get(monkey);

        if (isNumeric(number)) {
            return Long.parseLong(number);
        }

        if (number.contains("*")) {
            String[] operands = number.split(" [*] ");
            return getNumberOfRoot(monkeys, operands[0]) * getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("/")) {
            String[] operands = number.split(" / ");
            return getNumberOfRoot(monkeys, operands[0]) / getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("+")) {
            String[] operands = number.split(" [+] ");
            return getNumberOfRoot(monkeys, operands[0]) + getNumberOfRoot(monkeys, operands[1]);
        } else if (number.contains("-")) {
            String[] operands = number.split(" - ");
            return getNumberOfRoot(monkeys, operands[0]) - getNumberOfRoot(monkeys, operands[1]);
        } else {
            throw new RuntimeException("No operator");
        }
    }

    private static String getNumberOfRoot2(Map<String, String> monkeys, String monkey) {
        String number = monkeys.get(monkey);

        if (number.equals("humn")) {
            return "(humn)";
        }

        if (isNumeric(number)) {
            return number;
        }

        if (number.contains("*")) {
            String[] operands = number.split(" [*] ");
            String operand1 = getNumberOfRoot2(monkeys, operands[0]);
            String operand2 = getNumberOfRoot2(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) * Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "*" + operand2 + ")";
            }

        } else if (number.contains("/")) {
            String[] operands = number.split(" / ");
            String operand1 = getNumberOfRoot2(monkeys, operands[0]);
            String operand2 = getNumberOfRoot2(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) / Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "/" + operand2 + ")";
            }

        } else if (number.contains("+")) {
            String[] operands = number.split(" [+] ");
            String operand1 = getNumberOfRoot2(monkeys, operands[0]);
            String operand2 = getNumberOfRoot2(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) + Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "+" + operand2 + ")";
            }

        } else if (number.contains("-")) {
            String[] operands = number.split(" - ");
            String operand1 = getNumberOfRoot2(monkeys, operands[0]);
            String operand2 = getNumberOfRoot2(monkeys, operands[1]);

            if (isNumeric(operand1) && isNumeric(operand2)) {
                long result = Long.parseLong(operand1) - Long.parseLong(operand2);
                return String.valueOf(result);
            } else {
                return "(" + operand1 + "-" + operand2 + ")";
            }

        } else if (number.contains("=")) {
            String[] operands = number.split("=");
            String operand1 = getNumberOfRoot2(monkeys, operands[0]);
            String operand2 = getNumberOfRoot2(monkeys, operands[1]);

            return operand1 + "=" + operand2;
        }

        else {
            throw new RuntimeException("No operator");
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static String getEqualsCheck(String calc) {
        if (calc.contains("*")) {
            return calc.replace(" * ", "=");
        } else if (calc.contains("/")) {
            return calc.replace(" / ", "=");
        } else if (calc.contains("+")) {
            return calc.replace(" + ", "=");
        } else if (calc.contains("-")) {
            return calc.replace(" - ", "=");
        } else {
            throw new RuntimeException("No operator");
        }
    }

    private static long getResult(String equation) {
        while (true) {
            long wanted = getWanted(equation);
            String expression = getExpression(equation);

            if (expression.equals("(humn)")) {
                return wanted;
            }

            expression = expression.substring(1, expression.length() - 1); //remove ()

            String operand;
            String operator;

            if (!expression.startsWith("(")) {
                int indexOfBrace = expression.indexOf("(");

                operator = expression.substring(indexOfBrace - 1, indexOfBrace);

                if (operator.equals("-") || operator.equals("/")) {
                    operand = expression.substring(indexOfBrace);
                    expression = expression.substring(0, indexOfBrace-1);
                } else {
                    operand = expression.substring(0, indexOfBrace-1);
                    expression = expression.substring(indexOfBrace);
                }

            } else if (!expression.endsWith(")")) {
                int indexOfBrace = expression.lastIndexOf(")");

                operator = expression.substring(indexOfBrace + 1, indexOfBrace + 2);
                operand = expression.substring(indexOfBrace + 2);
                expression = expression.substring(0, indexOfBrace+1);
            } else {
                throw new RuntimeException("Klammern hat so ned funksioniert");
            }

            String newWanted = "";
            if (operator.equals("*")) {
                newWanted = String.valueOf(wanted / Long.parseLong(operand));

            } else if (operator.equals("/")) {
                if (isNumeric(operand)) {
                    newWanted = String.valueOf(wanted * Long.parseLong(operand));
                } else {
                    newWanted = "(" + wanted + "*" + operand + ")";
                }

            } else if (operator.equals("+")) {
                newWanted = String.valueOf(wanted - Long.parseLong(operand));

            } else if (operator.equals("-")) {
                if (isNumeric(operand)) {
                    newWanted = String.valueOf(wanted + Long.parseLong(operand));
                } else {
                    newWanted = "(" + wanted + "+" + operand + ")";
                }

            } else {
                throw new RuntimeException("No operator found");
            }

            equation = newWanted + "=" + expression;
        }
    }

    private static long getWanted(String equation) {
        String[] splittedEquation = equation.split("=");;

        if (!splittedEquation[0].contains("humn")) {
            return Long.parseLong(splittedEquation[0]);
        } else if (!splittedEquation[1].contains("humn")) {
            return Long.parseLong(splittedEquation[1]);
        } else {
            throw new RuntimeException("No result given");
        }
    }

    private static String getExpression(String equation) {
        String[] splittedEquation = equation.split("=");

        if (!splittedEquation[0].contains("humn")) {
            return splittedEquation[1];
        } else if (!splittedEquation[1].contains("humn")) {
            return splittedEquation[0];
        } else {
            throw new RuntimeException("No result given");
        }
    }
}
