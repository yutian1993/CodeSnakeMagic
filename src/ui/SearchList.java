package ui;

import service.model.ItemModel;
import service.model.TreeModel;
import ui.model.SearchListModel;
import util.LogUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/22.
 */
public class SearchList extends JList implements ListSelectionListener {
    SearchListModel searchListModel;
    ListItemChange listItemChange;

    public SearchList() {
        searchListModel = new SearchListModel();
        setCellRenderer(new SearchListRender());
        setModel(searchListModel);
        this.addListSelectionListener(this);
    }

    public void addNewItem(ItemModel treeModel) {
        searchListModel.add(treeModel);
    }

    public ListItemChange getListItemChange() {
        return listItemChange;
    }

    public void setListItemChange(ListItemChange listItemChange) {
        this.listItemChange = listItemChange;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Object obj = this.getSelectedValue();
            if (obj instanceof ItemModel) {
                ItemModel itemModel = (ItemModel) obj;
                if (listItemChange != null)
                    listItemChange.changeItemID(this.getSelectedIndex(), itemModel.getItemID());
                else {
                    LogUtil.infor("Not bind trigger information!");
                }
            }
        }
    }

    public byte[] reuestModelItemContent(int index) {
        ItemModel itemModel = searchListModel.getElementAt(index);
        return itemModel.getContentInfo();
    }

    public void updateModelItemContent(int index, byte[] content) {
        ItemModel itemModel = searchListModel.getElementAt(index);
        itemModel.setContentInfo(null);
        itemModel.setContentInfo(content);
    }

    /**
     * 更新列表信息
     * @param treeModelList
     */
    public void refreshListUI(List<TreeModel> treeModelList) {
        searchListModel.refreshList(treeModelList);
        updateUI();
    }

    public interface ListItemChange {
        void changeItemID(int index, String id);
    }
}
