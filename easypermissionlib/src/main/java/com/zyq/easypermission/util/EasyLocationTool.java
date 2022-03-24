package com.zyq.easypermission.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * Location service management tool
 * 定位服务管理工具
 * @author by Zhang YanQiang
 * @date 2022/3/22.
 */
public class EasyLocationTool {

    /**
     * Check whether the location service is enabled
     * 判断定位服务是否开启
     * @param
     * @return true 表示开启 true-open
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * The page for setting location information is displayed
     * 直接跳转至位置信息设置界面
     */
    public static void gotoAppSettings(Activity context) {
        Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

}
