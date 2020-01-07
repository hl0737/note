package ui;

import javax.swing.*;
import java.awt.*;

public class MyUI {
    public void show() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("字符串匹配");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);

        addComponent(frame);

        frame.setVisible(true);
    }

    private void addComponent(JFrame frame) {
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        JTextPane area = new JTextPane();
        area.setEditable(false);
        JLabel patternLabel = new JLabel("pattern: ");
        JTextField input = new JTextField(32);
        JButton button = new JButton("搜索");
        JLabel totalLabel = new JLabel();
        JLabel curLabel = new JLabel();
        SearchListener searchListener = new SearchListener(input, area, curLabel, totalLabel);
        button.addActionListener(searchListener);
        JButton chooseTxtButton = new JButton("选取txt文件");
        chooseTxtButton.addActionListener(new TxtListener(searchListener, area, panel));
        panel.add(chooseTxtButton);
        panel.add(patternLabel);
        panel.add(input);
        panel.add(button);
        panel.add(curLabel);
        panel.add(totalLabel);
        frame.add(panel, BorderLayout.NORTH);
        // add textarea
        JScrollPane jsp = new JScrollPane(area);
        frame.add(jsp, BorderLayout.CENTER);
    }
}
