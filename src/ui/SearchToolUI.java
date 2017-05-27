package ui;

import util.LogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by wuwenchuan on 2017/5/13.
 */
public class SearchToolUI implements SearchTextField.SearchTextFieldEvent, SearchSetUI.SearchConfigureTrigger {
    private JTextField mBeginSearch;
    private JLabel mSearch;
    private JLabel mGoSearch;
    public JPanel mRootContent;

    private ImageIcon mSearchOpIcon;
    private ImageIcon mSearchIcon;
    private ImageIcon mGoSearchIcon;
    private ImageIcon mGoIcon;

//  private JLayeredPane mConfigureLayere;
    private Boolean[] mSupportSearch;

    private SearchBarEvent searchBarEvent;

    public SearchToolUI() {
        init();
        mSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mSearch.setIcon(mSearchIcon);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mSearch.setIcon(mSearchOpIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                mConfigureLayere.setVisible(true);
                SearchSetUI setconfigure = new SearchSetUI();
                setconfigure.setSize(new Dimension(238,200));
                Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
                setconfigure.setLocation((int)screensize.getWidth()/2 - 129, (int)screensize.getHeight()/2 - 100);
                setconfigure.setSearchConfigureTigger(SearchToolUI.this);
                setconfigure.setCheckStatus(mSupportSearch);
                setconfigure.setVisible(true);
            }
        });
        mGoSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mGoSearch.setIcon(mGoSearchIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mGoSearch.setIcon(mGoIcon);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                enterEvent();
            }
        });
    }

    public void init() {
        //资源配置
        mSearchOpIcon = new ImageIcon(this.getClass().getResource("../img/find_operator.png"));
        mSearchIcon = new ImageIcon(this.getClass().getResource("../img/find.png"));

        mGoSearchIcon = new ImageIcon(this.getClass().getResource("../img/arror_go_right.png"));
        mGoIcon = new ImageIcon(this.getClass().getResource("../img/arrow_right.png"));

        //搜素配置
//        mConfigureLayere = JLayeredPane.getLayeredPaneAbove()
//        mConfigureLayere.add(new SearchSetUI());
//        mConfigureLayere.setVisible(false);
    }

    private void createUIComponents() {
        //设置搜素栏
        mBeginSearch = new SearchTextField();
        mBeginSearch.setBorder(null);
        mBeginSearch.setOpaque(false);
        ((SearchTextField)mBeginSearch).setEventCallBack(this);
        mBeginSearch.setMinimumSize(new Dimension(0, 30));
    }

    /**
     * 触发搜素
     */
    @Override
    public void enterEvent() {
        //收集搜素的条件
        searchBarEvent.triggerSearch(mBeginSearch.getText());
    }

    public void setSearchBarEvent(SearchBarEvent searchBarEvent) {
        this.searchBarEvent = searchBarEvent;
    }

    @Override
    public void updateSearchConfig(Boolean searchall, Boolean searchtitle, Boolean searchcontent, Boolean searchtag, Boolean searchcomment) {
        searchBarEvent.updateSearchConfig(searchall, searchtitle, searchcontent , searchtag, searchcomment);
    }

    /**
     * 操作接口类
     */
    public interface SearchBarEvent {
        public void triggerSearch(String content);
        public void updateSearchConfig(Boolean searchall, Boolean searchtitle,
                                       Boolean searchcontent, Boolean searchtag,
                                       Boolean searchcomment);
    }

    public void setSupportSearch(Boolean[] supportSearch) {
        if (mSupportSearch != null)              //让GC可回收
            mSupportSearch = null;
        this.mSupportSearch = supportSearch;
    }

    public void enable() {
        mBeginSearch.setEnabled(true);
        mGoSearch.setEnabled(true);
    }

    public void disable() {
        mBeginSearch.setEnabled(false);
        mGoSearch.setEnabled(false);
    }
}
