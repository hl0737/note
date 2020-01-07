package ui;

import tools.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TxtListener implements ActionListener {
    JTextPane mArea;
    JPanel mParent;
    SearchListener mSearchListener;

    public TxtListener(SearchListener searchListener, JTextPane area, JPanel panel) {
        mSearchListener = searchListener;
        mArea = area;
        mParent = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 打开文件选择框
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        int v = chooser.showOpenDialog(null);
        if (v == chooser.APPROVE_OPTION) {
            // 读文件
            String s = Tool.readFile(chooser.getSelectedFile().toString());
            mArea.setText(s);
            mSearchListener.reset();
        }
    }
}
