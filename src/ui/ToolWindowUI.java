package ui;

import service.CodeMagicServiceManager;
import service.ItemService;
import service.SearchService;
import service.impl.ItemServiceImpl;
import service.model.ItemModel;
import service.model.TreeModel;
import util.ConfigureValues;
import util.LogUtil;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wuwenchuan on 2017/5/12.
 */
public class ToolWindowUI implements SearchToolUI.SearchBarEvent,
        SearchList.ListItemChange, ItemInfoThread.ItemCallBack, SearchItemThread.SearchCallBack {
    public JPanel mRootContent;
    private JSplitPane mSplitContent;
    private JTextPane mSearchItemShow;
    private JPanel mLeftTools;
    private SearchToolUI mSearchUI;
    private JPanel mSearchBar;
    private JScrollPane mResultList;
    private JList mSearchList;

    private boolean mAutoLayout;

    //搜索相关变量初始化
    private SearchService gSearchService;
    private ExecutorService executorService;
    private ExecutorService searchExeService;
    private ItemService itemService;
    private Map<String, byte[]> searchList;
    private String currentSearch;                  //当前用户的搜素项
    private int currentIndex;                      //当前用户的选择项

    public ToolWindowUI() {

        gSearchService = CodeMagicServiceManager.getSearchService();
        itemService = CodeMagicServiceManager.getItemService();
        searchList = new HashMap<>();
        executorService = Executors.newSingleThreadExecutor();
        searchExeService = Executors.newSingleThreadExecutor();

        //设置搜素显示
        mRootContent.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                //动态修改显示布局
                if (mAutoLayout) {
                    if (mRootContent.getWidth() > 520) {
                        if (mSplitContent.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
                            mSplitContent.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
                            mSplitContent.setDividerLocation(0.3);
                            mSplitContent.setResizeWeight(0.3);
                        }
                    } else {
                        if (mSplitContent.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                            mSplitContent.setOrientation(JSplitPane.VERTICAL_SPLIT);
                            mSplitContent.setDividerLocation(0.6);
                            mSplitContent.setResizeWeight(0.6);
                        }
                    }
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }


    public void setmAutoLayout(boolean mAutoLayout) {
        this.mAutoLayout = mAutoLayout;
    }

    private void createUIComponents() {
        //Content显示设置
        HTMLEditorKit editorKit = new HTMLEditorKit();
        HTMLDocument document = (HTMLDocument) editorKit.createDefaultDocument();
        mSearchItemShow = new JTextPane(document);
        mSearchItemShow.setContentType("text/html");

        //添加搜素ToolBar
        mSearchUI = new SearchToolUI();
        mSearchBar = mSearchUI.mRootContent;

        //初始化变量
        if (gSearchService == null) {
            gSearchService = CodeMagicServiceManager.getSearchService();
        }
        if (gSearchService == null) {
            LogUtil.error("This should be happer!");
        } else {
            mSearchUI.setSupportSearch(gSearchService.getAllSupportSearch());
        }
        mSearchBar.setMinimumSize(new Dimension(220, 0));

        //需要回调搜素事件方法
        mSearchUI.setSearchBarEvent(this);

        //初始化List
        mSearchList = new SearchList();
        SearchList temp = (SearchList) mSearchList;
        temp.setListItemChange(this);
    }

    @Override
    public void updateSearchConfig(Boolean searchall, Boolean searchtitle, Boolean searchcontent, Boolean searchtag, Boolean searchcomment) {
        if (gSearchService.isSearchConfigureModify(searchall, searchtitle, searchcontent, searchtag, searchcomment)) {
            mSearchUI.setSupportSearch(gSearchService.getAllSupportSearch());
        } else {
            LogUtil.error("No modify");
        }
    }


    //触发搜素
    @Override
    public void triggerSearch(String content) {
        if (content.equals(currentSearch))                           //搜索内容没有改变，取消搜索
            return;

        mSearchUI.disable();
        SearchItemThread searchItemThread = new SearchItemThread("NEWThread", content);
        searchItemThread.setSearchService(gSearchService);
        searchItemThread.setSearchCallBack(this);
        searchExeService.execute(searchItemThread);
    }


    //触发Item信息更新
    @Override
    public void changeItemID(int index, String id) {
        currentIndex = index;
        SearchList temp = (SearchList) mSearchList;
        byte[] infor = temp.reuestModelItemContent(index);
        if (infor == null) {
            LogUtil.error("Get infor from service");
            ItemInfoThread newThread = new ItemInfoThread(id, id, index, this);
            newThread.setSearchService(itemService);
            executorService.execute(newThread);
        } else {
            LogUtil.error("Get info from item");
            mSearchItemShow.setText(new String(infor));
            infor = null;
        }
    }

    /**
     * 根据itemid来更新content和显示的content
     * @param itemid
     * @param index
     * @param content
     */
    @Override
    public void updateItemContent(String itemid, int index, byte[] content) {
        SearchList temp = (SearchList) mSearchList;
        temp.updateModelItemContent(index, content);
        if (currentIndex == index) {
            if (content == null) {
                mSearchItemShow.setText(ConfigureValues.G_NODATA);
            } else {
                mSearchItemShow.setText(new String(content));
            }
        }
        else
            mSearchItemShow.setText("Please wait!");
    }

    @Override
    public void updateItemList(List<TreeModel> treeModelList) {
        mSearchUI.enable();
        currentSearch = null;
        SearchList temp = (SearchList) mSearchList;
        temp.refreshListUI(treeModelList);
    }
}
