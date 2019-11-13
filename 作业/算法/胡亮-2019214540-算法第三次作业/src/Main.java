import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Sort[] sorts = new Sort[]{
                new InsertionSort(),
                new ShellSort(),
                new QuickSort(),
                new MergeSort(),
                new RadixSort()
        };
        int[] scale = {(int) 1e1, (int) 1e2, (int) 1e3, (int) 1e4, (int) 1e5, (int) 1e6, (int) 1e7, (int) 1e8, (int) (2 * 1e8), (int) 1e9};
        int loop = 3;
        for (int j = 0; j < scale.length; j++) {
            System.out.println("数量级: " + scale[j] + "------------------");
            for (int i = 0; i < loop; i++) {
                System.out.println("第" + (i + 1) + "次排序000000000000000000");
                long[] inn = random.constructLongs(scale[j]);
                for (int k = 0; k < sorts.length; k++) {
                    long[] in = new long[inn.length];
                    System.arraycopy(inn, 0, in, 0, inn.length);
                    Sort sort = sorts[k];
                    long start = System.nanoTime();
                    sort.sort(in);
                    long end = System.nanoTime();
                    long time = end - start;
                    System.out.println(sort.name() + ": " + output1(time));
                }
            }
        }
    }

    private static String output1(long nano) {
        Map<Integer, String> maps = new HashMap<>();
        maps.put(0, "ns");
        maps.put(1, "us");
        maps.put(2, "ms");
        maps.put(3, "s");
        double l = ((double) nano);
        int count = 0;
        for (; count < 3 && l >= 1000; count++) {
            l /= 1000;
        }
        String format = new DecimalFormat("0.00").format(l);
        return format + maps.get(count);
    }
}
