import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static int scale = 11;
    public static int[] scales;

    static {
        scales = new int[scale];
        for (int i = 0; i < scale; i++) {
            scales[i] = (int) Math.pow(2, i + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < scale; j++) {
            Matrix a = MatrixGenerator.give(scales[j]);
            Matrix b = MatrixGenerator.give(scales[j]);
            long l1 = System.nanoTime();
            Matrix c = a.normalMultiply(b);
            long l2 = System.nanoTime();
            Matrix d = a.strassenMultiply(b);
            long l3 = System.nanoTime();
            output(l1, l2, l3, scales[j]);
        }
    }

    private static void output(long l1, long l2, long l3, int scale) {
        long normalTime = l2 - l1;
        long strassenTime = l3 - l2;
        String s1 = output1(normalTime);
        String s2 = output1(strassenTime);
        System.out.println(scale + " x " + scale + "矩阵");
        System.out.println("普通方法耗时：" + s1);
        System.out.println("strassen方法耗时：" + s2);
    }


}
