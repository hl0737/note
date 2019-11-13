import java.util.Random;

public class MatrixGenerator {
    public static Matrix give(int n) throws InterruptedException {
        Matrix a = new Matrix(n);
        Random r = new Random(System.currentTimeMillis());
        Thread.sleep(20);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean b = r.nextBoolean();
                int c = b ? 1 : -1;
                a.my[i][j] = c * r.nextInt(11);
            }
        }
        return a;
    }
}
