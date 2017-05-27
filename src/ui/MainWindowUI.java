package ui;

import com.hexidec.ekit.EkitCore;
import service.ItemService;
import service.impl.ItemServiceImpl;
import ui.treeui.CodeTree;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * Created by wuwenchuan on 2017/5/23.
 */
public class MainWindowUI implements EkitCore.SaveDataCallBack, CodeTree.EditCoreCallback {
    private JPanel panel1;
    private JSplitPane mainSplitSpane;
    private JPanel mRightContent;
    private JPanel showtoolbox;
    private JPanel showeditor;
    private JTree treemanager;
    private EkitCore ekitCore;

    //窗口MainFrame
    JFrame jFrame;

    //数据库Service
    private ItemService itemService;

    public MainWindowUI() {
    }

    public void setupFrame() {
        if (jFrame == null) {
            jFrame = new JFrame("CodeSnakeMagic");
            jFrame.setContentPane(this.panel1);
            jFrame.pack();
//            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setSize(800, 800);
            jFrame.setVisible(true);
            jFrame.setJMenuBar(getEditorMenuBar());
            jFrame.setIconImage(new ImageIcon(this.getClass().getResource("../img/mainicon.png")).getImage());
        } else {
            jFrame.setVisible(true);
        }
    }

    private void createUIComponents() {
        //对编辑控件进行管理
        showtoolbox = new JPanel();
        showeditor = new JPanel();
        edtorInit();

        treemanager = new CodeTree();
        CodeTree codeTree = (CodeTree)treemanager;
        ((CodeTree) treemanager).setmEditCoreCallback(this);

        itemService = new ItemServiceImpl();
    }

    public JMenuBar getEditorMenuBar() {
        return ekitCore.getMenuBar();
    }

    public void edtorInit() {
        //默认控件设置
        String sDocument = null;
        String sStyleSheet = null;
        String sRawDocument = null;
        URL urlStyleSheet = null;
        boolean includeToolBar = true;
        boolean multibar = true;
        boolean includeViewSource = false;
        boolean includeMenuIcons = true;
        boolean modeExclusive = true;
        String sLang = null;
        String sCtry = null;
        boolean base64 = false;
        boolean debugOn = false;
        boolean spellCheck = false;
        boolean enterBreak = false;

        //初始化编辑控件
        ekitCore = new EkitCore(false, sDocument, sStyleSheet, sRawDocument, null, urlStyleSheet, includeToolBar,
                includeViewSource, includeMenuIcons, modeExclusive, sLang, sCtry, base64, debugOn, false, multibar,
                (multibar ? EkitCore.TOOLBAR_DEFAULT_MULTI : EkitCore.TOOLBAR_DEFAULT_SINGLE), enterBreak);
        showeditor.setLayout(new GridLayout());
        showeditor.add(ekitCore);
        ekitCore.setSaveDataCallBack(this);
        ekitCore.enableEdit(false);

        //初始化工具栏
        if(includeToolBar) {
            if (multibar) {
                showtoolbox.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 0.0;
                gbc.gridx = 1;

                gbc.gridy = 1;
                showtoolbox.add(ekitCore.getToolBarMain(includeToolBar), gbc);

                gbc.gridy = 2;
                showtoolbox.add(ekitCore.getToolBarFormat(includeToolBar), gbc);

                gbc.gridy = 3;
                showtoolbox.add(ekitCore.getToolBarStyles(includeToolBar), gbc);

                gbc.anchor = GridBagConstraints.SOUTH;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1.0;
                gbc.gridy = 4;
            }
        }
    }

    //保存文件内容
    @Override
    public void saveItemContent(Document document, String itemid) {
        HTMLDocument htmlDocument = (HTMLDocument)document;
        try {
            System.out.println(itemid + "=============" + htmlDocument.getText(0, htmlDocument.getLength()));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveItemContent(String content, String itemid) {
        if (itemid == null) {
            //新建一个record到根目录
        } else {
            ekitCore.setHasChange(false);
            ekitCore.updateSaveBtnIcon("SaveOK");
            itemService.updateItemContent(itemid, content.getBytes());
        }
    }

    //更新文件内容
    @Override
    public void updateItemContent(String itemid) {
        byte[] content = itemService.getItemInfoContent(itemid);
        ekitCore.setItemid(itemid);
        ekitCore.updateSaveBtnIcon("Save");
        ekitCore.setHasChange(false);
        ekitCore.enableEdit(true);
        if (content != null && content.length > 0) {
            ekitCore.setDocumentText(new String(content));
        } else {
            ekitCore.setDocumentText("");
        }
    }

    @Override
    public void deleteItem(String itemid) {
        if (ekitCore.getItemid().equals(itemid)) {
            ekitCore.updateSaveBtnIcon("Save");
            ekitCore.setHasChange(false);
            ekitCore.enableEdit(false);
            ekitCore.setDocumentText("");
        }
    }
}
