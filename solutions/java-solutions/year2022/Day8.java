package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 {
    record Point(int x, int y) {}
    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/08.txt"));
        Set<Point> visibleTrees = new HashSet<>();

        //looking origin left
        for (int y = 0; y < lines.size(); y++) {
            int maxTreeSize = -1;

            for (int x = 0; x < lines.get(0).length(); x++) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin right
        for (int y = 0; y < lines.size(); y++) {
            int maxTreeSize = -1;

            for (int x =  lines.get(0).length() - 1; x >= 0; x--) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin up
        for (int x = 0; x < lines.get(0).length(); x++) {
            int maxTreeSize = -1;

            for (int y = 0; y < lines.size(); y++) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        //looking origin up
        for (int x = 0; x < lines.get(0).length(); x++) {
            int maxTreeSize = -1;

            for (int y = lines.size() - 1; y >= 0; y--) {
                int treeSize = Character.getNumericValue(lines.get(y).charAt(x));

                if (treeSize > maxTreeSize) {
                    visibleTrees.add(new Point(x, y));
                    maxTreeSize = treeSize;
                }
            }
        }

        System.out.println(visibleTrees.size());

        //Part two
        int maxVisibleTrees = 0;
        for (int y = 1; y < lines.size()-1; y++) {
            for (int x = 1; x < lines.get(0).length()-1; x++) {

                //look left
                int productVisibleTrees;
                int countVisibleTrees = 0;
                for (int k = x-1; k >= 0; k--) {
                    if (Character.getNumericValue(lines.get(y).charAt(k)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees = countVisibleTrees;

                //look right
                countVisibleTrees = 0;
                for (int k = x+1; k < lines.get(0).length(); k++) {
                    if (Character.getNumericValue(lines.get(y).charAt(k)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                //look up
                countVisibleTrees = 0;
                for (int k = y-1; k >= 0 ; k--) {
                    if (Character.getNumericValue(lines.get(k).charAt(x)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                //look down
                countVisibleTrees = 0;
                for (int k = y+1; k < lines.size() ; k++) {
                    if (Character.getNumericValue(lines.get(k).charAt(x)) >= Character.getNumericValue(lines.get(y).charAt(x))) {
                        countVisibleTrees++;
                        break;
                    }

                    countVisibleTrees++;
                }
                productVisibleTrees *= countVisibleTrees;

                maxVisibleTrees = Math.max(maxVisibleTrees, productVisibleTrees);
            }
        }

        System.out.println(maxVisibleTrees);
    }
}
