package tool;

import bean.Point;

import java.util.*;

public class PointGenerator {
    public static long sCoordinateScale = (int) 1e8;
    private int scale;
    private List<Point> mPointList;

    public PointGenerator(int scale) {
        this.scale = scale;
        mPointList = new ArrayList<>(scale);
        generatePoint();
        sort();
    }

    public PointGenerator(List<Point> pointList) {
        mPointList = pointList;
        this.scale = mPointList.size();
        sort();
    }

    private void generatePoint() {
        Random random = new Random(System.currentTimeMillis());
        Set<Point> tempSet = new LinkedHashSet<>();
        for (int i = 0; i < scale; i++) {
            Point point = new Point();
            // 此处的点的x和y坐标均采用整数，简化模型
            boolean abs = random.nextBoolean();
            int a1 = abs ? 1 : -1;
            long x = a1 * random.nextLong();
            x %= sCoordinateScale;
            boolean abs1 = random.nextBoolean();
            int a2 = abs1 ? 1 : -1;
            long y = a2 * random.nextLong();
            y %= sCoordinateScale;
            point.x = x;
            point.y = y;
            tempSet.add(point);
        }
        mPointList.addAll(tempSet);
    }

    /**
     * 直接对生成的点按x坐标从小到大进行排序，方便进行分治算法
     */
    private void sort() {
        mPointList.sort(Comparator.comparingLong(point -> point.x));
    }

    // 留给GUI的时候用，在UI上点一个点，加入List
    public void insertPoint(Point p) {

    }

    public List<Point> getPointList() {
        return mPointList;
    }
}
