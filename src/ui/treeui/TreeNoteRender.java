package ui.treeui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by wuwenchuan on 2017/5/24.
 */
public class TreeNoteRender extends DefaultTreeCellRenderer {
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


//    protected Color m_textSelectionColor;
//    protected Color m_textNonSelectionColor;
//    protected Color m_bkSelectionColor;
//    protected Color m_bkNonSelectionColor;
//    protected Color m_borderSelectionColor;

    protected boolean m_selected;

    public TreeNoteRender() {
        super();
        setOpaque(false);
//        m_textSelectionColor = UIManager.getColor(
//                "Tree.selectionForeground");
//        m_textNonSelectionColor = UIManager.getColor(
//                "Tree.textForeground");
//        m_bkSelectionColor = UIManager.getColor(
//                "Tree.selectionBackground");
//        m_bkNonSelectionColor = UIManager.getColor(
//                "Tree.textBackground");
//        m_borderSelectionColor = UIManager.getColor(
//                "Tree.selectionBorderColor");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof TreeNode) {
            TreeNode note = (TreeNode)value;
            setText(note.getTreeModel().getTreeTitle());
            setToolTipText(note.getTreeModel().getTreeTitle());

            if (!note.hasExpand()) {
                setText("Retrieving data...");
                setToolTipText("Retrieving data...");
            }

            if (sel) {
                setForeground(getTextSelectionColor());
            } else {
                setForeground(getTextNonSelectionColor());
            }

            if (note.isRoot()) {
                setIcon(ICON_MAGIC);
            } else if (note.getTreeModel().getTreeType() != null) {
                if (note.getTreeModel().getTreeType() == 0) {
                    setIcon(ICON_FOLDER);
                } else {
                    setIcon(ICON_FILE);
                }
            }
        }

        setFont(tree.getFont());
        setForeground(sel ? selectTextColor :
                mouseTextColor);
        setBackground(sel ? selectBackground :
                normalBackground);
        m_selected = sel;

        return this;
    }



    @Override
    protected void paintComponent(Graphics g) {
        Color bColor = getBackground();
        Icon icon = getIcon();

        g.setColor(bColor);
        int offset = 0;
        if (icon != null && getText() != null)
            g.fillRect(offset, 0, getWidth() - 1 - offset,
                    getHeight() - 1);

        if (m_selected) {
            g.setColor(mouseBackground);
            g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
        }
        super.paintComponent(g);
    }
}
