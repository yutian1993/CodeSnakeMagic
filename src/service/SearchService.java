package service;

import service.model.TreeModel;

import java.util.List;
import java.util.Map;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
public interface SearchService {

    /**
     * 搜素的配置信息是否发生变更，并同时更新到存储信息中
     * @return 是否发生变更
     */
    boolean isSearchConfigureModify(Boolean searchall, Boolean searchtitle,
                                           Boolean searchcontent, Boolean searchtag,
                                           Boolean searchcomment);

    /**
     * 更新搜素配置
     * @param searchall
     * @param searchtitle
     * @param searchcontent
     * @param searchtag
     * @param searchcomment
     */
    void updateSearchConfigure(Boolean searchall, Boolean searchtitle,
                               Boolean searchcontent, Boolean searchtag,
                               Boolean searchcomment);

    /**
     * 获取系统目前设置的属性
     */
    Boolean getSupportSearchAll();
    Boolean getSupportSearchTag();
    Boolean getSupportSearchTitle();
    Boolean getSupportSearchContent();
    Boolean getSupportSearchComment();

    /**
     * 直接获取配置属性数组
     * @return 返回值的顺序依次是{All, Title, Content, Tag, Comment}
     */
    Boolean[] getAllSupportSearch();

    /**
     * 自动根据设置搜素文件内容
     * @param conent 搜素的内容
     * @return
     */
    Map<String, TreeModel> search(String conent);

    /**
     * 自动根据设置搜素文件内容
     * @param conent 搜素的内容
     * @return
     */
    List<TreeModel> searchList(String conent);

    /**
     * 根据title搜索内容
     * @param title title
     * @return
     */
    Map<String, TreeModel> searchTitle(String title);

    /**
     * 根据title搜索内容
     * @param title title
     * @return
     */
    List<TreeModel> searchTitleList(String title);

    /**
     * 搜索tag内容
     * @param tags tag
     * @return
     */
    Map<String, TreeModel> searchTags(String tags);

    /**
     * 搜索tag内容
     * @param tags tag
     * @return
     */
    List<TreeModel> searchTagsList(String tags);

    /**
     * 搜索content内容
     * @param conents content
     * @return
     */
    Map<String, TreeModel> searchContents(String conents);

    /**
     * 搜索content内容
     * @param conents content
     * @return
     */
    List<TreeModel> searchContentsList(String conents);

}
