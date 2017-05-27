package service;

import com.intellij.openapi.components.ServiceManager;
import service.impl.SearchServiceImpl;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
public class CodeMagicServiceManager {

    /**
     * 获取容器中的SearchService
     * @return 返回SearchService
     */
    public static SearchService getSearchService() {
        return (SearchService) ServiceManager.getService(SearchService.class);
    }

    /**
     * 获取容器中的ItemService
     * @return 返回ItemService
     */
    public static ItemService getItemService() {
        return (ItemService) ServiceManager.getService(ItemService.class);
    }

}
