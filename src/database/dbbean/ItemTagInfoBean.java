package database.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
@DatabaseTable(tableName = "itemtag_info")
public class ItemTagInfoBean {
    public static final String TABLENAME="itemtag_info";
    public static final String ITEMID="ITEMID";
    public static final String TID="TID";
    //Item ID
    @DatabaseField(foreign = true, canBeNull = false, columnName = ITEMID)
    private ItemInfoBean itemInfoBean;
    //Tag ID
    @DatabaseField(foreign = true, canBeNull = false, columnName = TID)
    private TagInfoBean tagInfoBean;

    public ItemInfoBean getItemInfoBean() {
        return itemInfoBean;
    }

    public void setItemInfoBean(ItemInfoBean itemInfoBean) {
        this.itemInfoBean = itemInfoBean;
    }

    public TagInfoBean getTagInfoBean() {
        return tagInfoBean;
    }

    public void setTagInfoBean(TagInfoBean tagInfoBean) {
        this.tagInfoBean = tagInfoBean;
    }
}
