package service.model;

import database.dbbean.TreeInfoBean;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuwenchuan on 2017/5/20.
 */
public class TreeModel {

    //自身信息
    String treeID;           //目录ID
    String treeTitle;        //目录标题
    Integer treeType;        //目录属性（0：目录， 1：文件）
    Date createTime;         //创建时间
    Date modifyTime;         //修改时间

    @Deprecated
    Integer treeSeq;

    //包含的model信息，ID中有时间信息，自动就可以完成Map的排序任务
    Map<String, TreeModel> treeInfoModels;

    public TreeModel() {
        treeInfoModels = new HashMap<>();
    }

    public TreeModel(String treeid, String treetitle) {
        this.treeID = treeid;
        this.treeTitle = treetitle;
        this.treeSeq = -1;         //-1直接放到最后，如有多个-1，按照创建的时间先后过来
        treeInfoModels = new HashMap<>();
    }

    public TreeModel(TreeInfoBean treeInfoBean) {
        treeID = treeInfoBean.getId();
        treeType = treeInfoBean.getType();
        treeTitle = treeInfoBean.getTitle();
        createTime = treeInfoBean.getCreatetime();
        modifyTime = treeInfoBean.getModifytime();
    }

    public Map<String, TreeModel> getTreeInfoModels() {
        return treeInfoModels;
    }

    public void setTreeInfoModels(Map<String, TreeModel> treeInfoModels) {
        this.treeInfoModels.clear();
        this.treeInfoModels = null;
        this.treeInfoModels = treeInfoModels;
    }

    /**
     * 添加一个Item项作为当前的子项
     * @param treeID
     * @param treeTitle
     */
    public void addNewTreeInfo(String treeID, String treeTitle) {
        TreeModel newModel = new TreeModel(treeID, treeTitle);
        treeInfoModels.put(treeID, newModel);
    }

    /**
     * 删除Map中的子项
     * @param treeID 删除一个子项
     */
    public void deleteTreeInfo(String treeID) {
        treeInfoModels.remove(treeID);
    }

    public String getTreeID() {
        return treeID;
    }

    public void setTreeID(String treeID) {
        this.treeID = treeID;
    }

    public String getTreeTitle() {
        return treeTitle;
    }

    public void setTreeTitle(String treeTitle) {
        this.treeTitle = treeTitle;
    }

    public Integer getTreeType() {
        return treeType;
    }

    public void setTreeType(Integer treeType) {
        this.treeType = treeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 转换Bean到Model
     * @param treeInfoBean
     * @return
     */
    public static TreeModel covertBeanToModel(TreeInfoBean treeInfoBean) {
        TreeModel treeInfoModel = new TreeModel();
        treeInfoModel.setTreeID(treeInfoBean.getId());
        treeInfoModel.setTreeType(treeInfoBean.getType());
        treeInfoModel.setTreeTitle(treeInfoBean.getTitle());
        treeInfoModel.setCreateTime(treeInfoBean.getCreatetime());
        treeInfoModel.setModifyTime(treeInfoBean.getModifytime());
        return treeInfoModel;
    }

    /**
     * 转换List到Map
     * @param treeInfoBeans
     * @return
     */
    public  static Map<String, TreeModel> covertListBeansToMapModels(List<TreeInfoBean> treeInfoBeans) {
        Map<String, TreeModel> treeInfoModelMap = new TreeMap<>();
        for (TreeInfoBean bean: treeInfoBeans) {
            treeInfoModelMap.put(bean.getId(), covertBeanToModel(bean));
        }
        return treeInfoModelMap;
    }
}
