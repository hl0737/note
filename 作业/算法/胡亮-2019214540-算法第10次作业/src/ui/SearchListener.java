package ui;

import matcher.BMMatcher;
import matcher.Matcher;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {
    JTextField mField;
    JTextPane mArea;
    JLabel mCurLabel;
    JLabel mTotalLabel;
    int count;
    String mLast;
    SimpleAttributeSet mTotalAttributeSet;
    SimpleAttributeSet mCurAttributeSet;
    boolean mClear;
    Matcher matcher = new BMMatcher();
    int[] mLastIndexes;

    public SearchListener(JTextField field, JTextPane area, JLabel curLabel, JLabel totalLabel) {
        count = 0;
        mLast = "";
        mField = field;
        mArea = area;
        mCurLabel = curLabel;
        mTotalLabel = totalLabel;
        mTotalAttributeSet = new SimpleAttributeSet();
        mCurAttributeSet = new SimpleAttributeSet();
        StyleConstants.setBackground(mTotalAttributeSet, new Color(255, 0, 0));
        StyleConstants.setBackground(mCurAttributeSet, new Color(255, 255, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mClear) {
            perform();
        } else if (!mField.getText().equals(mLast)) {
            reset();
            perform();
        } else {
            continuee();
        }
    }

    private void perform() {
        String p = mField.getText();
        String t = mArea.getText();
        int[] indexes = matcher.match(t, p);
        if (indexes.length != 0) {
            mCurLabel.setText((count + 1) + " /");
            // change text color
            StyledDocument doc = mArea.getStyledDocument();
            for (int i = 0; i < indexes.length; i++) {
                if (i == count) {
                    doc.setCharacterAttributes(indexes[i], p.length(), mCurAttributeSet, true);
                } else {
                    doc.setCharacterAttributes(indexes[i], p.length(), mTotalAttributeSet, true);
                }
            }
            count++;
        } else {
            mCurLabel.setText(0 + " /");
        }
        mLast = p;
        mLastIndexes = indexes;
        mTotalLabel.setText(String.valueOf(indexes.length));
        mClear = false;
    }

    private void continuee() {
        if (count == mLastIndexes.length) count = 0;
        String p = mField.getText();
        StyledDocument doc = mArea.getStyledDocument();
        for (int i = 0; i < mLastIndexes.length; i++) {
            if (i == count) {
                doc.setCharacterAttributes(mLastIndexes[i], p.length(), mCurAttributeSet, true);
            } else {
                doc.setCharacterAttributes(mLastIndexes[i], p.length(), mTotalAttributeSet, true);
            }
        }
        mCurLabel.setText((count + 1) + " /");
        count++;
    }

    public void reset() {
        count = 0;
        mClear = true;
        mCurLabel.setText(0 + " /");
        mTotalLabel.setText(0 + "");
        if (mLastIndexes != null) {
            StyledDocument doc = mArea.getStyledDocument();
            SimpleAttributeSet nullSet = new SimpleAttributeSet();
            for (int i = 0; i < mLastIndexes.length; i++) {
                doc.setCharacterAttributes(mLastIndexes[i], mLast.length(), nullSet, true);
            }
        }
    }
}
