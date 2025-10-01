import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6_1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
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
    }
}
