package control;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;
import ui.ToolWindowUI;

/**
 * Created by wuwenchuan on 2017/5/12.
 */
public class ToolWindowConfigure implements ToolWindowFactory {
    private ToolWindowUI toolWindowUI = null;
    private SettingControl gSettingControl = null;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (toolWindowUI == null) {
            toolWindowUI = new ToolWindowUI();
        }
        if (gSettingControl == null) {
            gSettingControl = SettingControl.getSettingControl();
            gSettingControl.setToolWindowUI(toolWindowUI);
        }
        initDataFromPersistent();
        //注册ToolWindow
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(toolWindowUI.mRootContent, null, false);
        contentManager.addContent(content);
        Disposer.register(project, content);
    }

    private void initDataFromPersistent() {
        toolWindowUI.setmAutoLayout(gSettingControl.getAutoLayout());
    }
}
