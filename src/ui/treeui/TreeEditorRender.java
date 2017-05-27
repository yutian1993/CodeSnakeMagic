package ui.treeui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by wuwenchuan on 2017/5/24.
 */
public class TreeEditorRender extends DefaultTreeCellRenderer {
    //图标
    public static final ImageIcon ICON_MAGIC =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/magic.png");
    public static final ImageIcon ICON_FOLDER =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/computer.png");
    public static final ImageIcon ICON_FILE =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/file.png");

    //正常的颜色
    private Color normalBackground = new Color(255, 255, 255);
    private Color normalTextColor = new Color(0, 0, 0);

    //鼠标移动的颜色
    private Color mouseBackground = new Color(215, 255, 240);
    private Color mouseTextColor = new Color(0, 0, 0);

    //选中的颜色
    private Color selectBackground = new Color(239, 239, 218);
    private Color selectTextColor = new Color(255, 0, 0);

    protected boolean m_selected;

    public TreeEditorRender() {
        super();
        setOpaque(false);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//        System.out.println("Get you!");
//        if (value instanceof ui.treeui.TreeNode) {
//            ui.treeui.TreeNode note = (ui.treeui.TreeNode)value;
//            setText(note.getTreeModel().getTreeTitle());
//
//            if (sel) {
//                setForeground(getTextSelectionColor());
//            } else {
//                setForeground(getTextNonSelectionColor());
//            }
//
//            if (note.isRoot()) {
//                setIcon(ICON_MAGIC);
//            } else if (note.getTreeModel().getTreeType() == 0) {
//                setIcon(ICON_FOLDER);
//            } else {
//                setIcon(ICON_FILE);
//            }
//        }
//
//        setFont(tree.getFont());
//        setForeground(sel ? selectTextColor :
//                normalTextColor);
//        setBackground(sel ? selectBackground :
//                normalBackground);
//        m_selected = sel;

        return this;
    }



    @Override
    protected void paintComponent(Graphics g) {
//        Color bColor = getBackground();
//        Icon icon = getIcon();
//
//        g.setColor(bColor);
//        int offset = 0;
//        if (icon != null && getText() != null)
//            g.fillRect(offset, 0, getWidth() - 1 - offset,
//                    getHeight() - 1);
//
//        if (m_selected) {
//            g.setColor(mouseBackground);
//            g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
//            System.out.println("Selected");
//        }
//        super.paintComponent(g);
//        System.out.println("Get you!");
    }
}
