package year2016;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Day20 {

    record Range(long start, long end) implements Comparable<Range> {
        @Override
        public int compareTo(@NotNull Range o) {
            int result = Long.compare(this.start, o.start);
            return result == 0 ? Long.compare(this.end, o.end) : result;
        }
    }

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("20.txt");
        Set<Range> ranges = new TreeSet<>();

        for (String line : input) {
            String[] splitLine = line.split("-");
            ranges.add(new Range(Long.parseLong(splitLine[0]), Long.parseLong(splitLine[1])));
        }

        long lastEnd = 0;
        for (Range range : ranges) {
            if (range.start > lastEnd) {
                break;
            }

            lastEnd = range.end + 1;
        }

        System.out.println(lastEnd);


        //Part two
        lastEnd = 0;
        long validIds = 0;

        for (Range range : ranges) {
            if (range.start > lastEnd) {
                validIds += range.start - lastEnd;
            }

            lastEnd = Math.max(lastEnd, range.end + 1);
        }

        validIds += 4294967295L - lastEnd + 1;

        System.out.println(validIds);
    }
}
