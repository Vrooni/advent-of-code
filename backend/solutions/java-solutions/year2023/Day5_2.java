import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day5_2 {
    record MapInformation(long source, long destination, long length) {
    }

    record Range(long from, long to) {
    }

    public static void main(String[] args) throws IOException {

        // Part one
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        List<Long> seeds;

        long result = Long.MAX_VALUE;

        List<MapInformation> seedToSoilMap = new ArrayList<>();
        List<MapInformation> soilToFertilizerMap = new ArrayList<>();
        List<MapInformation> fertilizerToWaterMap = new ArrayList<>();
        List<MapInformation> waterToLightMap = new ArrayList<>();
        List<MapInformation> lightToTemperatureMap = new ArrayList<>();
        List<MapInformation> temperatureToHumidityMap = new ArrayList<>();
        List<MapInformation> humidityToLocationMap = new ArrayList<>();

        String seedLine = lines.remove(0).replace("seeds: ", "");
        seeds = Arrays.stream(seedLine.split(" "))
                .map(Long::parseLong)
                .toList();

        lines = extractMapInformation(lines, seedToSoilMap);
        lines = extractMapInformation(lines, soilToFertilizerMap);
        lines = extractMapInformation(lines, fertilizerToWaterMap);
        lines = extractMapInformation(lines, waterToLightMap);
        lines = extractMapInformation(lines, lightToTemperatureMap);
        lines = extractMapInformation(lines, temperatureToHumidityMap);
        extractMapInformation(lines, humidityToLocationMap);

        List<Range> seedRanges = new ArrayList<>();
        List<Range> soilRanges;
        List<Range> fertilizerRanges;
        List<Range> waterRanges;
        List<Range> lightRanges;
        List<Range> temperatureRanges;
        List<Range> humidityRanges;
        List<Range> locationRanges;

        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
        }

        soilRanges = mapRange(seedToSoilMap, seedRanges);
        fertilizerRanges = mapRange(soilToFertilizerMap, soilRanges);
        waterRanges = mapRange(fertilizerToWaterMap, fertilizerRanges);
        lightRanges = mapRange(waterToLightMap, waterRanges);
        temperatureRanges = mapRange(lightToTemperatureMap, lightRanges);
        humidityRanges = mapRange(temperatureToHumidityMap, temperatureRanges);
        locationRanges = mapRange(humidityToLocationMap, humidityRanges);

        result = Collections.min(locationRanges.stream().map(range -> range.from).toList());

        System.out.println(result);
    }

    private static List<String> extractMapInformation(List<String> lines, List<MapInformation> map) {
        lines = lines.subList(2, lines.size());
        List<String> linesToDelete = new ArrayList<>();

        for (String line : lines) {
            String[] splittedLine = line.split(" ");

            if (splittedLine.length != 3) {
                break;
            }

            map.add(new MapInformation(
                    Long.parseLong(splittedLine[1]),
                    Long.parseLong(splittedLine[0]),
                    Long.parseLong(splittedLine[2])));

            linesToDelete.add(line);
        }

        lines.removeAll(linesToDelete);
        return lines;
    }

    private static List<Range> mapRange(List<MapInformation> map, List<Range> ranges) {
        List<Range> mappedRanges = new ArrayList<>();

        while (!ranges.isEmpty()) {
            Range range = ranges.get(0);
            boolean foundRange = false;

            for (MapInformation entry : map) {
                Range interRange = getIntersection(range, entry);
                Range rangeBefore = new Range(range.from, interRange.from - 1);
                Range rangeAfter = new Range(interRange.to + 1, range.to);

                // no intersection found -> try next one
                if (interRange.to - interRange.from <= 0) {
                    continue;
                }

                // intersection found -> map the range
                foundRange = true;
                ranges.remove(range);

                long destinationStart = entry.destination + (interRange.from - entry.source);
                long destinationEnd = destinationStart + (interRange.to - interRange.from);
                mappedRanges.add(new Range(destinationStart, destinationEnd));

                // only add remaining ranges, if they're valid
                if (rangeBefore.from < rangeBefore.to) {
                    ranges.add(rangeBefore);
                }
                if (rangeAfter.from < rangeAfter.to) {
                    ranges.add(rangeAfter);
                }
            }

            // no map for range -> range already mapped
            if (!foundRange) {
                ranges.remove(range);
                mappedRanges.add(range);
            }
        }

        return mappedRanges;
    }

    private static Range getIntersection(Range range, MapInformation entry) {
        return new Range(
                Math.max(range.from, entry.source),
                Math.min(range.to, entry.source + entry.length));
    }
}
