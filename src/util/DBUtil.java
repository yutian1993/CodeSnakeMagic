package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wuwenchuan on 2017/5/9.
 */
public class DBUtil {

    /**
     * 判断该文件是否为sqlite数据库
     * @param filepath 数据库地址
     * @return
     */
    public static boolean isSqliteDB(String filepath) {
        Connection temp = connectSqliteDB(filepath);
        Statement stat = null;
        boolean result = temp != null;
        try {
            stat = temp.createStatement();
            //尝试获取数据库中的所有表
            result = stat.execute("select name from sqlite_master where type='table' order by name");
            stat.close();
            temp.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                temp.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取数据库的Connection
     * @param filepath 数据库地址
     * @return
     */
    private static Connection connectSqliteDB(String filepath) {
        Connection dbConnection = null;
        String sql="jdbc:sqlite://" + filepath;
        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

}
