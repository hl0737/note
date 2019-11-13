package tool;

import bean.Pair;
import processor.DivideProcessor;
import processor.EnumProcessor;
import processor.Processor;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Comparision {
    private static int[] sScale = {(int) 1e1, (int) 1e2, (int) 1e3, (int) 1e4, (int) 1e5};

    public void compare() {
        // compare enumerate with divide
        for (int j = 0; j < 10; j++) {
            System.out.println("--------第" + (j + 1) + "次比较-------------");
            for (int i = 0; i < sScale.length; i++) {
                int n = sScale[i];
                PointGenerator gene = new PointGenerator(n);
                Processor divider = new DivideProcessor(gene);
                Processor enumeration = new EnumProcessor(gene);
                long t1 = System.nanoTime();
                Pair dividePair = divider.process();
                long t2 = System.nanoTime();
                Pair enumPair = enumeration.process();
                long t3 = System.nanoTime();
                long divideTime = t2 - t1;
                long enumTime = t3 - t2;
                System.out.println("数据规模：" + n);
                System.out.println("分治法最近点对：p1(" + dividePair.a.x + ", " + dividePair.a.y + "), p2(" +
                        dividePair.b.x + ", " + dividePair.b.y + ") 距离：" + dividePair.mDistance);
                System.out.println("枚举法最近点对：p1(" + enumPair.a.x + ", " + enumPair.a.y + "), p2(" +
                        enumPair.b.x + ", " + enumPair.b.y + ") 距离：" + enumPair.mDistance);
                System.out.println("分治法耗时：" + timeConvert(divideTime)
                        + ", 枚举法耗时：" + timeConvert(enumTime));
                if (dividePair.mDistance != enumPair.mDistance)
                    System.out.println(gene.getPointList());
                System.out.println("--------------------------------------------------------");
            }
        }
    }

    private String timeConvert(long nano) {
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
