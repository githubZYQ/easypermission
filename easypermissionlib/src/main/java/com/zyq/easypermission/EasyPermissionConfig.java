package com.zyq.easypermission;

/**
 * @author Zhang YanQiang
 * @date 2019/6/2　14:58.
 */
public class EasyPermissionConfig {
    /**
     * Print the tag for the log
     * 打印日志的tag
     */
    public static final String LOG_TAG = "EasyPermissionLog";
    /**
     * Debug mode
     * 是否是debug模式
     */
    private static boolean DEBUG = false;

    public static boolean isDebug() {
        return DEBUG;
    }

    /**
     * isDebug
     * @return
     */
    public static void setDebug(boolean isDebug) {
        DEBUG = isDebug;
    }
}
