package service;

import service.model.TagModel;

import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public interface TagService {

    /**
     * 获取所有的TAG信息
     * @return
     */
    List<TagModel> getAllTag();

    /**
     * 添加一个新的TAG
     * @param tag 标签
     * @return ID值
     */
    int addNewTag(String tag);

    /**
     * 根据ID获取TAG的String信息
     * @param id
     * @return
     */
    String getTagByID(int id);

    /**
     * 根据ID删除一个TAG信息
     * @param id
     * @return
     */
    boolean deleteTagByID(int id);

    /**
     * 删除所有的标签
     * @return
     */
    boolean deleteAllTags();
}
