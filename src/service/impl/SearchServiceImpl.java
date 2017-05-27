package service.impl;

import control.SettingControl;
import database.dbbean.TreeInfoBean;
import database.dbdao.ItemInfoDao;
import database.dbdao.ItemTagInfoDao;
import database.dbdao.TagInfoDao;
import database.dbdao.TreeInfoDao;
import service.SearchService;
import service.model.TreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
public class SearchServiceImpl implements SearchService {
    private SettingControl gSettingControl = SettingControl.getSettingControl();

    private Boolean searchAll;
    private Boolean searchTags;
    private Boolean searchContent;
    private Boolean searchComment;
    private Boolean searchTitle;

    public SearchServiceImpl() {
        searchAll = gSettingControl.getSearchAll();
        searchTags = gSettingControl.getSearchTags();
        searchContent = gSettingControl.getSearchContent();
        searchComment = gSettingControl.getSearchComments();
        searchTitle = gSettingControl.getSearchTitle();

        treeInfoDao = new TreeInfoDao();
        tagInfoDao = new TagInfoDao();
        itemInfoDao = new ItemInfoDao();
        itemTagInfoDao = new ItemTagInfoDao();
    }

    @Override
    public boolean isSearchConfigureModify(Boolean searchall, Boolean searchtitle, Boolean searchcontent, Boolean searchtag, Boolean searchcomment) {
        boolean result = false;
        if (this.searchAll.equals(searchall) && searchall == true) {
            //Do nothing
        } else {
            this.searchAll = searchall;
            gSettingControl.setSearchAll(searchall);
            result = true;
            if (!searchall) {
                if (!this.searchTitle.equals(searchtitle) && searchtitle != null) {
                    this.searchTitle = searchtitle;
                    gSettingControl.setSearchTitle(searchtitle);
                }
                if (!this.searchContent.equals(searchcontent) && searchcontent != null) {
                    this.searchContent = searchcontent;
                    gSettingControl.setSearchContent(searchcontent && searchcontent != null);
                }
                if (!this.searchTags.equals(searchtag) && searchtag != null) {
                    this.searchTags = searchtag;
                    gSettingControl.setSearchTags(searchtag);
                }
                if (!this.searchComment.equals(searchcomment) && searchcomment != null) {
                    this.searchComment = searchcomment;
                    gSettingControl.setSearchComments(searchcomment);
                }
            }
        }
        return result;
    }

    @Override
    public void updateSearchConfigure(Boolean searchall, Boolean searchtitle, Boolean searchcontent, Boolean searchtag, Boolean searchcomment) {
        if (searchall != null)
            gSettingControl.setSearchAll(searchall);
        if (searchtitle != null)
            gSettingControl.setSearchTitle(searchtitle);
        if (searchcontent != null)
            gSettingControl.setSearchContent(searchcontent);
        if (searchtag != null)
            gSettingControl.setSearchTags(searchtag);
        if (searchcomment != null)
            gSettingControl.setSearchComments(searchcomment);
    }

    @Override
    public Boolean getSupportSearchAll() {
        return searchAll;
    }

    @Override
    public Boolean getSupportSearchTag() {
        return searchTags;
    }

    @Override
    public Boolean getSupportSearchTitle() {
        return searchTitle;
    }

    @Override
    public Boolean getSupportSearchContent() {
        return searchContent;
    }

    @Override
    public Boolean getSupportSearchComment() {
        return searchComment;
    }

    @Override
    public Boolean[] getAllSupportSearch() {
        Boolean[] supportSearch = {searchAll, searchTitle, searchContent, searchTags, searchComment};
        return supportSearch;
    }

    @Override
    public Map<String, TreeModel> search(String conent) {
        return searchTitle(conent);
    }

    @Override
    public List<TreeModel> searchList(String conent) {
        return searchTitleList(conent);
    }

    private TreeInfoDao treeInfoDao;
    private TagInfoDao tagInfoDao;
    private ItemInfoDao itemInfoDao;
    private ItemTagInfoDao itemTagInfoDao;

    @Override
    public Map<String, TreeModel> searchTitle(String title) {
        Map<String, TreeModel> treeModelMap = new TreeMap<>();
        List<TreeInfoBean> treeInfoBeanList = treeInfoDao.searchTitle(title);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelMap.put(treeInfoBean.getId(), new TreeModel(treeInfoBean));
        }
        return treeModelMap;
    }

    @Override
    public List<TreeModel> searchTitleList(String title) {
        List<TreeModel> treeModelList = new ArrayList<>();
        List<TreeInfoBean> treeInfoBeanList = treeInfoDao.searchTitle(title);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelList.add(new TreeModel(treeInfoBean));
        }
        return treeModelList;
    }

    @Override
    public Map<String, TreeModel> searchTags(String tags) {
        Map<String, TreeModel> treeModelMap = new TreeMap<>();

        //匹配TAG项
        String[] matchTagIDs = tagInfoDao.searchContent(tags);
        if (matchTagIDs == null)
            return treeModelMap;

        //匹配ITEM项
        String[] matchItemIDs = itemTagInfoDao.searchContent(matchTagIDs);
        if (matchItemIDs == null)
            return treeModelMap;

        //搜索Item项信息
        List<TreeInfoBean> treeInfoBeanList = itemInfoDao.searchItemIDs(matchItemIDs);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelMap.put(treeInfoBean.getId(), new TreeModel(treeInfoBean));
        }
        return treeModelMap;
    }

    @Override
    public List<TreeModel> searchTagsList(String tags) {
        List<TreeModel> treeModelList = new ArrayList<>();

        //匹配TAG项
        String[] matchTagIDs = tagInfoDao.searchContent(tags);
        if (matchTagIDs == null)
            return treeModelList;

        //匹配ITEM项
        String[] matchItemIDs = itemTagInfoDao.searchContent(matchTagIDs);
        if (matchItemIDs == null)
            return treeModelList;

        //搜索Item项信息
        List<TreeInfoBean> treeInfoBeanList = itemInfoDao.searchItemIDs(matchItemIDs);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelList.add(new TreeModel(treeInfoBean));
        }
        return treeModelList;
    }

    @Override
    public Map<String, TreeModel> searchContents(String conents) {
        Map<String, TreeModel> treeModelMap = new TreeMap<>();
        List<TreeInfoBean> treeInfoBeanList = itemInfoDao.searchContent(conents);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelMap.put(treeInfoBean.getId(), new TreeModel(treeInfoBean));
        }
        return treeModelMap;
    }

    @Override
    public List<TreeModel> searchContentsList(String conents) {
        List<TreeModel> treeModelList = new ArrayList<>();
        List<TreeInfoBean> treeInfoBeanList = itemInfoDao.searchContent(conents);
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            treeModelList.add(new TreeModel(treeInfoBean));
        }
        return treeModelList;
    }
}
