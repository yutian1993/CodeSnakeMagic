package database.dbbean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by wuwenchuan on 2017/5/17.
 */
@DatabaseTable(tableName = "tree_info")
public class TreeInfoBean {
    public static final String TABLENAME = "tree_info";
    public static final String ID = "ID";
    public static final String PID = "PID";
    public static final String TYPE = "TYPE";
    public static final String STYLEID = "STYLEID";
    public static final String CREATETIME = "CREATETIME";
    public static final String MODIFYTIME = "MODIFYTIME";
    public static final String SEQUNCE = "SEQUNCE";
    public static final String TITLE="TITLE";

    //每一个ITEM的ID都是自动生成，这里会出现删除的ID服务复用的问题
    @DatabaseField(id = true, canBeNull = false, columnName = ID)
    private String id;
    //父目录的ID，null表示的是根目录
    @DatabaseField(columnName = PID)
    private String pid;
    //类别（0：目录，1：文件）
    @DatabaseField(columnName = TYPE)
    private int type;
    //代码样式（保留字段，暂时无用处）
    @DatabaseField(columnName = STYLEID)
    private Integer styleid;
    //创建时间
    @DatabaseField(columnName = CREATETIME, dataType = DataType.SQL_DATE)
    private Date createtime;
    //修改时间
    @DatabaseField(columnName = MODIFYTIME, dataType = DataType.SQL_DATE)
    private Date modifytime;
    //目录中的排序
    @DatabaseField(columnName = SEQUNCE)
    private Integer sequnce;
    //目录的title
    @DatabaseField(columnName = TITLE, width = 4096, index = true)
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStyleid() {
        return styleid;
    }

    public void setStyleid(int styleid) {
        this.styleid = styleid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public int getSequnce() {
        return sequnce;
    }

    public void setSequnce(int sequnce) {
        this.sequnce = sequnce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
