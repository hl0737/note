package bean;

public class Pair {
    public Point a;
    public Point b;
    public double mDistance;

    public Pair(Point a, Point b, double distance) {
        this.a = a;
        this.b = b;
        this.mDistance = distance;
    }

    public Pair() {
    }

    public static Pair maxDP() {
        Pair pair = new Pair();
        pair.mDistance = Double.MAX_VALUE;
        return pair;
    }

    public void inflate(Point a, Point b, double distance) {
        this.a = a;
        this.b = b;
        mDistance = distance;
    }
}
