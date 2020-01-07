import matcher.Matcher;
import matcher.BFMatcher;
import matcher.BMMatcher;
import matcher.KMPMatcher;
import tools.Tool;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        int n = 2;
        int N = 5;
        Matcher matcher1 = new BFMatcher();
        Matcher matcher2 = new KMPMatcher();
        Matcher matcher3 = new BMMatcher();
        String pattern = "is";
        for (int i = 0; i < N; i++) {
            String name = (int) Math.pow(10,n++) + ".txt";
            String txt = Tool.readFile(Test.class.getResourceAsStream(name));
            long startBF = System.nanoTime();
            int[] matchBF = matcher1.match(txt, pattern);
            long endBF = System.nanoTime();
            System.out.println(timeConvert(endBF - startBF));
            long startKMP = System.nanoTime();
            int[] matchKMP = matcher2.match(txt, pattern);
            long endKMP = System.nanoTime();
            System.out.println(timeConvert(endKMP - startKMP));
            if (!Tool.isArrayEqual(matchBF, matchKMP)) System.exit(-1);
            long startBM = System.nanoTime();
            int[] matchBM = matcher3.match(txt, pattern);
            long endBM = System.nanoTime();
            System.out.println(timeConvert(endBM - startBM));
            if (!Tool.isArrayEqual(matchKMP, matchBM)) System.exit(-1);
        }
    }

    public static String timeConvert(long nano) {
        Map<Integer, String> maps = new HashMap<>();
        maps.put(0, "ns");
        maps.put(1, "us");
        maps.put(2, "ms");
        maps.put(3, "s");
        maps.put(4, "m");
        double l = ((double) nano);
        int count = 0;
        for (; count < 3 && l >= 1000; count++) {
            l /= 1000;
        }
        for (; count == 3 && l >= 60; count++) {
            l /= 60;
        }
        String format = new DecimalFormat("0.00").format(l);
        return format + maps.get(count);
    }
}
