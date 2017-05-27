package database.dbdao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import database.dbbean.TagInfoBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class TagInfoDao {
    private Dao<TagInfoBean, Integer> dao;

    public TagInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(TagInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 新建一条Tag信息
    public void createNewTagInfo(TagInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据ID获取Tag信息
    public TagInfoBean getTagById(Integer id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据ID删除Tag信息
    public boolean deleteTagById(Integer id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取所有的Tag信息
     * @return TAG列表
     */
    public List<TagInfoBean> getAllTags() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除所有的Tag信息
     * @return 是否成功删除
     */
    public boolean deleteAllTags() {
        try {
            dao.executeRawNoArgs("DELETE FROM " + TagInfoBean.TABLENAME);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 搜索tag
     * @param tags
     * @return item list
     */
    public String[] searchContent(String tags) {
        String[] tagids = null;
        try {
            GenericRawResults<String[]> rawResults =
                    dao.queryRaw("SELECT " + TagInfoBean.TID + " FROM " + TagInfoBean.TABLENAME +
                            " WHERE " + TagInfoBean.TAG + " LIKE '%" + tags + "%'");
            List<String[]> results = rawResults.getResults();
            tagids = new String[results.size()];
            for(int index = 0; index < results.size(); ++index) {
                tagids[index] = results.get(index)[0];
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagids;
    }
}
