package service;

import service.model.ItemModel;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public interface ItemService {

    /**
     * 根据ID获取Item的详细信息
     * @param id id
     * @return 获取Item信息
     */
    ItemModel getItemInfo(String id);

    /**
     * 根据ID获取Item的主要内容
     * @param id id
     * @return 内容信息
     */
    byte[] getItemInfoContent(String id);

    /**
     * 根据ID获取Item的Title信息
     * @param id id
     * @param title 标题
     * @return 是否成功更新
     */
    boolean updateItemTitle(String id, String title);

    /**
     * 根据ID更新Item的content信息
     * @param id id
     * @param content item 内容
     * @return 是否成功更新
     */
    boolean updateItemContent(String id, byte[] content);

    /**
     * 根据item id和attach id更新附件title
     * @param itemid item id
     * @param attachid attach id
     * @param title 文件标题
     * @return 是否成功更新
     */
    boolean updateItemAttachTitle(String itemid, String attachid, String title);

    /**
     * 根据item id和attach id更新附件title
     * @param itemid item id
     * @param attachid attachment id
     * @param content 文件内容
     * @return 是否成功更新
     */
    boolean updateItemAttachContent(String itemid, String attachid, byte[] content);
}
