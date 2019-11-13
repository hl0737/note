public class Random {
    private static long upper = (long) (Math.pow(2, 32));
    private static long min = 0;

    public long[] constructLongs(int n) {
        java.util.Random random = new java.util.Random();
        long[] longs = random.longs(n, min, upper).toArray();
        return longs;
    }
}
