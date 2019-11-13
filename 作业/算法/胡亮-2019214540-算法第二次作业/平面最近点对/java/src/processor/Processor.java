package processor;

import bean.Pair;
import bean.Point;
import tool.PointGenerator;

public abstract class Processor {
    protected PointGenerator mGenerator;
    protected Pair mShortestPointPair;

    public Processor(PointGenerator generator) {
        mGenerator = generator;
        mShortestPointPair = new Pair();
    }

    public Processor() {
    }

    public static double calculateDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    public abstract Pair process();
}
