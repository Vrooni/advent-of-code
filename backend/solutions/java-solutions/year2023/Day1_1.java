import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1_1 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int sum = 0;

        for (String line : lines) {
            line = line.replaceAll("[^0-9]", "");
            int n = line.length();

            String stringNumber = line.charAt(0) + line.substring(n - 1);
            sum += Integer.parseInt(stringNumber);
        }
        System.out.println(sum);
    }
}
