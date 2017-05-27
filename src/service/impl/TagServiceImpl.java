package service.impl;

import database.dbbean.TagInfoBean;
import database.dbdao.TagInfoDao;
import service.TagService;
import service.model.TagModel;

import java.util.List;

/**
 * Created by wuwenchuan on 2017/5/21.
 */
public class TagServiceImpl implements TagService {
    private TagInfoDao tagInfoDao;

    public TagServiceImpl() {
        tagInfoDao = new TagInfoDao();
    }

    @Override
    public List<TagModel> getAllTag() {
        return TagModel.convertBeansToModels(tagInfoDao.getAllTags());
    }

    @Override
    public int addNewTag(String tag) {
        TagInfoBean tagInfoBean = new TagInfoBean();
        tagInfoBean.setTag(tag);
        tagInfoDao.createNewTagInfo(tagInfoBean);
        return tagInfoBean.getTid();
    }

    @Override
    public String getTagByID(int id) {
        TagInfoBean tagInfoBean = tagInfoDao.getTagById(id);
        if (tagInfoBean != null)
            return tagInfoBean.getTag();
        return null;
    }

    @Override
    public boolean deleteTagByID(int id) {
        return tagInfoDao.deleteTagById(id);
    }

    @Override
    public boolean deleteAllTags() {
        return tagInfoDao.deleteAllTags();
    }
}
