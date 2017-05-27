package control;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ui.SettingConfigureUI;

import javax.swing.*;

/**
 * Created by wuwenchuan on 2017/5/9.
 */
public class SettingConfigure implements SearchableConfigurable {
    private SettingControl gSettingControl = SettingControl.getSettingControl();

    private static String G_NAME = "CodeSnake";
    private SettingConfigureUI settingUI = null;

    @NotNull
    @Override
    public String getId() {
        return G_NAME;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return G_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return G_NAME;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingUI == null) {
            settingUI = new SettingConfigureUI();
        }
        return settingUI.mRootContent;
    }

    @Override
    public boolean isModified() {
        boolean result = !settingUI.mDatabasePath.getText().equals(gSettingControl.getDatabasePath());
        result = result || !gSettingControl.getPageItems().equals((String) settingUI.mPageList.getSelectedItem());
        result = result || !(settingUI.mAutoLayout.isSelected() == gSettingControl.getAutoLayout());
        return result;
    }

    @Override
    public void apply() throws ConfigurationException {
        gSettingControl.setDatabasePath(settingUI.mDatabasePath.getText());
        gSettingControl.setPageItems((String) settingUI.mPageList.getSelectedItem());
        gSettingControl.setAutoLayout(settingUI.mAutoLayout.isSelected());
    }

    @Override
    public void reset() {
        settingUI.mDatabasePath.setText(gSettingControl.getDatabasePath());
        settingUI.mPageList.setSelectedItem(gSettingControl.getPageItems());
        settingUI.mAutoLayout.setSelected(gSettingControl.getAutoLayout());
        //重新更新图片
        settingUI.updateLayoutImg();
    }
}
