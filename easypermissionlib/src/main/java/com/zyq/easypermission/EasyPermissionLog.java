package com.zyq.easypermission;

import android.text.TextUtils;
import android.util.Log;

/**
 * Print log tool
 * 打印日志工具
 *
 * @author Zhang YanQiang
 * @date 2019/6/2　15:02.
 */
public class EasyPermissionLog {

    /**
     * Print log
     * the debug level, EasyPermissionConfig. Info = false without printing
     *
     * 打印log日志
     * info级别，EasyPermissionConfig.DEBUG=false时不打印
     *
     * @param logString
     */
    public static void i(String logString) {
        if (TextUtils.isEmpty(logString)) {
            return;
        }
        if (EasyPermissionConfig.isDebug()) {
            Log.i(EasyPermissionConfig.LOG_TAG, logString);
        }
    }

    /**
     * Print log
     * the debug level, EasyPermissionConfig. Debug = false without printing
     *
     * 打印log日志
     * debug级别，EasyPermissionConfig.DEBUG=false时不打印
     *
     * @param logString
     */
    public static void d(String logString) {
        if (TextUtils.isEmpty(logString)) {
            return;
        }
        if (EasyPermissionConfig.isDebug()) {
            Log.d(EasyPermissionConfig.LOG_TAG, logString);
        }
    }

    /**
     * 打印log日志
     *  error级别，不管是不是debug模式，都打印
     * 打印log日志
     * error级别，不管是不是debug模式，都打印
     *
     * @param logString
     */
    public static void e(String logString) {
        if (TextUtils.isEmpty(logString)) {
            return;
        }
        Log.e(EasyPermissionConfig.LOG_TAG, logString);
    }
}
