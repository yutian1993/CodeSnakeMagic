package database.dbbean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
@DatabaseTable(tableName = "item_info")
public class ItemInfoBean {
    public static final String TABLENAME="item_info";
    public static final String ITEMID="ITEMID";
    public static final String TITLE="TITLE";
    public static final String CONTENTINFO="CONTENTINFO";

    //TreeItem中的ID
    @DatabaseField(columnName = ITEMID, index = true, id = true, canBeNull = false)
    private String itemid;
    //Item的title
    @DatabaseField(columnName = TITLE, width = 4096, index = true)
    private String title;
    //Item的content
    @DatabaseField(columnName = CONTENTINFO, dataType= DataType.BYTE_ARRAY)
    private byte[] contentinfo;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContentinfo() {
        return contentinfo;
    }

    public void setContentinfo(byte[] contentinfo) {
        this.contentinfo = contentinfo;
    }
}
