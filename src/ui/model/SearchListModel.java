package ui.model;

import service.model.ItemModel;
import service.model.TreeModel;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by wuwenchuan on 2017/5/22.
 */
public class SearchListModel implements ListModel {

    List<ItemModel> searchListModel;

    public SearchListModel() {
        searchListModel = new ArrayList<>();
    }


    public boolean add(ItemModel o) {
        return searchListModel.add(o);
    }

    public ItemModel remove(int index) {
        return searchListModel.remove(index);
    }

    public List<ItemModel> getSearchListModel() {
        return searchListModel;
    }

    public void setSearchListModel(List<ItemModel> searchListModel) {
        if (this.searchListModel.size() > 0)
            this.searchListModel.clear();
        this.searchListModel = searchListModel;
    }

    /**
     * 更新对应index的item content
     * @param index 列表索引
     * @param content
     */
    public void updateItemContent(int index, byte[] content) {
        ItemModel itemModel = getElementAt(index);
        if (itemModel != null) {
            itemModel.setContentInfo(content);
        }
    }

    @Override
    public int getSize() {
        return searchListModel.size();
    }

    @Override
    public ItemModel getElementAt(int index) {
        if (index < 0 || index >= searchListModel.size())
            return null;
        return searchListModel.get(index);
    }

    /**
     * 更新列表
     * @param treeModelList
     */
    public void refreshList(List<TreeModel> treeModelList) {
        if (treeModelList != null) {
            clean();
            for (TreeModel treeModel: treeModelList) {
                ItemModel itemModel = new ItemModel();
                itemModel.setItemID(treeModel.getTreeID());
                itemModel.setTitle(treeModel.getTreeTitle());
                searchListModel.add(itemModel);
            }
        }
    }

    /**
     * 清空列表
     * @return
     */
    public boolean clean() {
        searchListModel.clear();
        return true;
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
