import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

/**
 * Created by wuwenchuan on 2017/5/10.
 */
public class MainWindowAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Application manager = ApplicationManager.getApplication();
        CodeAppComponent component = manager.getComponent(CodeAppComponent.class);
        component.testFunction();
    }
}
