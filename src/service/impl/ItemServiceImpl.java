package service.impl;

import database.dbbean.ItemInfoBean;
import database.dbbean.ItemTagInfoBean;
import database.dbdao.ItemInfoDao;
import database.dbdao.ItemTagInfoDao;
import database.dbdao.TagInfoDao;
import service.ItemService;
import service.model.ItemModel;
import service.model.TagModel;

import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public class ItemServiceImpl implements ItemService {
    private ItemInfoDao itemInfoDao;
    private TagInfoDao tagInfoDao;
    private ItemTagInfoDao itemTagInfoDao;

    public ItemServiceImpl() {
        itemInfoDao = new ItemInfoDao();
        tagInfoDao = new TagInfoDao();
        itemTagInfoDao = new ItemTagInfoDao();
    }

    @Override
    public ItemModel getItemInfo(String id) {
        ItemInfoBean itemInfoBean = itemInfoDao.getItemById(id);
        ItemModel itemModel = ItemModel.convertBeanToModel(itemInfoBean);
        List<ItemTagInfoBean> itemTagBeans = itemTagInfoDao.getItemTagsByItemID(id);
        for (ItemTagInfoBean bean: itemTagBeans) {
            itemModel.addTagModel(new TagModel(bean.getTagInfoBean()));
        }
        return itemModel;
    }

    @Override
    public byte[] getItemInfoContent(String id) {
        ItemInfoBean itemInfoBean = itemInfoDao.getItemById(id);
        if (itemInfoBean != null)
            return itemInfoBean.getContentinfo();
        return null;
    }

    @Override
    public boolean updateItemTitle(String id, String title) {
        return itemInfoDao.updateItemTitleByID(id, title);
    }

    @Override
    public boolean updateItemContent(String id, byte[] content) {
        return itemInfoDao.updateItemConentByID(id, content);
    }

    @Override
    public boolean updateItemAttachTitle(String itemid, String attachid, String title) {
        return false;
    }

    @Override
    public boolean updateItemAttachContent(String itemid, String attachid, byte[] content) {
        return false;
    }
}
