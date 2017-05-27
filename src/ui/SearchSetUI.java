package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchSetUI extends JDialog {
    private JPanel contentPane;
    private JRadioButton searchAllRadioButton;
    private JCheckBox searchTagsCheckBox;
    private JCheckBox searchContentCheckBox;
    private JCheckBox searchCommentCheckBox;
    private JCheckBox searchTitleCheckBox;
    private JButton mSure;
    private JButton mCancel;

    private SearchConfigureTrigger mSearchConfigureTigger;

    public SearchSetUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(mSure);
        initUI();
        searchAllRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initUI();
            }
        });
        mSure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchAllRadioButton.isSelected()) {
                    if (mSearchConfigureTigger != null)
                        mSearchConfigureTigger.updateSearchConfig(true, null,
                                null, null, null);
                } else {
                    if (mSearchConfigureTigger != null)
                        mSearchConfigureTigger.updateSearchConfig(false, searchTitleCheckBox.isSelected(),
                                searchContentCheckBox.isSelected(), searchTagsCheckBox.isSelected(),
                                searchCommentCheckBox.isSelected());
                }
                dispose();
            }
        });
        mCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void initUI() {
        if (searchAllRadioButton.isSelected()) {
            groupEnable(false);
        } else {
            groupEnable(true);
        }
    }

    protected void groupEnable(boolean enable) {
        searchCommentCheckBox.setEnabled(false);       //暂时还不支持
        searchContentCheckBox.setEnabled(enable);
        searchTagsCheckBox.setEnabled(enable);
        searchTitleCheckBox.setEnabled(enable);
    }

    public void setSearchConfigureTigger(SearchConfigureTrigger searchConfigureTigger) {
        this.mSearchConfigureTigger = searchConfigureTigger;
    }

    public interface SearchConfigureTrigger {
        public void updateSearchConfig(Boolean searchall, Boolean searchtitle,
                                       Boolean searchcontent, Boolean searchtag,
                                       Boolean searchcomment);
    }

    /**
     * 传入的参数只接受下面的顺序
     *
     * @param searchconfig {All, Title, Content, Tag, Comment}
     */
    public void setCheckStatus(Boolean[] searchconfig) {
        if (searchconfig.length == 5) {
            searchAllRadioButton.setSelected(searchconfig[0]);
            searchTitleCheckBox.setSelected(searchconfig[1]);
            searchContentCheckBox.setSelected(searchconfig[2]);
            searchTagsCheckBox.setSelected(searchconfig[3]);
            searchCommentCheckBox.setSelected(searchconfig[4]);
            initUI();
        }
    }
}
