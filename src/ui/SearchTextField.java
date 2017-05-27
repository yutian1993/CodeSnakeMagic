package ui;

import util.LogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by wuwenchuan on 2017/5/13.
 */
public class SearchTextField extends JTextField implements KeyListener {
    private SearchTextFieldEvent mEventCallBack;

    public SearchTextField() {
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {        //消费掉Enter键
            mEventCallBack.enterEvent();
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    public interface SearchTextFieldEvent {
        //按下了Enter键
        public void enterEvent();
    }

    public void setEventCallBack(SearchTextFieldEvent mEventCallBack) {
        this.mEventCallBack = mEventCallBack;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }
}
