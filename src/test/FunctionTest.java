package test;

import ui.MainWindowUI;
import ui.treeui.CodeTree;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by wuwenchuan on 2017/5/15.
 */
public class FunctionTest {

    public static void main(String[] args) {
//        CodeStyleFactory.test();
//        String str = "\"import java.util.regex;\"";
//        String reg = "(import|package)\\s(.+)";
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.find()) {// matcher.matchers() {
//            String fqdnId = matcher.group();
//            System.out.println(fqdnId);
//        }

//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("javascript");
//
//        String jsFileName = "expression.js";   // 读取js文件
//
//        FileReader reader = null;   // 执行指定脚本
//        try {
//            reader = new FileReader(jsFileName);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            engine.eval(reader);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
//
//        if(engine instanceof Invocable) {
//            Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数
//
//// c = merge(2, 3);
//
//            Double c = null;
//            try {
//                c = (Double)invoke.invokeFunction("merge", 2, 3);
//            } catch (ScriptException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("c = " + c);
//        }
//
//        try {
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        TreeInfoDao treeInfoDao = new TreeInfoDao();
////        TreeInfoBean bean = new TreeInfoBean();
////        bean.setId("wuwenchuan");
////        bean.setCreatetime(new Date(System.currentTimeMillis()));
////        treeInfoDao.createNewTreeInfo(bean);
//        TreeInfoBean treeInfoBean = treeInfoDao.getItemById("wuwenchuan");
//        if (treeInfoBean != null)
//            System.out.println(treeInfoBean.getCreatetime());

//        ItemService itemService = new ItemServiceImpl();
//        TreeService treeService = new TreeServiceImpl();
//        SearchService searchService = new SearchServiceImpl();

//        String id = treeService.insertNewTreeInfo("wuwenchuan", 1, null);
//        System.out.println(id);
//        ItemModel itemModel = itemService.getItemInfo(id);
//        System.out.println(itemModel.getItemID());
//        System.out.println(itemModel.getTitle());
//
//        treeService.deleteTreeInfo("201705213424219");

//        System.out.println("========================================");
//        Map<String, TreeModel> treeModelMap = searchService.searchTitle("wu");
//        for (String iterator: treeModelMap.keySet()) {
//            System.out.println(iterator);
//        }


//        JFrame f = new JFrame("JList");
//        Container contentPane = f.getContentPane();
//        /*
//		 * 由于我们在DataModelodel类中继承AbstractListModel,并实作了getElementAt()与getSize()方法
//		 * ，因此我们可以由DataModel类产生一个ListModel的实体来。
//		 */
//        ListModel mode = new DataModel();
//        JList list = new JList(mode);// 利用ListModel建立一个JList.
//        list.setVisibleRowCount(5);// 设置程序一打开时所能看到的数据项个数。
//        list.setBorder(BorderFactory.createTitledBorder("你最喜欢到哪个国家玩呢?"));
//
//        contentPane.add(new JScrollPane(list));
//        f.pack();
//		/*
//		 * 当程序要show出list时，系统会先自动调用getSize()方法，看看这个list长度有多少;然后再调
//		 * 用setVisibleRowCount()方
//		 * 法,看要一次输出几笔数据;最后调用getElementAt()方法，将list中的项目值(item
//		 * )填入list中。读者若还不太清楚，可直接
//		 * 在getSize()与getElementAt()方法中个别加入System.out.println
//		 * ("size");与System.out.println("element")叙述，就可以 在dos
//		 * console清楚看出整个显示list调用的过程。
//		 */
//        f.show();
//        f.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });


//        System.out.println(System.getProperty("user.dir"));

//        JFrame jFrame = new JFrame();
//        jFrame.add(new CodeTree());
//        jFrame.setVisible(true);
//        jFrame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        MainWindowUI mainWindowUI = new MainWindowUI();


    }

    /**
     * 由于我们在下面程序中继承AbstractListModel抽象类，因此我们分别在下面程序中实现了getElementAt()与getSize()方法。
     **/
    static class DataModel extends AbstractListModel {
        String[] s = {"美国", "越南", "大陆", "英国", "法国", "大陆", "意大利", "澳洲"};

        public Object getElementAt(int index) {// getxElementAt()方法中的参数index,系统会自动由0开始计算,不过要自己作累加
            // 的操作.
            return (index + 1) + "." + s[index++];
        }

        public int getSize() {
            return s.length;
        }
    }
}
