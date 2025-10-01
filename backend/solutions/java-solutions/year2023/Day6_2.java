import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day6_2 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));

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
