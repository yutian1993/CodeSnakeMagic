package util;

import a.j.k.a.P;

/**
 * Created by yutian on 2017/5/13.
 */
public class LogUtil {
    //Log信息级别设置
    private static final boolean G_INFOR = false;
    private static final boolean G_ERROR = false;

    /**
     * 输出日志信息
     * @param info
     */
    public static void infor(String info) {
        if (G_INFOR) {
            System.out.println(info);
        }
    }

    /**
     * 输出日志信息
     * @param tag
     * @param info
     */
    public static void infor(String tag, String info) {
        if (G_INFOR) {
            System.out.println(tag + "   " + info);
        }
    }

    /**
     * 输出错误日志信息
     * @param tag
     * @param err
     */
    public static void error(String tag, String err) {
        if (G_ERROR) {
            System.err.println(tag + "   " + err);
        }
    }

    /**
     * 输出错误日志信息
     * @param err
     */
    public static void error(String err) {
        if (G_ERROR) {
            System.err.println(err);
        }
    }
}
