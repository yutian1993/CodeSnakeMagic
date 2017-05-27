package ui.treeui;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by wuwenchuan on 2017/5/25.
 */
public class TreeNoteEditor extends DefaultTreeCellEditor implements CellEditorListener {

    public static final ImageIcon ICON_MAGIC =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/magic.png");
    public static final ImageIcon ICON_FOLDER =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/computer.png");
    public static final ImageIcon ICON_FILE =
            new ImageIcon("F:/CodeSnakeMagic/resources/img/file.png");

    private TreeNode treeNode;

    public TreeNoteEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
        addCellEditorListener(this);
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);

        if (value instanceof TreeNode) {
            treeNode = (TreeNode)value;
            if (treeNode.getTreeModel().getTreeType() == null) {
                editingIcon = ICON_MAGIC;
                editingComponent.setEnabled(false);
            } else if (treeNode.getTreeModel().getTreeType() == 0) {
                editingIcon = ICON_FOLDER;
                editingComponent.setEnabled(true);
            } else {
                editingIcon = ICON_FILE;
                editingComponent.setEnabled(true);
            }
            DefaultTextField editor = (DefaultTextField) editingComponent;
            editor.setText(treeNode.getTreeModel().getTreeTitle());
        }

        return editingContainer;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        super.valueChanged(e);
    }

    @Override
    public void editingStopped(ChangeEvent e) {
//        if (!treeNode.getTreeModel().equals((String)getCellEditorValue())) {
//            treeNode.getTreeModel().setTreeTitle((String) getCellEditorValue());
//        }
        editingCanceled(e);
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
        if (!treeNode.getTreeModel().equals((String)getCellEditorValue())) {
            treeNode.getTreeModel().setTreeTitle((String) getCellEditorValue());
            boolean item = treeNode.getTreeModel().getTreeType() == 1;
            titleUpdateCallback.updateNodeTitle(treeNode.getTreeModel().getTreeID(),
                    treeNode.getTreeModel().getTreeTitle(),
                    item);
        }
    }

    private TitleUpdateCallback titleUpdateCallback;

    public TitleUpdateCallback getTitleUpdateCallback() {
        return titleUpdateCallback;
    }

    public void setTitleUpdateCallback(TitleUpdateCallback titleUpdateCallback) {
        this.titleUpdateCallback = titleUpdateCallback;
    }

    public interface TitleUpdateCallback {
        void updateNodeTitle(String id, String title, boolean item);
    }
}
