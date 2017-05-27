package database.dbdao;

import com.j256.ormlite.dao.Dao;
import database.dbbean.DBStatusInfoBean;
import database.util.SQLUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class DBStatusInfoDao {
    private Dao<DBStatusInfoBean, String> dao;

    private static final Integer ITEMADD = 2;
    private static final Integer ITEMCHANGE = 1;
    private static final Integer ITEMDELETE = -1;
    private static final Integer ITEMFIX = 0;

    public DBStatusInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(DBStatusInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个新的DBStatus记录
     *
     * @param bean 记录
     */
    public void addNewItemInfo(DBStatusInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接添加一个新的Item ID
     *
     * @param itemid
     */
    public void addNewItemInfo(String itemid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("INSERT INTO " + DBStatusInfoBean.TABLENAME +
                    " ( " + DBStatusInfoBean.TREEID + "," + DBStatusInfoBean.STATUS + " ) " + " VALUES (?,?)", itemid, ITEMADD));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改了一条记录
     * @param itemid item id
     */
    public void changeNewItemInfo(String itemid) {
        try {
            List<DBStatusInfoBean> beans = dao.queryForEq(DBStatusInfoBean.TREEID, itemid);
            if (beans.size() == 0) {
                addNewItemInfo(itemid);
            } else {
                if (beans.get(0).getStatus() == ITEMFIX)
                    updatNewItemInfoStatus(itemid, ITEMCHANGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新到删除状态
     *
     * @param itemid
     */
    public void deleteNewItemInfo(String itemid) {
        try {
            List<DBStatusInfoBean> beans = dao.queryForEq(DBStatusInfoBean.TREEID, itemid);
            if (beans.size() != 0) {
                if (beans.get(0).getStatus() == ITEMADD) {            //还没有同步过的数据，直接删除记录
                    deleteNoneSatus(itemid);
                    return;
                } else {                                               //更新数据库表记录到删除状态
                    updatNewItemInfoStatus(itemid, ITEMDELETE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * PC和Mobile完成了同步
     *
     * @param itemid
     */
    public void hasSyncWithMobile(String itemid) {
        try {
            List<DBStatusInfoBean> beans = null;
            beans = dao.queryForEq(DBStatusInfoBean.TREEID, itemid);
            if (beans.size() == 0) {
                addNewItemInfo(itemid);
            } else {
                DBStatusInfoBean bean = beans.get(0);
                if (bean.getStatus() == ITEMDELETE) {
                    deleteNoneSatus(itemid);
                } else {
                    updatNewItemInfoStatus(itemid, ITEMFIX);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果删除了一条记录，那么该记录的同步信息也就没有必要存在
     * 删除一条状态信息
     * @param itemid item id
     */
    private void deleteNoneSatus(String itemid) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + DBStatusInfoBean.TABLENAME +
                    " WHERE " + DBStatusInfoBean.TREEID + " = ?", itemid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除Items的状态信息
     * @param ids item ids
     */
    private void deleteNoneSatus(String[] ids) {
        if (ids == null || ids.length == 0)
            return;

        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("DELETE FROM " + DBStatusInfoBean.TABLENAME +
                    " WHERE " + DBStatusInfoBean.TREEID + " IN ", SQLUtil.generateINSQL(ids)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新一个Item的状态信息
     * 1 : 修改
     * 0 : 不变
     * -1 ：删除
     *
     * @param itemid item id
     * @param status 状态值
     */
    private void updatNewItemInfoStatus(String itemid, int status) {
        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("UPDATE " + DBStatusInfoBean.TABLENAME +
                    " SET " + DBStatusInfoBean.STATUS + " = ? WHERE " + DBStatusInfoBean.TREEID + " = ?", status, itemid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新一个Items的状态信息
     * 1 : 修改
     * 0 : 不变
     * -1 ：删除
     * @param ids
     * @param status
     */
    private void updateNewItemInfoStatus(String[] ids, int status) {
        if (ids == null || ids.length == 0)
            return;

        try {
            dao.executeRaw(SQLUtil.generateInsertSQL("UPDATE " + DBStatusInfoBean.TABLENAME +
                    " SET " + DBStatusInfoBean.STATUS + " = ? WHERE " + DBStatusInfoBean.TREEID + " IN ?", status, SQLUtil.generateINSQL(ids)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
