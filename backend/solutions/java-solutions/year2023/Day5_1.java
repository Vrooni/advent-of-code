import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day5_1 {
    record MapInformation(long source, long destination, long length) {
    }

    record Range(long from, long to) {
    }

    public static void main(String[] args) throws IOException {
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

        for (long seed : seeds) {
            long soil = getOrDefault(seedToSoilMap, seed);
            long fertilizer = getOrDefault(soilToFertilizerMap, soil);
            long water = getOrDefault(fertilizerToWaterMap, fertilizer);
            long light = getOrDefault(waterToLightMap, water);
            long temperature = getOrDefault(lightToTemperatureMap, light);
            long humidity = getOrDefault(temperatureToHumidityMap, temperature);
            long location = getOrDefault(humidityToLocationMap, humidity);

            result = Math.min(location, result);
        }

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

    private static long getOrDefault(List<MapInformation> map, long key) {
        for (MapInformation entry : map) {
            if (key >= entry.source && key <= entry.source + entry.length) {
                long offset = key - entry.source;
                return entry.destination + offset;
            }
        }

        return key;
    }
}
