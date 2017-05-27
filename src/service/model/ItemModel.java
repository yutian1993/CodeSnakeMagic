package service.model;

import database.dbbean.ItemInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public class ItemModel {
    private String itemID;
    private String title;
    private byte[] contentInfo;
    private List<TagModel> tagModels;

    //UI 选中状态与否
    private boolean isSelect;

    public ItemModel() {
        tagModels = new ArrayList<>();
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(byte[] contentInfo) {
        if (this.contentInfo != null && this.contentInfo.length > 0)
            this.contentInfo = null;
        this.contentInfo = contentInfo;
    }

    public List<TagModel> getTagModels() {
        return tagModels;
    }

    public void setTagModels(List<TagModel> tagModels) {
        this.tagModels = tagModels;
    }

    /**
     * 添加一个新的TagModel
     * @param tagModel
     */
    public void addTagModel(TagModel tagModel) {
        this.tagModels.add(tagModel);
    }

    public static ItemModel convertBeanToModel(ItemInfoBean itemInfoBean) {
        ItemModel itemModel = new ItemModel();
        itemModel.setItemID(itemInfoBean.getItemid());
        itemModel.setContentInfo(itemInfoBean.getContentinfo());
        itemModel.setTitle(itemInfoBean.getTitle());
        return itemModel;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
