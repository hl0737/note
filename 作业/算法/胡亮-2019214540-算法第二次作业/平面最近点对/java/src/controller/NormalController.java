package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import bean.Pair;
import processor.DivideProcessor;
import processor.Processor;
import tool.PointGenerator;

public class NormalController {
    public Text nearestPair;
    public Text distance;
    public ListView listview;

    public void million(ActionEvent actionEvent) {
        generateNode(1000000);
    }

    public void hundredThousand(ActionEvent actionEvent) {
        generateNode(100000);
    }

    public void tenThousand(ActionEvent actionEvent) {
        generateNode(10000);
    }

    public void thousand(ActionEvent actionEvent) {
        generateNode(1000);
    }

    public void hundred(ActionEvent actionEvent) {
        generateNode(100);
    }

    public void ten(ActionEvent actionEvent) {
        generateNode(10);
    }

    private void generateNode(int n) {
        PointGenerator generator = new PointGenerator(n);
        listview.setItems(FXCollections.observableArrayList(generator.getPointList()));
        Processor processor = new DivideProcessor(generator);
        Pair pair = processor.process();
        String pairNode = "(" + pair.a.x + ", " + pair.a.y + ") - (" + pair.b.x + ", " + pair.b.y + ")";
        nearestPair.setText(pairNode);
        String s = Double.toString(pair.mDistance);
        distance.setText(s);
    }
}
