package ui;

import service.ItemService;
import service.SearchService;

/**
 * Created by wuwenchuan on 2017/5/22.
 */
public class ItemInfoThread extends Thread {
    private String itemid;
    private int index;
    ItemCallBack callBack;
    ItemService itemService;

    public ItemInfoThread(String name, String itemid, int index, ItemCallBack callBack) {
        super(name);
        this.itemid = itemid;
        this.index = index;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        if (callBack == null)                         //回调函数为空，无需执行
            return;

        if (itemService != null)                    //开始获取Item项
        {
            callBack.updateItemContent(itemid, index, itemService.getItemInfoContent(itemid));
        }
        else
            callBack.updateItemContent(itemid, index, "Search service error!".getBytes());

        itemService = null;
        callBack = null;
    }

    public void setSearchService(ItemService itemService) {
        this.itemService = itemService;
    }

    public interface ItemCallBack {
        void updateItemContent(String itemid, int index, byte[] content);
    }
}