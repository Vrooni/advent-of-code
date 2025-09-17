package year2016;

public class Day9 {
    public static void main(String[] args) {
        //Part one
        String file = Utils.readString("09.txt");
        StringBuilder decompressed = new StringBuilder();

        while (!file.isEmpty()) {
            int mStart = file.indexOf("(");
            int mEnd = file.indexOf(")");

            String marker = file.substring(mStart+1, mEnd);

            int length = Integer.parseInt(marker.split("x")[0]);
            int times = Integer.parseInt(marker.split("x")[1]);
            int sStart = mEnd + 1;
            int sEnd = sStart + length;

            decompressed.append(file.substring(0, mStart));
            for (int i = 0; i < times; i++) {
                decompressed.append(file.substring(sStart, sEnd));
            }

            file = file.substring(sEnd);
        }

        System.out.println(decompressed.length());


        //Part two
        file = Utils.readString("09.txt");
        long decompressedLength = getDecompressedLength(file);

        System.out.println(decompressedLength);
    }

    private static long getDecompressedLength(String file) {
        long decompressedLength = 0;

        while (file.contains("(")) {
            int mStart = file.indexOf("(");
            int mEnd = file.indexOf(")");

            String marker = file.substring(mStart+1, mEnd);

            int length = Integer.parseInt(marker.split("x")[0]);
            int times = Integer.parseInt(marker.split("x")[1]);
            int sStart = mEnd + 1;
            int sEnd = sStart + length;

            String partFile = file.substring(sStart, sEnd);
            if (partFile.contains("(")) {
                decompressedLength += times * getDecompressedLength(partFile);
            } else {
                decompressedLength += (long) length * times;
            }

            file = file.substring(sEnd);
        }

        return decompressedLength;
    }
}
