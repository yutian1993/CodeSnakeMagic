package control;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;
import ui.ToolWindowUI;
import util.ConfigureValues;

@State(
        name = "CodeSnake",
        storages = {@Storage(
                id = "other",
                file = "$APP_CONFIG$/CodeSnake.xml"
        )}
)

/**
 * Created by wuwenchuan on 2017/5/9.
 */
public class SettingControl implements PersistentStateComponent<Element> {

    //Setting设置
    private String databasePath;
    private String pageItems;

    //Toolbar设置
    private Boolean autoLayout;

    //ToolMainWindow对象引用
    private ToolWindowUI toolWindowUI;

    //设置搜素项
    private Boolean searchAll;
    private Boolean searchTitle;
    private Boolean searchContent;
    private Boolean searchTags;
    private Boolean searchComments;

    public SettingControl() {
    }

    /**
     * 获取全局代码环境配置
     * @return
     */
    public static SettingControl getSettingControl() {
        return (SettingControl) ServiceManager.getService(SettingControl.class);
    }

    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("CodeSnake");
        //数据库位置
        element.setAttribute(ConfigureValues.G_DATAPATH, databasePath);
        //搜素界面设置
        element.setAttribute(ConfigureValues.G_LISTITEMS, pageItems);
        element.setAttribute(ConfigureValues.G_AUTOLAYOUT, String.valueOf(autoLayout));
        //搜素界面配置
        element.setAttribute(ConfigureValues.G_SEARCHALL, String.valueOf(searchAll));
        element.setAttribute(ConfigureValues.G_SEARCHTITLE, String.valueOf(searchTitle));
        element.setAttribute(ConfigureValues.G_SEARCHCONTENT, String.valueOf(searchContent));
        element.setAttribute(ConfigureValues.G_SEARCHTAG, String.valueOf(searchTags));
        element.setAttribute(ConfigureValues.G_SEARCHCOMMENT, String.valueOf(searchComments));

        return element;
    }

    @Override
    public void loadState(Element element) {
        this.databasePath = element.getAttributeValue(ConfigureValues.G_DATAPATH);
        if (databasePath == null) databasePath = System.getProperty("user.dir") + ConfigureValues.G_DBNAME;

        this.pageItems = element.getAttributeValue(ConfigureValues.G_LISTITEMS);
        if (pageItems == null) pageItems = ConfigureValues.G_PAGEITEMS;
        this.autoLayout = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_AUTOLAYOUT));
        if (autoLayout == null) autoLayout = true;

        this.searchAll = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_SEARCHALL));
        if (searchAll == null) autoLayout = true;
        this.searchTitle = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_SEARCHTITLE));
        if (searchTitle == null) searchTitle = false;
        this.searchContent = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_SEARCHCONTENT));
        if (searchContent == null) searchContent = false;
        this.searchTags = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_SEARCHTAG));
        if (searchTags == null) searchTags = false;
        this.searchComments = Boolean.valueOf(element.getAttributeValue(ConfigureValues.G_SEARCHCOMMENT));
        if (searchComments == null) searchComments = false;
    }

    public String getDatabasePath() {
        return databasePath == null ? System.getProperty("user.dir") + ConfigureValues.G_DBNAME : databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getPageItems() {
        return pageItems == null ? ConfigureValues.G_PAGEITEMS : pageItems;
    }

    public void setPageItems(String pageItems) {
        this.pageItems = pageItems;
    }

    public Boolean getAutoLayout() {
        return autoLayout;
    }

    public void setAutoLayout(Boolean autoLayout) {
        this.autoLayout = autoLayout;

        //更新MainWindow布局显示
        toolWindowUI.setmAutoLayout(autoLayout);
    }

    public ToolWindowUI getToolWindowUI() {
        return toolWindowUI;
    }

    public void setToolWindowUI(ToolWindowUI toolWindowUI) {
        this.toolWindowUI = toolWindowUI;
    }

    public Boolean getSearchAll() {
        return searchAll;
    }

    public void setSearchAll(Boolean searchAll) {
        this.searchAll = searchAll;
    }

    public Boolean getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(Boolean searchTitle) {
        this.searchTitle = searchTitle;
    }

    public Boolean getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(Boolean searchContent) {
        this.searchContent = searchContent;
    }

    public Boolean getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(Boolean searchTags) {
        this.searchTags = searchTags;
    }

    public Boolean getSearchComments() {
        return searchComments;
    }

    public void setSearchComments(Boolean searchComments) {
        this.searchComments = searchComments;
    }
}
