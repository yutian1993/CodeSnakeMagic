package ui.treeui;

import service.ItemService;
import service.TreeService;
import service.impl.ItemServiceImpl;
import service.impl.TreeServiceImpl;
import service.model.TreeModel;
import util.ConfigureValues;
import util.DBUtil;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Created by wuwenchuan on 2017/5/24.
 */
public class CodeTree extends JTree implements TreeNoteEditor.TitleUpdateCallback {
    //数据库服务
    private ItemService itemService;
    private TreeService treeService;

    //根目录
    protected DefaultTreeModel mTreeModel;
    protected TreeNode mRootNode;

    protected JPopupMenu mPopupMenu;
    protected Action mAction;
    protected TreePath mClickedPath;

    private Action mDeleteAction;
    private Action mRenameAction;
    private Action mAddFile;
    private Action mAddFolder;

    //回调事件
    private EditCoreCallback mEditCoreCallback;

    public CodeTree() {
        super();
        initService();
        putClientProperty("JTree.lineStyle", "Angled");
        setShowsRootHandles(true);
        initData();
        this.setEditable(true);
        addMouseListener(new PopupTrigger());
        addTreeExpansionListener(new FolderExpansionListener());
        addTreeSelectionListener(new ItemSelectItemListener());
        initPopupMenu();
        initTreeEditor();
    }

    private void initService() {
        itemService = new ItemServiceImpl();
        treeService = new TreeServiceImpl();
    }

    protected void initData() {
        mRootNode = new TreeNode(new TreeModel(null, "CodeMagic"));
        mRootNode.setAllowsChildren(true);
        mRootNode.setHasExpand(true);

        TreeModel temp = new TreeModel("12345678", "Retrieving data...");
        temp.setTreeType(3);
        TreeNode node = new TreeNode(temp);
        node.setAllowsChildren(false);
        node.setHasExpand(false);
        mRootNode.add(node);

        mTreeModel = new DefaultTreeModel(mRootNode);
        setModel(mTreeModel);
        setCellRenderer(new TreeNoteRender());
        collapseRow(0);
    }

    protected void initPopupMenu() {
        mPopupMenu = new JPopupMenu();

        //展开节点
        mAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mClickedPath == null)
                    return;
                if (isExpanded(mClickedPath))
                    collapsePath(mClickedPath);
                else
                    expandPath(mClickedPath);
            }
        };
        mPopupMenu.add(mAction);
        mPopupMenu.addSeparator();

        //添加一个文件节点
        mAddFile = new AbstractAction("New Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeNode treeNode = (TreeNode) getTreeNode(mClickedPath);
                if (treeNode == null)
                    return;

                TreeNode parentNode =  getCurrentPID(treeNode);
                createNewNote(parentNode, "New File", 1);

            }
        };
        mPopupMenu.add(mAddFile);

        //添加一个文件夹节点
        mAddFolder = new AbstractAction("New Folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeNode treeNode = (TreeNode) getTreeNode(mClickedPath);
                if (treeNode == null)
                    return;

                TreeNode parentNode =  getCurrentPID(treeNode);
                createNewNote(parentNode, "New Folder", 0);
            }
        };
        mPopupMenu.add(mAddFolder);

        //删除节点
        mDeleteAction = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mClickedPath == null)
                    return;

                TreeNode treeNode = (TreeNode) getTreeNode(mClickedPath);
                if (treeNode != null && treeNode.getParent() != null) {
                    if (mEditCoreCallback != null)
                        mEditCoreCallback.updateItemContent(treeNode.getTreeModel().getTreeID());
                    mTreeModel.removeNodeFromParent(treeNode);
                    treeService.deleteTreeInfo(treeNode.getTreeModel().getTreeID());
                }
            }
        };
        mPopupMenu.add(mDeleteAction);

        //重命名节点
        mRenameAction = new AbstractAction("Rename") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mClickedPath == null)
                    return;

                //编辑选中的节点
                startEditingAtPath(mClickedPath);
            }
        };
        mPopupMenu.add(mRenameAction);
    }

    @Override
    public void startEditingAtPath(TreePath path) {
        if (path.getParentPath() != null)       //根节点不允许重命名
            super.startEditingAtPath(path);
    }

    /**
     * 获取指定节点的父节点
     * @param treeNode 指定节点
     * @return 父节点
     */
    public TreeNode getCurrentPID(TreeNode treeNode) {
        TreeNode parentNode = treeNode;
        //确定根节点
        if (treeNode.getParent() == null) {
            parentNode = mRootNode;
        } else if (treeNode.getTreeModel().getTreeType() == 0) {
            parentNode = treeNode;
        } else {
            parentNode = ((TreeNode)(treeNode.getParent()));
        }
        return parentNode;
    }

    /**
     * 在指定的父节点处插入一条新的数据
     * @param parentNode  父节点
     * @param title 标题
     * @param type 类型（0：文件夹 1：文件）
     * @return 是否成功插入
     */
    public boolean createNewNote(TreeNode parentNode, String title,  int type) {
        String newID = treeService.insertNewTreeInfo(title, type,
                parentNode.getTreeModel().getTreeID());
        TreeModel treeModel = new TreeModel(newID, title);
        treeModel.setTreeType(type);
        TreeNode node = new TreeNode(treeModel);
        node.setHasExpand(true);
        if (type == 0) {
            if (node.getTreeModel().getTreeType() == 0) {
                TreeModel temp = new TreeModel("12345678", "Retrieving data...");
                temp.setTreeType(3);
                TreeNode childNode = new TreeNode(temp);
                childNode.setHasExpand(false);
                node.add(childNode);
            }
        }

        parentNode.add(node);

        javax.swing.tree.TreeNode[] nodes = mTreeModel.getPathToRoot(node);
        TreePath path = new TreePath(nodes);
        super.scrollPathToVisible(path);
        super.updateUI();
        startEditingAtPath(path);
        return true;
    }

    public EditCoreCallback getmEditCoreCallback() {
        return mEditCoreCallback;
    }

    public void setmEditCoreCallback(EditCoreCallback mEditCoreCallback) {
        this.mEditCoreCallback = mEditCoreCallback;
    }

    /**
     * @param path tree path
     * @return
     */
    private DefaultMutableTreeNode getTreeNode(TreePath path) {
        return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }

    /**
     * 初始化Tree Editor
     */
    private void initTreeEditor() {
        TreeNoteEditor treeNoteEditor = new TreeNoteEditor(this, new TreeEditorRender());
        treeNoteEditor.setTitleUpdateCallback(this);
        this.setCellEditor(treeNoteEditor);
    }

    @Override
    public void updateNodeTitle(String id, String title, boolean item) {
        treeService.updateNewTreeInfo(id, title);
        if (item)
            itemService.updateItemTitle(id, title);
    }

    /**
     * 展开节点
     * @param treeNode 可扩展节点
     * @return 是否成功展开节点
     */
    private boolean expandTreeNode(TreeNode treeNode) {
        TreeNode flag =
                (TreeNode)treeNode.getFirstChild();
        if (flag==null)       // No flag
            return false;
        if (flag.hasExpand())
            return false;      // Already expanded


        //获取数据
        Map<String, TreeModel> treeModelMap =
                treeService.getTreeInfo(treeNode.getTreeModel().getTreeID());
        treeNode.removeAllChildren();
        for ( String key : treeModelMap.keySet()) {
            TreeNode newNode = new TreeNode(treeModelMap.get(key));
            if (newNode.getTreeModel().getTreeType() == 0) {
                TreeModel treeModel = new TreeModel("12345678", "Retrieving data...");
                treeModel.setTreeType(3);
                TreeNode childNode = new TreeNode(treeModel);
                childNode.setHasExpand(false);
                newNode.add(childNode);
            }
            newNode.setHasExpand(true);
            treeNode.add(newNode);
        }
        treeNode.setHasExpand(true);
        return true;
    }

    /**
     * 右击鼠标响应事件
     */
    class PopupTrigger extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = getPathForLocation(x, y);
                if (path != null) {
                    if (isExpanded(path))
                        mAction.putValue(Action.NAME, "Collapse");
                    else
                        mAction.putValue(Action.NAME, "Expand");
                    mPopupMenu.show(CodeTree.this, x, y);
                    mClickedPath = path;
                }
            }
        }
    }

    /**
     * 展开目录
     */
    class FolderExpansionListener implements TreeExpansionListener {

        @Override
        public void treeExpanded(TreeExpansionEvent event) {
            final DefaultMutableTreeNode node = getTreeNode(
                    event.getPath());

            if (node instanceof TreeNode) {
                TreeNode treeNode = (TreeNode)node;
                //展开Folder
                if (treeNode.getTreeModel().getTreeType() == null ||
                        (treeNode.getTreeModel().getTreeType() != null && treeNode.getTreeModel().getTreeType() == 0)) {
                    Thread runner = new Thread()
                    {
                        public void run()
                        {
                            if (treeNode != null && expandTreeNode(treeNode))
                            {
                                Runnable runnable = new Runnable()
                                {
                                    public void run()
                                    {
                                        mTreeModel.reload(node);
                                    }
                                };
                                SwingUtilities.invokeLater(runnable);
                            }
                        }
                    };
                    runner.start();
                }
            }
        }

        @Override
        public void treeCollapsed(TreeExpansionEvent event) {
        }
    }

    /**
     * Tree Item选择事件
     */
    class ItemSelectItemListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            final DefaultMutableTreeNode node = getTreeNode(
                    e.getPath());

            if (node instanceof TreeNode) {
                TreeNode treeNode = (TreeNode)node;
                TreeModel treeModel = treeNode.getTreeModel();

                if (treeModel != null && treeModel.getTreeID() != null
                        && treeModel.getTreeType() == 1) {
                    if (mEditCoreCallback!=null)
                        mEditCoreCallback.updateItemContent(treeModel.getTreeID());
                }
            }
        }
    }

    /**
     * 通知Edit Core更新内容
     */
    public interface EditCoreCallback {
        void updateItemContent(String itemid);
        void deleteItem(String itemid);
    }

}
