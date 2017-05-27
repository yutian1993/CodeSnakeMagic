package service.model;

import database.dbbean.TagInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public class TagModel {
    private int id;
    private String tag;

    public TagModel() {
    }

    public TagModel(TagInfoBean tagInfoBean) {
        this.id = tagInfoBean.getTid();
        this.tag = tagInfoBean.getTag();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 将bean转换成model
     * @param tagInfoBean
     * @return
     */
    public static TagModel covertBeanToModel(TagInfoBean tagInfoBean) {
        TagModel tagModel = new TagModel();
        tagModel.setId(tagInfoBean.getTid());
        tagModel.setTag(tagInfoBean.getTag());
        return tagModel;
    }

    /**
     * 将Beans列表转换成Models列表
     * @param tagInfoBeans
     * @return
     */
    public static List<TagModel> convertBeansToModels(List<TagInfoBean> tagInfoBeans) {
        List<TagModel> tagModels = new ArrayList<>();
        for (TagInfoBean tagInfoBean: tagInfoBeans) {
            tagModels.add(new TagModel(tagInfoBean));
        }
        return tagModels;
    }
}
