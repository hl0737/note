package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import bean.Pair;
import bean.Point;
import processor.EnumProcessor;
import tool.PointGenerator;

import java.util.ArrayList;
import java.util.List;

public class CoordinateController {

    public Pane coordinate;
    private List<Point> points = new ArrayList<>();
    private EnumProcessor processor = new EnumProcessor(new PointGenerator(points));
    private Line theLine;
    private List<Circle> mJoinCircle = new ArrayList<>();

    public void refresh(ActionEvent actionEvent) {
        points.clear();
        coordinate.getChildren().removeAll(mJoinCircle);
        coordinate.getChildren().remove(theLine);
    }

    public void mouseClick(MouseEvent mouseEvent) {
        long x = (long) mouseEvent.getX();
        long y = (long) mouseEvent.getY();
        Point p = new Point();
        p.x = x;
        p.y = y;
        points.add(p);
        drawCircle(x, y);
        Pair pair = null;
        if (points.size() > 1) {
            pair = processor.process();
        }
        drawLine(pair);
    }

    private void drawCircle(long x, long y) {
        Circle circle = new Circle(x, y, 2);
        circle.setFill(new Color(0, 0, 0, 1));
        mJoinCircle.add(circle);
        coordinate.getChildren().add(circle);
    }

    private void drawLine(Pair pair) {
        if (pair == null) return;
        if (theLine == null) {
            theLine = new Line(pair.a.x, pair.a.y, pair.b.x, pair.b.y);
            theLine.setStroke(new Color(1, 0, 0, 1));
            coordinate.getChildren().add(theLine);
            return;
        }
        coordinate.getChildren().remove(theLine);
        theLine.setStartX(pair.a.x);
        theLine.setStartY(pair.a.y);
        theLine.setEndX(pair.b.x);
        theLine.setEndY(pair.b.y);
        coordinate.getChildren().add(theLine);
    }
}
