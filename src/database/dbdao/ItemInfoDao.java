package database.dbdao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import database.dbbean.ItemInfoBean;
import database.dbbean.TreeInfoBean;
import database.util.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class ItemInfoDao {
    private Dao<ItemInfoBean, String> dao;

    public ItemInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(ItemInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 新建一条Item信息
    public void createNewItemInfo(ItemInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据ID获取Item信息
    public ItemInfoBean getItemById(String id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据ID删除Item信息
    public boolean deleteItemById(String id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 批量删除item ids
     *
     * @param ids item ids
     * @return
     */
    public boolean deleteItemByIDs(String[] ids) {
        if (ids == null || ids.length == 0)
            return true;

        String sql = "DELETE FOROM " + ItemInfoBean.TABLENAME + " WHERE "
                + ItemInfoBean.ITEMID + " IN " + SQLUtil.generateINSQL(ids);
        try {
            dao.executeRawNoArgs(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据ID来更新Item的title
     *
     * @param id    item id
     * @param title item title
     * @return 根据ID来更新title
     */
    public boolean updateItemTitleByID(String id, String title) {
        try {
            dao.updateRaw("UPDATE " + ItemInfoBean.TABLENAME + " SET " + ItemInfoBean.TITLE + " = ? WHERE " +
                    ItemInfoBean.ITEMID + " = ? ", title, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据ID来更新Item的content
     * @param id item id
     * @param content item content
     * @return
     */
    public boolean updateItemConentByID(String id, byte[] content) {
        try {
            ItemInfoBean itemInfoBean = dao.queryForId(id);
            if (itemInfoBean != null) {
                itemInfoBean.setContentinfo(content);
                dao.update(itemInfoBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 搜索content
     * @param content
     * @return item list
     */
    public List<TreeInfoBean> searchContent(String content) {
        List<TreeInfoBean> treeInfoBeans = new ArrayList<>();
        try {
            GenericRawResults<String[]> rawResults =
                    dao.queryRaw("SELECT " + ItemInfoBean.ITEMID + ", TITLE FROM " + ItemInfoBean.TABLENAME +
                            " WHERE " + ItemInfoBean.CONTENTINFO + " LIKE '%" + content + "%'");
            List<String[]> results = rawResults.getResults();
            generateStringArrToBean(treeInfoBeans, results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treeInfoBeans;
    }

    /**
     * 搜索content
     * @param ids Item id
     * @return item list
     */
    public List<TreeInfoBean> searchItemIDs(String[] ids) {
        List<TreeInfoBean> treeInfoBeans = new ArrayList<>();
        try {
            GenericRawResults<String[]> rawResults =
                    dao.queryRaw("SELECT " + ItemInfoBean.ITEMID + "," + ItemInfoBean.TITLE + " FROM " + ItemInfoBean.TABLENAME +
                            " WHERE " + ItemInfoBean.ITEMID  + " IN " + SQLUtil.generateINSQL(ids));
            List<String[]> results = rawResults.getResults();
            generateStringArrToBean(treeInfoBeans, results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treeInfoBeans;
    }

    private void generateStringArrToBean(List<TreeInfoBean> treeInfoBeans,
                                    List<String[]> results) {
        TreeInfoBean treeInfoBean = null;
        for (String[] columns: results) {
            treeInfoBean = new TreeInfoBean();
            treeInfoBean.setTitle(columns[1]);
            treeInfoBean.setId(columns[0]);
            treeInfoBean.setType(SQLUtil.DB_TREE_FILE);
            treeInfoBeans.add(treeInfoBean);
        }
    }
}
