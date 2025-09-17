package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Day15 {
    private static Map<String, Integer> hashMap = new HashMap<>();

    private static class Box {
        List<Lens> lenses;

        Box() {
            this.lenses = new ArrayList<>();
        }
    }

    private static class Lens {
        String label;
        int focalLength;

        Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = List.of(Utils.read(Path.of("src/year2023/files/15.txt")).split(","));
        int result = Utils.sum(lines.stream().map(Day15::getHash).toList());
        System.out.println(result);


        //Part two
        List<Box> boxes = initBoxes();

        for (String line : lines) {
            line = line.replace("\n", "");

            if (line.contains("=")) {
                String[] splitLine = line.split("=");
                insert(boxes, splitLine[0], Integer.parseInt(splitLine[1]));
            } else if (line.contains("-")) {
                delete(boxes, line.replace("-", ""));
            }
        }

        result = 0;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).lenses.size(); j++) {
                result += (i+1) * (j+1) * boxes.get(i).lenses.get(j).focalLength;
            }
        }

        System.out.println(result);
    }

    private static int getHash(String line) {
        int result = 0;

        for (char c : line.replaceAll("\n", "").toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }

        hashMap.put(line, result);
        return result;
    }

    private static List<Box> initBoxes() {
        List<Box> boxes = new ArrayList<>();

        for (int i = 0; i < 256; i++) {
            boxes.add(new Box());
        }

        return boxes;
    }

    private static void insert(List<Box> boxes, String label, int focalLength) {
        int boxIndex = hashMap.containsKey(label) ? hashMap.get(label) : getHash(label);
        int lensIndex = getLensIndex(boxes, label);

        if (lensIndex == -1) {
            boxes.get(boxIndex).lenses.add(new Lens(label, focalLength));
        } else {
            boxes.get(boxIndex).lenses.get(lensIndex).focalLength = focalLength;
        }
    }

    private static void delete(List<Box> boxes, String label) {
        int boxIndex = hashMap.containsKey(label) ? hashMap.get(label) : getHash(label);
        int lensIndex = getLensIndex(boxes, label);

        if (lensIndex != -1) {
            boxes.get(boxIndex).lenses.remove(lensIndex);
        }
    }

    private static int getLensIndex(List<Box> boxes, String label) {
        int boxIndex = hashMap.containsKey(label) ? hashMap.get(label) : getHash(label);

        Box box = boxes.get(boxIndex);
        for (int i = 0; i < box.lenses.size(); i++) {
            if (box.lenses.get(i).label.equals(label)) {
                return i;
            }
        }

        return -1;
    }
}
