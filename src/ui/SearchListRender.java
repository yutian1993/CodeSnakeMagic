package ui;

import service.model.ItemModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by wuwenchuan on 2017/5/22.
 */
public class SearchListRender extends JLabel implements ListCellRenderer, MouseListener {

    private ItemModel itemModel;

    //正常的颜色
    private Color normalBackground = new Color(232, 255, 232);
    private Color normalTextColor = new Color(0, 0, 0);

    //鼠标移动的颜色
    private Color mouseBackground = new Color(215, 255, 240);
    private Color mouseTextColor = new Color(0, 0, 0);

    //选中的颜色
    private Color selectBackground = new Color(239, 239, 218);
    private Color selectTextColor = new Color(255, 0, 0);

    public SearchListRender() {
        super();
        setOpaque(true);
        this.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        setVerticalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof ItemModel) {
            itemModel = (ItemModel) value;
            /*******设置JLable的文字******/
//            String text="<html><body>" + itemModel.getTitle() + "<br/><hr/><body><html/>";
            setText(itemModel.getTitle());//设置JLable的文字
            setToolTipText(itemModel.getTitle());
            /*******设置JLable的图片*****/
            // 得到此图标的 Image,然后创建此图像的缩放版本。
//            Image img=user.u.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
//            setIcon(new ImageIcon(img));//设置JLable的图片
//            setIconTextGap(30);//设置JLable的图片与文字之间的距离

            Color background;
            Color foreground;
            // check if this cell represents the current DnD drop location
            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                    && !dropLocation.isInsert()
                    && dropLocation.getIndex() == index) {
                background = new Color(215, 255, 240);
                foreground = new Color(0, 0, 0);

                // check if this cell is selected
            } else if (isSelected) {
                background = new Color(239, 239, 218);
                foreground = new Color(255, 0, 0);

                // unselected, and not the DnD drop location
            } else {
                background = new Color(232, 255, 232);
                foreground = new Color(0, 0, 0);
            }

            setBackground(background);
            setForeground(foreground);
        }
        return this;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.err.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.err.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.err.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.err.println("mouseEntered");
//        if (!itemModel.isSelect()) {
        this.setBackground(mouseBackground);
        this.setForeground(mouseTextColor);
//        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.err.println("mouseExited");
//        if (!itemModel.isSelect()) {
        this.setBackground(normalBackground);
        this.setForeground(normalTextColor);
//        }
    }
}
