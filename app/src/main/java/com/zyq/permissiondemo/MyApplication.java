package com.zyq.permissiondemo;

import android.app.Application;
import android.view.Gravity;

import com.zyq.easypermission.EasyPermissionHelper;
import com.zyq.easypermission.bean.EasyAppSettingDialogStyle;

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
        //设置被禁止时说明弹窗的样式，可以不设置使用默认的STYLE_DEFAULT,STYLE_SYSTEM
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
                        .setTitleSize(17)//设置标题
                        .setTitleColor("#333333")
                        .setMessageSize(14)//设置内容
                        .setMessageColor("#666666")
                        .setButtonTextSize(14)//设置按钮
                        .setButtonThemeColor("#FF0000")
                        .setCancelText("取消")//设置文本
                        .setConfirmText("去打开"));
    }
}
