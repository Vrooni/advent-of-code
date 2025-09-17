package year2024;

import year2024.utils.Utils;

import java.util.*;

public class Day2 {
    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("02.txt");
        int saveReports = 0;

        for (String line : input) {
            List<Integer> report = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (isSave(report)) {
                saveReports++;
            }
        }

        System.out.println(saveReports);

        //Part two
        saveReports = 0;

        for (String line : input) {
            List<Integer> report = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (isSave(report)) {
                saveReports++;
                continue;
            }

            for (int i = 0; i < report.size(); i++) {
                List<Integer> reportCopy = new ArrayList<>(report);
                reportCopy.remove(i);

                if (isSave(reportCopy)) {
                    saveReports++;
                    break;
                }
            }
        }

        System.out.println(saveReports);

    }

    private static boolean isSave(List<Integer> report) {
        boolean desc = report.get(0) - report.get(1) > 0;

        for (int i = 0; i < report.size() - 1; i++) {
            int distance = Math.abs(report.get(i) - report.get(i+1));
            if (distance < 1 || distance > 3) {
                return false;
            }

            if (desc && report.get(i) - report.get(i+1) < 0) {
                return false;
            }

            if (!desc && report.get(i) - report.get(i+1) > 0) {
                return false;
            }
        }

        return true;
    }
}
