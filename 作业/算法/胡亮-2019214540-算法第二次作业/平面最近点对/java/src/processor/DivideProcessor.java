package processor;

import bean.Pair;
import bean.Point;
import tool.PointGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DivideProcessor extends Processor {
    public DivideProcessor(PointGenerator generator) {
        super(generator);
    }

    @Override
    public Pair process() {
        List<Point> points = mGenerator.getPointList();
        int left = 0, right = points.size() - 1;
        mShortestPointPair = processInner(left, right);
        return mShortestPointPair;
    }

    private Pair processInner(int left, int right) {
        List<Point> list = mGenerator.getPointList();
        int mid = (left + right) >> 1;
        // 递归终止条件
        if (left == mid || right == mid) return Pair.maxDP();
        if (right - 1 == left)
            return new Pair(list.get(left), list.get(right), calculateDistance(list.get(left), list.get(right)));
        Pair leftShortestPair = processInner(left, mid);
        Pair rightShortestPair = processInner(mid, right);
        Pair shortestPair;
        double d;
        if (leftShortestPair.mDistance <= rightShortestPair.mDistance) {
            shortestPair = leftShortestPair;
            d = leftShortestPair.mDistance;
        } else {
            shortestPair = rightShortestPair;
            d = rightShortestPair.mDistance;
        }
        //找mid-d和mid+d中的点，算他们的最短距离
        Point midPoint = list.get(mid);
        List<Point> midPoints = new ArrayList<>();
        // 扫描mid-d到mid+d中的点
        for (int i = left; i <= right; i++) {
            Point curP = list.get(i);
            if (Math.abs(midPoint.x - curP.x) < d) midPoints.add(curP);
        }
        midPoints.sort((Comparator.comparingLong(point -> point.y)));
        for (int i = 0; i < midPoints.size() - 1; i++) {
            Point p1 = midPoints.get(i);
            // 只用跟它6个往上的点比较即可，它6个往下的点已经被比较过，牺牲一点时间性能换取代码的简单
            for (int j = i + 1; j <= i + 6 && j < midPoints.size(); j++) {
                Point p2 = midPoints.get(j);
                double d1 = calculateDistance(p1, p2);
                if (d1 < d) {
                    d = d1;
                    shortestPair.inflate(p1, p2, d);
                }
            }
        }
        return shortestPair;
    }
}
