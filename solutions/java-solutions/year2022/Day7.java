package year2022;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {
    static class Directory {
        int fileSizes;
        Directory parent;
        Map<String, Directory> directories = new HashMap<>();

        Directory(Directory parent) {
            this.parent = parent;
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2022/files/07.txt"));

        Directory current = new Directory(null);
        final Directory root = current;

        for (String line : lines) {
            if (line.startsWith("$ cd")) {
                String dir = line.substring(5);

                if (dir.equals("..")) {
                    current = current.parent;
                    continue;
                } else if (dir.equals("/")) {
                    continue;
                }

                if (current.directories.get(dir) == null) {
                    current.directories.put(dir, new Directory(current));
                }
                current = current.directories.get(dir);
            } else if (!line.startsWith("$") && !line.startsWith("dir")) {
                current.fileSizes += Integer.parseInt(line.split(" ")[0]);
            }
        }

        calculateFileSizes(root);

        System.out.println(getFileSizes(root));

        //Part two
        int freeSpace = 70000000 - root.fileSizes;
        System.out.println(getFileSizeToDelete(root, 30000000 - freeSpace));

    }

    public static void calculateFileSizes(Directory root) {
        for (Directory dir : root.directories.values()) {
            calculateFileSizes(dir);
            root.fileSizes += dir.fileSizes;
        }
    }

    public static int getFileSizes(Directory root) {
        int fileSize = 0;
        for (Directory dir : root.directories.values()) {
            fileSize += getFileSizes(dir);
        }

        return fileSize + (root.fileSizes <= 100000 ? root.fileSizes : 0);
    }

    public static int getFileSizeToDelete(Directory root, int spaceToDelete) {
        int fileSize = Integer.MAX_VALUE;
        for (Directory dir : root.directories.values()) {
            int fileSizeConcurrent = getFileSizeToDelete(dir, spaceToDelete);
            fileSize = Math.min(fileSize, fileSizeConcurrent);
        }

        if (root.fileSizes >= spaceToDelete) {
            fileSize = Math.min(fileSize, root.fileSizes);
        }

        return fileSize;
    }
}
