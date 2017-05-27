package service.impl;

import database.dbbean.ItemInfoBean;
import database.dbbean.TreeInfoBean;
import database.dbdao.*;
import database.util.SQLUtil;
import service.TreeService;
import service.model.TreeModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuwenchuan on 2017/5/20.
 */
public class TreeServiceImpl implements TreeService {
    private TreeInfoDao treeInfoDao;
    private DBStatusInfoDao dbStatusInfoDao;
    private AttachInfoDao attachInfoDao;
    private ItemInfoDao itemInfoDao;
    private ItemTagInfoDao itemTagInfoDao;

    public TreeServiceImpl() {
        treeInfoDao = new TreeInfoDao();
        dbStatusInfoDao = new DBStatusInfoDao();
        attachInfoDao = new AttachInfoDao();
        itemInfoDao = new ItemInfoDao();
        itemTagInfoDao = new ItemTagInfoDao();
    }

    @Override
    public Map<String, TreeModel> getRootTreeInfo() {
        return getTreeInfo(SQLUtil.DR_PID);
    }

    @Override
    public Map<String, TreeModel> getTreeInfo(String treeid) {
        List<TreeInfoBean> treeInfoBeans = treeInfoDao.getItemsByPID(treeid);
        return TreeModel.covertListBeansToMapModels(treeInfoBeans);
    }

    @Override
    public String insertNewTreeInfo(String titile, Integer type, String pid) {
        TreeInfoBean treeInfoBean = new TreeInfoBean();
        Date createDate = new Date(System.currentTimeMillis());
        String id = SQLUtil.generateDatetimeID(createDate);
        treeInfoBean.setId(id);
        treeInfoBean.setPid(pid == null ? SQLUtil.DR_PID : pid);
        treeInfoBean.setType(type);
        treeInfoBean.setCreatetime(createDate);
        treeInfoBean.setModifytime(createDate);
        treeInfoBean.setTitle(titile);
        treeInfoBean.setSequnce(-1);
        treeInfoBean.setStyleid(-1);
        treeInfoDao.createNewTreeInfo(treeInfoBean);
        dbStatusInfoDao.addNewItemInfo(id);
        if (type == SQLUtil.DB_TREE_FILE) {
            ItemInfoBean itemInfoBean = new ItemInfoBean();
            itemInfoBean.setItemid(id);
            itemInfoBean.setTitle(titile);
            itemInfoDao.createNewItemInfo(itemInfoBean);
        }
        return id;
    }

    @Override
    public void updateNewTreeInfo(String id, String title) {
        treeInfoDao.updateTitleInfo(id, title);
        dbStatusInfoDao.changeNewItemInfo(id);
    }

    @Override
    public void deleteTreeInfo(String id) {
        TreeInfoBean treeInfoBean = treeInfoDao.getItemById(id);
        if (treeInfoBean == null)
            return;
        if (treeInfoBean.getType() == SQLUtil.DB_TREE_DIR) {
            //删除该目录下所有信息
            List<TreeInfoBean> treeInfoBeans = treeInfoDao.getItemsByPID(id);
            List<TreeInfoBean> fileChildBeans = new ArrayList<>();
            for (TreeInfoBean childBean : treeInfoBeans) {
                if (childBean.getType() == SQLUtil.DB_TREE_DIR) {
                    deleteTreeInfo(childBean.getId());
                } else {
                    fileChildBeans.add(childBean);
                    dbStatusInfoDao.deleteNewItemInfo(id);
                }
            }
            //批量删除信息，加快SQL执行速度
            if (fileChildBeans.size() > 0) {
                String[] ids = SQLUtil.getItemIDsFromTreeInfoBeanList(fileChildBeans);
                treeInfoDao.deleteItemByID(ids);
                itemInfoDao.deleteItemByIDs(ids);
                itemTagInfoDao.deleteItemTagByItemIDs(ids);
//            attachInfoDao.d
            }
        }
        treeInfoDao.deleteItemById(id);
        dbStatusInfoDao.deleteNewItemInfo(id);
        itemInfoDao.deleteItemById(id);
        itemTagInfoDao.deleteItemTagByItemID(id);
//            attachInfoDao.d
    }

}