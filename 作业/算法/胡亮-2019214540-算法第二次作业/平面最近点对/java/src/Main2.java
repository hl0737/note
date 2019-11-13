import bean.Pair;
import processor.DivideProcessor;
import processor.Processor;
import tool.Comparision;
import tool.PointGenerator;

public class Main2 {
    public static final MODE sCurMode = MODE.COMPARE;
    private static final int MILLION = 1010000; // 因为会生成重复的数，所以预留一点buffer

    public static void main(String[] args) {
        if (sCurMode.equals(MODE.COMPARE)) {
            Comparision comparision = new Comparision();
            comparision.compare();
        } else if (sCurMode.equals(MODE.MILLION)) {
            PointGenerator generator = new PointGenerator(MILLION);
            Processor processor = new DivideProcessor(generator);
            Pair result = processor.process();
            System.out.println("平面最近点对是：P1=(" + result.a.x + ", " + result.a.y
                    + ") P2=(" + result.b.x + ", " + result.b.y + ")");
            System.out.println("它们之间的距离是 " + result.mDistance);
        }
    }

    private enum MODE {
        COMPARE, MILLION
    }
}
