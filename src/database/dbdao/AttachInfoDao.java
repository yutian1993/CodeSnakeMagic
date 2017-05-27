package database.dbdao;

import com.j256.ormlite.dao.Dao;
import database.dbbean.AttachInfoBean;

import java.sql.SQLException;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class AttachInfoDao {
    private Dao<AttachInfoBean, String> dao;

    public AttachInfoDao() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        try {
            dao = databaseHelper.getDao(AttachInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 新建一条Attach信息
    public void createAttachInfo(AttachInfoBean bean) {
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能还需要完善
     */
}
