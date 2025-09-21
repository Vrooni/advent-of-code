package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day22 {
    private record Node(int x, int y) {}

    public static void main(String[] args) {
        List<String> input = Utils.readLines("22.txt");
        input = input.subList(2, input.size());
        int pairs = 0;

        for (int i = 0; i < input.size(); i++) {
            String[] splitData = input.get(i).split("\\s+");

            int usedA = Integer.parseInt(splitData[2].replace("T", ""));
            int availA = Integer.parseInt(splitData[3].replace("T", ""));

            for (int j = i+1; j < input.size(); j++) {
                splitData = input.get(j).split("\\s+");

                int usedB = Integer.parseInt(splitData[2].replace("T", ""));
                int availB = Integer.parseInt(splitData[3].replace("T", ""));

                if (usedA != 0 && usedA <= availB || usedB != 0 && usedB <= availA) {
                    pairs++;
                }
            }
        }

        System.out.println(pairs);


        //Part two
        int maxX = input.stream().map(s -> Integer.parseInt(s
                .split("\\s+")[0]
                .split("-")[1].
                replace("x", "")
        )).max(Integer::compare).get();

        int maxAvail = input.stream().map(s -> Integer.parseInt(s
                .split("\\s+")[3]
                .replace("T", "")
        )).max(Integer::compare).get();

        List<Node> wall = new ArrayList<>();
        Node emptyNode = new Node(-1, -1);

        for (String s : input) {
            String[] splitData = s.split("\\s+");

            int x = Integer.parseInt(s.split("\\s+")[0].split("-")[1].replace("x", ""));
            int y = Integer.parseInt(s.split("\\s+")[0].split("-")[2].replace("y", ""));

            int used = Integer.parseInt(splitData[2].replace("T", ""));

            if (used == 0) {
                emptyNode = new Node(x, y);
            } else if (used > maxAvail) {
                wall.add(new Node(x, y));
            }
        }

        int steps = 0;
        int wallWidth = 1 + Math.min(
                emptyNode.x - wall.get(0).x,
                wall.get(wall.size()-1).x - emptyNode.x
        );

        steps += emptyNode.y + (maxX - emptyNode.x);    //way to up right
        steps += 2*wallWidth;                           //way around wall
        steps += 5 * (maxX - 1);                        //spiral way to left


        System.out.println(steps);
    }
}
