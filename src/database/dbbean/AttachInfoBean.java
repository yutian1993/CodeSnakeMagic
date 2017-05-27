package database.dbbean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
@DatabaseTable(tableName = "attach_info")
public class AttachInfoBean {
    public static final String TABLENAME = "attach_info";
    public static final String ITEMID = "ITEMID";
    public static final String ATTACHID = "ATTACHID";
    public static final String TITLE = "TITLE";
    public static final String SIZE = "SIZE";
    public static final String CREATETIME = "CREATETIME";
    public static final String FILEDATA = "FILEDATA";
    public static final String STORAGEPATh = "STORAGEPATh";

    @DatabaseField(foreign = true, canBeNull = false, columnName = ITEMID)
    private ItemInfoBean itemInfoBean;
    @DatabaseField(columnName = ATTACHID, id = true)
    private Integer aid;
    @DatabaseField(columnName = TITLE)
    private String title;
    @DatabaseField(columnName = SIZE)
    private Integer size;
    @DatabaseField(columnName = CREATETIME, dataType = DataType.SQL_DATE)
    private Date createtime;
    @DatabaseField(columnName = FILEDATA, dataType= DataType.BYTE_ARRAY)
    private byte[] filedata;
    @DatabaseField(columnName = STORAGEPATh)
    private String storagepath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public String getStoragepath() {
        return storagepath;
    }

    public void setStoragepath(String storagepath) {
        this.storagepath = storagepath;
    }
}
