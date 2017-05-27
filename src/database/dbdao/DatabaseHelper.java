package database.dbdao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import control.SettingControl;
import database.dbbean.*;
import util.ConfigureValues;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
public class DatabaseHelper {
    private JdbcPooledConnectionSource gConnectionSource;

    private static DatabaseHelper gDatabaseHelper;

    /**
     * 获取单例的数据库操作对象
     */
    public static DatabaseHelper getInstance() {
        if (gDatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (gDatabaseHelper == null)
                    gDatabaseHelper = new DatabaseHelper();
            }
        }
        return gDatabaseHelper;
    }

    public DatabaseHelper() {
        setupDatabase();
    }

    public Dao getDao(Class clz) {
        try {
            return (Dao) DaoManager.createDao(getConnectionSource(), clz);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 配置数据库的连接信息以及数据库表的初始化信息
     */
    public void setupDatabase() {
        try {
            ConnectionSource coonect = getConnectionSource();
            TableUtils.createTableIfNotExists(coonect, TreeInfoBean.class);
            TableUtils.createTableIfNotExists(coonect, ItemInfoBean.class);
            TableUtils.createTableIfNotExists(coonect, ItemTagInfoBean.class);
            TableUtils.createTableIfNotExists(coonect, TagInfoBean.class);
            TableUtils.createTableIfNotExists(coonect, AttachInfoBean.class);
            TableUtils.createTableIfNotExists(coonect, DBStatusInfoBean.class);
            coonect = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取到连接数据库
     * @return 连接对象
     */
    private ConnectionSource getConnectionSource() {
        //大部分流程都是走这段简洁逻辑
        if (isConnect())
            return gConnectionSource;

        //初始化数据库连接
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Impossible, can't find driver!");
            return gConnectionSource;
        }
        String connectionString = "jdbc:sqlite:" + (ConfigureValues.G_DEBUG ?
                ConfigureValues.D_DBNAME : SettingControl.getSettingControl().getDatabasePath());
        if (gConnectionSource == null) {
            //每次打开连接只保活5分钟
            try {
                gConnectionSource = new JdbcPooledConnectionSource(connectionString);
//                gConnectionSource.setMaxConnectionAgeMillis(5*60*100);
            } catch (SQLException e) {
                e.printStackTrace();
                return gConnectionSource;
            }
            return gConnectionSource;
        }
        if (gConnectionSource.isOpen(ConfigureValues.G_DEBUG ?
                ConfigureValues.D_DBNAME : SettingControl.getSettingControl().getDatabasePath())) {
            return gConnectionSource;
        } else {
            try {
                gConnectionSource.close();
                gConnectionSource = null;
                gConnectionSource = new JdbcPooledConnectionSource(connectionString);
//                gConnectionSource.setMaxConnectionAgeMillis(5*60*100);
            } catch (IOException e) {
                e.printStackTrace();
                //操作异常，停止该次数据库请求
                gConnectionSource = null;
            } catch (SQLException e) {
                e.printStackTrace();
                gConnectionSource = null;
            }
        }
        return gConnectionSource;
    }

    /**
     * 判断目前的数据库是否仍处于连接状态
     * @return 是否处于连接状态
     */
    public boolean isConnect() {
        if (!ConfigureValues.G_DEBUG)
            if (gConnectionSource != null && gConnectionSource.isOpen(SettingControl.getSettingControl().getDatabasePath()))
                return true;
        return false;
    }
}
