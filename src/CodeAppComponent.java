import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ui.Messages;
import control.SettingControl;
import org.jetbrains.annotations.NotNull;
import ui.MainWindowUI;

/**
 * Created by wuwenchuan on 2017/5/10.
 */
public class CodeAppComponent implements ApplicationComponent {

    MainWindowUI mainWindowUI;

    public CodeAppComponent() {
        mainWindowUI = new MainWindowUI();
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "CodeAppComponent";
    }

    //Function test
    public void testFunction() {
        mainWindowUI.setupFrame();
    }
}
