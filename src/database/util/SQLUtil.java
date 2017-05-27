package database.util;

import database.dbbean.TreeInfoBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
public class SQLUtil {

    public static final String DR_PID = "-1";
    public static final int DB_TREE_DIR = 0;
    public static final int DB_TREE_FILE = 1;

    private static final SimpleDateFormat DB_ID_DATE =
            new SimpleDateFormat("yyyyMMddmmssSSS");

    /**
     * 构造SQL语句
     * @param sql sql语句
     * @param args 参数
     * @return sql语句
     */
    public static String generateInsertSQL(String sql, Object... args) {
        StringBuilder newSql = new StringBuilder(sql);
        int index = -1;
        for (Object obj : args) {
            index = newSql.indexOf("?");
            if (index < 0)
                break;
            if (obj instanceof String) {
                newSql = newSql.replace(index, index+1, "'" + (String) obj + "'");
            } else if (obj instanceof Integer) {
                newSql = newSql.replace(index, index+1, String.valueOf((Integer) obj));
            } else if (obj instanceof Date) {
                Date date = (Date) obj;
                newSql = newSql.replace(index, index+1, "'" + String.valueOf(date.getTime()) + "'");
            } else {
                newSql = newSql.replace(index, index+1, "'" + String.valueOf(obj) + "'");
            }
        }
        return newSql.toString();
    }

    /**
     * 将date转成String ID
     * @param date
     * @return
     */
    public static String generateDatetimeID(Date date) {
        return DB_ID_DATE.format(date);
    }

    /**
     * 构造如('A','B','C')该样的字符串
     * @param items 对象str列表
     * @return 构造好的字符串
     */
    public static String generateINSQL(String[] items) {
        String includs = " (";
        for (String str: items) {
            includs = includs + "'" + str + "',";
        }
        includs = includs.substring(0, includs.length()-1);

        includs += ")";
        return includs;
    }

    /**
     * 构造Item IDs数组列表信息
     * @param treeInfoBeanList Tree Item information
     * @return Item IDs array
     */
    public static String[] getItemIDsFromTreeInfoBeanList(List<TreeInfoBean> treeInfoBeanList) {
        String[] ids = new String[treeInfoBeanList.size()];
        int index = 0;
        for (TreeInfoBean treeInfoBean: treeInfoBeanList) {
            ids[index] = treeInfoBean.getId();
            ++index;
        }
        return ids;
    }
}
