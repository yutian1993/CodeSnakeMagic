package service;

import service.model.TreeModel;

import java.util.Map;

/**
 * 目录操作Service
 * Created by wuwenchuan on 2017/5/20.
 */
public interface TreeService {

    /**
     * 获取根目录
     * @return
     */
    Map<String, TreeModel> getRootTreeInfo();

    /**
     * 获取指定ID目录信息
     * @return
     */
    Map<String, TreeModel> getTreeInfo(String treeid);

    /**
     * 新建一个目录索引对象
     * @param title 所以标题
     * @param type 索引类别（0：目录，1：文件）
     * @param pid
     * @return 插入对象的ID值
     */
    String insertNewTreeInfo(String title, Integer type, String pid);

    /**
     * 更新ree的Title
     * @param id tree id
     * @param title title
     */
    void updateNewTreeInfo(String id, String title);

    /**
     * 删除Tree中的 指定ID
     * 如果删除的是文件对象，直接删除即可；
     * 如果删除的是目录对象，删除所有的关联对象；
     * @param id
     */
    void deleteTreeInfo(String id);
}
