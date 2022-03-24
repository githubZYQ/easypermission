package com.zyq.easypermission.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Hover window permission management
 * 悬浮窗权限管理
 * @author by Zhang YanQiang
 * @date 2022/3/22.
 */
public class EasyFloatWindowTool {
    /**
     * Whether you have suspension window permission
     * 是否有悬浮窗权限
     *
     * @return
     */
    public static boolean isFloatWindowEnabled(Context context) {
        //在6.0以前的系统版本，悬浮窗权限是默认开启的，直接使用即可
        //In system versions prior to 6.0, hover window permission is enabled by default and can be used directly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return true;
        }
    }
    /**
     * Go set hover window permissions
     * 去设置悬浮窗权限
     *
     * @return
     */
    public static void gotoAppSettings(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
