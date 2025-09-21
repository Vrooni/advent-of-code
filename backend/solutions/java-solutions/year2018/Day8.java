package year2018;

import year2018.utils.Utils;
import java.util.*;

public class Day8 {
    private record Node(List<Node> children, List<Integer> metadata) {}

    public static void main(String[] args) {
        //Part one
        List<Integer> input = Arrays.stream(Utils.readString("08.txt")
                .split(" "))
                .map(Integer::parseInt)
                .toList();

        Node head = getNode(new ArrayList<>(input));

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        int metaData = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            metaData += Utils.sum(current.metadata);
            queue.addAll(current.children);
        }

        System.out.println(metaData);


        //Part two
        System.out.println(getValue(head));
    }

    private static Node getNode(List<Integer> input) {
        int sizeChildren = input.remove(0);
        int sizeMetaData = input.remove(0);

        List<Node> children = new ArrayList<>();
        for (int i = 0; i < sizeChildren; i++) {
            children.add(getNode(input));
        }

        List<Integer> metaData = new ArrayList<>();
        for (int i = 0; i < sizeMetaData; i++) {
            metaData.add(input.remove(0));
        }

        return new Node(children, metaData);
    }

    private static int getValue(Node node) {
        if (node.children.isEmpty()) {
            return Utils.sum(node.metadata);
        }

        int value = 0;
        for (Integer index : node.metadata) {
            value += index > 0 && index <= node.children.size()
                    ? getValue(node.children.get(index-1))
                    : 0;
        }

        return value;
    }
}
