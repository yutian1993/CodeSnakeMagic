package ui;

import com.intellij.openapi.ui.Messages;
import util.ConfigureValues;
import util.DBUtil;
import util.LogUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by wuwenchuan on 2017/5/12.
 */
public class SettingConfigureUI {
    public JTextField mDatabasePath;
    public JButton mViewFiles;
    public JComboBox mPageList;
    public JPanel mRootContent;
    public JRadioButton mAutoLayout;
    private JLabel mAutoLayoutIcon;
    private JComboBox comboBox1;
    private JButton syncCodeButton;
    private JPanel mSearchTools;

    private ImageIcon mEnableAutoRotate;
    private ImageIcon mDisableAutoRotate;

    public SettingConfigureUI() {
        init();
        mViewFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showDialog(new JLabel(), ConfigureValues.G_FILESEL);
                File file=jfc.getSelectedFile();
                if ( file != null && file.isFile()) {
                    if (DBUtil.isSqliteDB(file.getAbsolutePath())) {
                        mDatabasePath.setText(file.getAbsolutePath());
                    } else {
                        Messages.showInfoMessage(ConfigureValues.G_SELECTWARNING, ConfigureValues.G_SELECTERROR);
                    }
                }
            }
        });
        mAutoLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLayoutImg();
            }
        });
        syncCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.showInfoMessage(ConfigureValues.D_FUNCTIONDEP, ConfigureValues.G_SELECTERROR);
            }
        });
    }

    public void init() {
        mEnableAutoRotate = new ImageIcon(this.getClass().getResource("../img/autorotate.png"));
        mDisableAutoRotate = new ImageIcon(this.getClass().getResource("../img/autorotate_disable.png"));
    }

    public void updateLayoutImg() {
        if (mAutoLayout.isSelected()) {
            mAutoLayoutIcon.setIcon(mEnableAutoRotate);
        } else {
            mAutoLayoutIcon.setIcon(mDisableAutoRotate);
        }
    }

    private void createUIComponents() {
        SearchToolUI temp = new SearchToolUI();
        mSearchTools = temp.mRootContent;
    }
}
