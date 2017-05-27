package database.dbbean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuwenchuan on 2017/5/19.
 */
@DatabaseTable(tableName = "tag_info")
public class TagInfoBean {
    public static final String TABLENAME="tag_info";
    public static final String TID="TID";
    public static final String TAG="TAG";

    @DatabaseField(generatedId = true, columnName = TID)
    private Integer tid;
    @DatabaseField(columnName = TAG, index = true)
    private String tag;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
