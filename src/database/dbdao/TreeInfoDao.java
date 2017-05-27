package database.dbdao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import database.dbbean.TreeInfoBean;
import database.util.SQLUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
public class TreeInfoDao {
    private Dao<TreeInfoBean, String> dao;

    public TreeInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(TreeInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 新建一条Item信息
    public void createNewTreeInfo(TreeInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据ID获取Item信息
    public TreeInfoBean getItemById(String id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据ID获取Item信息
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
     * 根据PID获取该项下的所有ITEM
     *
     * @param pid PID
     * @return 列表
     */
    public List<TreeInfoBean> getItemsByPID(String pid) {
        try {
            if (pid == null) {
                List<TreeInfoBean> treeInfoBeans = new ArrayList<>();
                try {
                    GenericRawResults<String[]> rawResults =
                            dao.queryRaw("SELECT " + TreeInfoBean.ID + ", " +TreeInfoBean.TYPE +"," + TreeInfoBean.TITLE +"  FROM " + TreeInfoBean.TABLENAME +
                                    " WHERE " + TreeInfoBean.PID  + " = -1");
                    List<String[]> results = rawResults.getResults();
                    TreeInfoBean treeInfoBean = null;
                    for (String[] columns : results) {
                        treeInfoBean = new TreeInfoBean();
                        treeInfoBean.setId(columns[0]);
                        treeInfoBean.setTitle(columns[2]);
                        treeInfoBean.setType(Integer.valueOf(columns[1]));
                        treeInfoBeans.add(treeInfoBean);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return treeInfoBeans;
            } else {
                return dao.queryForEq(TreeInfoBean.PID, pid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新Tree Item中Title
     *
     * @param id    tree id
     * @param title tree title
     */
    public void updateTitleInfo(String id, String title) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("UPDATE " + TreeInfoBean.TABLENAME + " SET " +
                            TreeInfoBean.TITLE + " = ? , " + TreeInfoBean.MODIFYTIME +
                            " = ? WHERE " + TreeInfoBean.ID + " = ?",
                    title, new Date(System.currentTimeMillis()), id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某一个父ID关联的所有对象
     *
     * @param pid 父ID
     */
    public void deleteItemByPID(String pid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + TreeInfoBean.TABLENAME + " WHERE " +
                    TreeInfoBean.PID + " = ? " + pid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有相关的ITEM IDs信息
     *
     * @param ids Tree Item IDs
     */
    public void deleteItemByID(String[] ids) {
        if (ids == null || ids.length == 0)
            return;

        String sql = "DELETE FOROM " + TreeInfoBean.TABLENAME + " WHERE "
                + TreeInfoBean.ID + " IN " + SQLUtil.generateINSQL(ids);

        try {
            dao.executeRawNoArgs(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索title
     *
     * @param title
     * @return item list
     */
    public List<TreeInfoBean> searchTitle(String title) {
        List<TreeInfoBean> treeInfoBeans = new ArrayList<>();
        try {
            GenericRawResults<String[]> rawResults =
                    dao.queryRaw("SELECT " + TreeInfoBean.ID + ", TITLE FROM " + TreeInfoBean.TABLENAME +
                            " WHERE " + TreeInfoBean.TITLE + " LIKE '%" + title + "%' AND TYPE = 1");
            List<String[]> results = rawResults.getResults();
            TreeInfoBean treeInfoBean = null;
            for (String[] columns : results) {
                treeInfoBean = new TreeInfoBean();
                treeInfoBean.setId(columns[0]);
                treeInfoBean.setTitle(columns[1]);
                treeInfoBean.setType(SQLUtil.DB_TREE_FILE);
                treeInfoBeans.add(treeInfoBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treeInfoBeans;
    }

}
