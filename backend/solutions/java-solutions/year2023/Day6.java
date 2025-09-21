package year2023;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day6 {

    public static void main(String[] args) throws IOException {
        //Part one
        List<String> lines = Utils.readLines(Path.of("src/year2023/files/06.txt"));
        List<Integer> times = new ArrayList<>();
        List<Integer> records = new ArrayList<>();

        int recordsBeat = 1;

        String timeLine = lines.get(0).replace("Time:", "");
        while (!timeLine.trim().isEmpty()) {
            String time = timeLine.trim().split(" ")[0];
            timeLine = timeLine.replace(time, "");

            times.add(Integer.parseInt(time));
        }

        String recordLine = lines.get(1).replace("Distance:", "");
        while (!recordLine.trim().isEmpty()) {
            String record = recordLine.trim().split(" ")[0];
            recordLine = recordLine.replace(record, "");

            records.add(Integer.parseInt(record));
        }

        for (int i = 0; i < records.size(); i++) {
            int time = times.get(i);
            int record = records.get(i);
            int recordsBeatThisRound = 0;

            for (int j = 0; j < time; j++) {
                int myRecord = (time - j) * j;
                if (myRecord > record) {
                    recordsBeatThisRound++;
                }
            }

            recordsBeat *= recordsBeatThisRound;
        }

        System.out.println(recordsBeat);


        //Part two
        long time = Long.parseLong(lines.get(0)
                .replace("Time:", "")
                .replaceAll(" ", ""));
        long record = Long.parseLong(lines.get(1)
                .replace("Distance:", "")
                .replaceAll(" ", ""));

        long minimumTime = 0;
        long maximumTime = 0;

        for (long i = 0; i < time; i++) {
            long myRecord = (time - i) * i;
            if (myRecord > record) {
                minimumTime = i;
                break;
            }
        }

        for (long i = time; i >= 0; i--) {
            long myRecord = (time - i) * i;
            if (myRecord > record) {
                maximumTime = i + 1;
                break;
            }
        }

        System.out.println(maximumTime - minimumTime);
    }
}
