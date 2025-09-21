package year2016;

import java.util.ArrayList;
import java.util.List;

public class Day7 {
    public static void main(String[] args) {
        //Part one
        List<String> ips = Utils.readLines("07.txt");
        int validIps = 0;

        for (String ip : ips) {
            if (supportsTLS(ip)) {
                validIps++;
            }
        }

        System.out.println(validIps);


        //Part two
        validIps = 0;

        for (String ip : ips) {
            if (supportsSSL(ip)) {
                validIps++;
            }
        }

        System.out.println(validIps);
    }

    private static boolean supportsTLS(String ip) {
        boolean hasABBA = false;
        while (!ip.isEmpty()) {

            if (ip.startsWith("[")) {
                String subIp = ip.substring(1, ip.indexOf("]"));
                ip = ip.substring(ip.indexOf("]") + 1);

                if (containsABBA(subIp)) {
                    return false;
                }
            } else if (ip.contains("[")) {
                String subIp = ip.substring(0, ip.indexOf("["));
                ip = ip.substring(ip.indexOf("["));

                if (!hasABBA && containsABBA(subIp)) {
                    hasABBA = true;
                }
            } else {
                if (! hasABBA && containsABBA(ip)) {
                    hasABBA = true;
                }
                ip = "";
            }
        }

        return hasABBA;
    }

    private static boolean containsABBA(String subIp) {
        for (int i = 0; i < subIp.length()-3; i++) {
            if (subIp.charAt(i) == subIp.charAt(i+3)
                    && subIp.charAt(i+1) == subIp.charAt(i+2)
                    && subIp.charAt(i) != subIp.charAt(i+1)) {
                return true;
            }
        }

        return false;
    }

    private static boolean supportsSSL(String ip) {
        List<String> ABAs = new ArrayList<>();
        List<String> BABs = new ArrayList<>();

        while (!ip.isEmpty()) {

            if (ip.startsWith("[")) {
                String subIp = ip.substring(1, ip.indexOf("]"));
                ip = ip.substring(ip.indexOf("]") + 1);

                addABAS(BABs, subIp);
            } else if (ip.contains("[")) {
                String subIp = ip.substring(0, ip.indexOf("["));
                ip = ip.substring(ip.indexOf("["));

                addABAS(ABAs, subIp);
            } else {
                addABAS(ABAs, ip);
                ip = "";
            }
        }

        for (String aba : ABAs) {
            String bab = String.valueOf(aba.charAt(1)) + aba.charAt(0) + aba.charAt(1);
            if (BABs.contains(bab)) {
                return true;
            }
        }

        return false;
    }

    private static void addABAS(List<String> ABAs, String subIp) {
        for (int i = 0; i < subIp.length()-2; i++) {
            if (subIp.charAt(i) == subIp.charAt(i+2) && subIp.charAt(i) != subIp.charAt(i+1)) {
                ABAs.add(subIp.substring(i, i+3));
            }
        }
    }
}
