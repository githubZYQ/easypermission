package com.zyq.permissiondemo;

import android.app.Application;
import android.view.Gravity;

import com.zyq.easypermission.EasyPermissionHelper;
import com.zyq.easypermission.bean.EasyAppSettingDialogStyle;
import com.zyq.easypermission.bean.EasyTopAlertStyle;

/**
 * @author by Zhang YanQiang
 * @date 2022/3/21.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //首次使用权限申请之前完成初始化，建议放在Application onCreate()中完成
        EasyPermissionHelper.getInstance().init(this);

        //设置被禁止时说明弹窗的样式，可以不设置 使用默认的STYLE_DEFAULT,STYLE_SYSTEM
        //默认样式，可以不用设置
//        EasyPermissionHelper.getInstance().setDialogStyle(
//                new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_DEFAULT));
        //系统样式
//        EasyPermissionHelper.getInstance().setDialogStyle(
//                new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_SYSTEM));
        //自定义样式
        EasyPermissionHelper.getInstance().setDialogStyle(
                new EasyAppSettingDialogStyle(EasyAppSettingDialogStyle.DialogStyle.STYLE_CUSTOM)
                        .setTitleGravity(Gravity.CENTER)//设置居中
                        .setTitleSize(17)//设置标题样式
                        .setTitleColor("#333333")
                        .setMessageSize(14)//设置内容样式
                        .setMessageColor("#666666")
                        .setButtonTextSize(14)//设置按钮样式
                        .setButtonThemeColor("#FF0000")
                        .setCancelText("取消")//设置按钮文本
                        .setConfirmText("去打开"));

        //设置申请权限时顶部提示框样式，可以不设置 使用默认的STYLE_DEFAULT
        //默认样式，可以不用设置
        EasyPermissionHelper.getInstance().setTopAlertStyle(
                new EasyTopAlertStyle(EasyTopAlertStyle.AlertStyle.STYLE_DEFAULT));
        //自定义样式
//        EasyPermissionHelper.getInstance().setTopAlertStyle(
//                new EasyTopAlertStyle(EasyTopAlertStyle.AlertStyle.STYLE_CUSTOM)
//                        .setTitleGravity(Gravity.LEFT)//默认居左
//                        .setTitleSize(16)//设置标题样式，默认16sp
//                        .setTitleColor("#333333")
//                        .setMessageSize(14)//设置内容样式，默认14sp
//                        .setMessageColor("#333333")
//                        .setBackgroundColor("#FFFFFF")//设置背景色，默认白色
//                        .setBackgroundRadius(8)//设置背景圆角弧度，默认8dp
//                        .setBackgroundElevation(6)//设置背景阴影范围，默认6dp
//                        .setTopMargin(10)//设置距离顶部标题栏间距，默认10dp
//                        .setSideMargin(10));//设置距离屏幕两边宽度，默认10dp
    }
}
