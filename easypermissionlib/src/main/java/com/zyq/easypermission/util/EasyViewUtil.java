package com.zyq.easypermission.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class EasyViewUtil {
    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        EasyViewUtil.getDisplay(context).getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        EasyViewUtil.getDisplay(context).getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 是否是长屏幕
     *
     * @param context
     * @return
     */
    public static boolean isHighScreen(Context context) {
        try {
            float height = (float)getScreenHeight(context);
            float width = (float)getScreenWidth(context);
            //获取长宽比
            float ratio = height / width;
            return ratio > 1.9;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 最后一次点击的时间
     */
    private static long last_time = 0;
    /**
     * 设置连续点击间隔
     */
    public static  void setIntervalClick(){

        long current_time = System.currentTimeMillis();
        long d_time = current_time - last_time;
        if (d_time <= 150) {
            return;
        } else {
            last_time = current_time;
        }
    }

    /**
     * 兼容API获取屏幕信息
     * @param context
     * @return
     */
    public static Display getDisplay(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return getDisplayApiR(context);
        }else {
            return getDisplayApiL(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Display getDisplayApiL(Context context){
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    private static Display getDisplayApiR(Context context){
        return context.getDisplay();
    }

}
