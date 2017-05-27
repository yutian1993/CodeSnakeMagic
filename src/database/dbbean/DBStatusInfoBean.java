package database.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
@DatabaseTable(tableName = "dbstatus_info")
public class DBStatusInfoBean {
    public static final String TABLENAME = "dbstatus_info";
    public static final String SEQUENCE = "SEQUENCE";
    public static final String TREEID = "ID";
    public static final String STATUS = "STATUS";

    @DatabaseField(generatedId = true, columnName = SEQUENCE)
    private Integer sequence;
    @DatabaseField(foreign = true, columnName = TREEID, canBeNull = false)
    private TreeInfoBean treeInfoBean;
    @DatabaseField(columnName = STATUS)
    private Integer status;

    public TreeInfoBean getTreeInfoBean() {
        return treeInfoBean;
    }

    public void setTreeInfoBean(TreeInfoBean treeInfoBean) {
        this.treeInfoBean = treeInfoBean;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
