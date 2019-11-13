package processor;

import bean.Pair;
import bean.Point;
import tool.PointGenerator;

import java.util.List;

public class EnumProcessor extends Processor {
    public EnumProcessor(PointGenerator generator) {
        super(generator);
    }

    @Override
    public Pair process() {
        List<Point> points = mGenerator.getPointList();
        double minDis = PointGenerator.sCoordinateScale;
        for (int i = 0; i < points.size(); i++) {
            Point baseP = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point comP = points.get(j);
                double b = calculateDistance(baseP, comP);
                if (b < minDis) {
                    minDis = b;
                    mShortestPointPair.inflate(baseP, comP, minDis);
                }
            }
        }
        return mShortestPointPair;
    }
}
