package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day13 {
    interface MyList {}
    record SubList(List<MyList> children) implements MyList { }
    record Value(int value) implements MyList { }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/13.txt"));

        int listsInOrder = 0;
        for (int i = 0; i < lines.size(); i+=3) {
            MyList root1 = getList(lines.get(i));
            MyList root2 = getList(lines.get(i+1));

            int result = isInOrder(root1, root2);
            if (result != -1) {
                listsInOrder+= i/3 + 1;
            }
        }

        System.out.println(listsInOrder);

        //Part two
        MyList start = new SubList(List.of(new SubList(List.of(new Value(2)))));
        MyList stop = new SubList(List.of(new SubList(List.of(new Value(6)))));

        List<MyList> ordered = new ArrayList<>(List.of(start, stop));
        for (int i = 0; i < lines.size(); i+=3) {
            MyList root1 = getList(lines.get(i));
            MyList root2 = getList(lines.get(i + 1));

            ordered.add(root1);
            ordered.add(root2);
        }

        ordered.sort((a, b) -> isInOrder(b, a));

        System.out.println((ordered.indexOf(start)+1) * (ordered.indexOf(stop)+1));
    }

    private static MyList getList(String s) {
        SubList myList = new SubList(new ArrayList<>());

        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == '[') {
                int index = getIndexOfParen(s, i);
                MyList child = getList(s.substring(i, index+1));
                myList.children.add(child);
                i = index;
            } else if (s.charAt(i) != ',' && s.charAt(i) != ' ') {
                if (s.charAt(i+1) == '0') {
                    int value = Integer.parseInt(s.substring(i, i+2));
                    myList.children.add(new Value(value));
                    i++;
                } else {
                    int value = Integer.parseInt(s.substring(i, i+1));
                    myList.children.add(new Value(value));
                }
            }
        }

        return myList;
    }

    private static int getIndexOfParen(String s, int index) {
        int openParen = 0;
        for (int i = index; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                openParen++;
            } else if (s.charAt(i) == ']') {
                openParen--;
            }

            if (openParen == 0) {
                return i;
            }
        }

        return -1;
    }

    private static int isInOrder(MyList l1, MyList l2) {
        if (l1 instanceof SubList list1) {
            if (l2 instanceof SubList list2) {
                for (int i = 0; i < list1.children.size(); i++) {
                    if (list2.children.size() <= i) {
                        return -1;
                    }

                    int result = isInOrder(list1.children.get(i), list2.children().get(i));
                    if (result != 0) {
                        return result;
                    }
                }

                if (list1.children.size() < list2.children.size()) {
                    return 1;
                }
            } else if (l2 instanceof Value value2) {
                return isInOrder(list1, new SubList(List.of(value2)));
            }
        } else if (l1 instanceof Value value1) {
            if (l2 instanceof Value value2) {
                if (value1.value < value2.value) {
                    return 1;
                } else if (value1.value > value2.value) {
                    return -1;
                }
            } else if (l2 instanceof SubList list2) {
                return isInOrder(new SubList(List.of(value1)), list2);
            }
        }

        return 0;
    }
}
