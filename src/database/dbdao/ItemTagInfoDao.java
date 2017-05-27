package database.dbdao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import database.dbbean.ItemTagInfoBean;
import database.util.SQLUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class ItemTagInfoDao {
    private Dao<ItemTagInfoBean, Void> dao;

    public ItemTagInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(ItemTagInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 新建一条Item信息
    public void createNewItemTagInfo(ItemTagInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用ID直接插入TAG信息
     * @param itemid ITEM ID
     * @param tagid Tag ID
     */
    public void createNewItemTaginfo(String itemid, Integer tagid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("INSERT INTO " + ItemTagInfoBean.TABLENAME +
                    "(" + ItemTagInfoBean.ITEMID + "," + ItemTagInfoBean.TID + ")" + " VALUES (?,?) ", itemid, tagid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据item id获取该item所有的TAG信息
     * @param itemid
     * @return
     */
    public List<ItemTagInfoBean> getItemTagsByItemID(String itemid) {
        List<ItemTagInfoBean> result = null;
        try {
            result = dao.queryForEq(ItemTagInfoBean.ITEMID, itemid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据TAG ID获取获取所有使用过该ID的ITEM信息
     * @param tagid
     * @return
     */
    public List<ItemTagInfoBean> getItemsByTag(Integer tagid) {
        List<ItemTagInfoBean> result = null;
        try {
            result = dao.queryForEq(ItemTagInfoBean.TID, tagid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  根据删除一个ITEM的一个TAG
     * @param itemid item id
     * @param tagid tag id
     */
    public void deleteItemTagByItemIDAndTagID(String itemid, Integer tagid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + ItemTagInfoBean.TABLENAME +
                    " WHERE " + ItemTagInfoBean.ITEMID + " = ? AND" + ItemTagInfoBean.TID + " = ?", itemid, tagid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ITEM ID删除ITEM
     * @param itemid ITEM ID
     */
    public void deleteItemTagByItemID(String itemid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + ItemTagInfoBean.TABLENAME +
                    " WHERE " + ItemTagInfoBean.ITEMID + " = ? ", itemid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除ITEM IDs
     * @param ids item ids
     */
    public void deleteItemTagByItemIDs(String[] ids) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + ItemTagInfoBean.TABLENAME +
                    " WHERE " + ItemTagInfoBean.ITEMID + " IN ", SQLUtil.generateINSQL(ids)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据TAG ID删除该TAG相关的所有ITEMTAG
     * @param tagid
     */
    public void deleteItemTagByTagID(Integer tagid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + ItemTagInfoBean.TABLENAME +
                    " WHERE " + ItemTagInfoBean.TID + " = ?", tagid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索符合规定的Item
     * @param tagIDs
     * @return item list
     */
    public String[] searchContent(String[] tagIDs) {
        String[] itemids = null;
        try {
            GenericRawResults<String[]> rawResults =
                    dao.queryRaw("SELECT DISTINCT(" + ItemTagInfoBean.ITEMID + ") FROM " + ItemTagInfoBean.TABLENAME +
                            " WHERE " + ItemTagInfoBean.TID + " IN " + SQLUtil.generateINSQL(tagIDs));
            List<String[]> results = rawResults.getResults();
            itemids = new String[results.size()];
            for(int index = 0; index < results.size(); ++index) {
                itemids[index] = results.get(index)[0];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemids;
    }
}
