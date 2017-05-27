package ui;

import service.SearchService;
import service.model.TreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/23.
 */
public class SearchItemThread extends Thread {

    private SearchService searchService;
    private SearchCallBack searchCallBack;
    private String searchContent;

    public SearchItemThread(String name, String searchContent) {
        super(name);
        this.searchContent = searchContent;
    }

    @Override
    public void run() {
        if (searchCallBack != null && searchService != null) {
            searchCallBack.updateItemList(searchService.searchList(searchContent));
        } else {
            if (searchCallBack != null) {
                searchCallBack.updateItemList(new ArrayList<>());
            }
        }
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setSearchCallBack(SearchCallBack searchCallBack) {
        this.searchCallBack = searchCallBack;
    }

    public interface SearchCallBack {
        void updateItemList(List<TreeModel> treeModelList);
    }

}
