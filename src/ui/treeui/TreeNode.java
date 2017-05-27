package ui.treeui;

import service.model.TreeModel;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by wuwenchuan on 2017/5/23.
 */
public class TreeNode extends DefaultMutableTreeNode {

    private boolean isRoot;
    private TreeModel treeModel;
    private boolean hasExpand;

    public TreeNode(TreeModel treeModel) {
        this.treeModel = treeModel;
        if (treeModel.getTreeID() == null)
            isRoot = true;
        hasExpand = false;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }


    public boolean hasExpand() {
        return hasExpand;
    }

    public void setHasExpand(boolean hasExpand) {
        this.hasExpand = hasExpand;
    }
}
