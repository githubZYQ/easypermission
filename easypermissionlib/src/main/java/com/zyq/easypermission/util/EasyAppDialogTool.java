package com.zyq.easypermission.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;
import com.zyq.easypermission.EasyPermissionLog;
import com.zyq.easypermission.R;
import com.zyq.easypermission.bean.EasyAppSettingDialogStyle;
import com.zyq.easypermission.bean.EasyTopAlertStyle;
import com.zyq.easypermission.bean.PermissionAlertInfo;

/**
 * App sets the popover controller
 * app设置弹窗控制器
 *
 * @author by Zhang YanQiang
 * @date 2022/3/23.
 */
public class EasyAppDialogTool {
    /**
     * Permissions are disabled from pop-ups(default popover style)
     * 权限被禁止弹窗(默认弹窗样式)
     */
    public static void showDialogWithDefaultStyle(@Nullable final EasyPermission easyPermission) {
        final int requestCode = easyPermission.getRequestCode();
        PermissionAlertInfo mAlertInfo = easyPermission.getAlertInfo();
        if (mAlertInfo == null || TextUtils.isEmpty(mAlertInfo.alertMessage)) {
            return;
        }
        final Activity topActivity = EasyPermissionHelper.getInstance().getTopActivity();
        if (topActivity == null) return;
        View contentView = View.inflate(topActivity, R.layout.dialog_info_middle, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(topActivity, R.style.theme_alertdialog_transparent);
        builder.setView(contentView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        View.OnClickListener cancelClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(easyPermission.getResult() != null){
                    easyPermission.onPermissionsDismiss();
                }
            }
        };
        View.OnClickListener confirmClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EasyPermissionHelper.getInstance().goToAppSettings(requestCode);
            }
        };
        //按钮点击
        EasyAlphaTouchListener alphaTouchListener = new EasyAlphaTouchListener();
        contentView.findViewById(R.id.btnCancel).setOnTouchListener(alphaTouchListener);
        contentView.findViewById(R.id.btnConfirm).setOnTouchListener(alphaTouchListener);

        ((TextView) contentView.findViewById(R.id.tvAlertTitle)).setText(mAlertInfo.alertTitle);
        ((TextView) contentView.findViewById(R.id.tvAlertMessage)).setText(mAlertInfo.alertMessage);
        contentView.findViewById(R.id.btnCancel).setOnClickListener(cancelClick);
        contentView.findViewById(R.id.btnConfirm).setOnClickListener(confirmClick);
        dialog.show();
    }

    /**
     * Permissions forbidden popover (system popover style)
     * 权限被禁止弹窗(系统弹窗样式)
     */
    public static void showDialogWithSystemStyle(@Nullable final EasyPermission easyPermission) {
        final int requestCode = easyPermission.getRequestCode();
        PermissionAlertInfo mAlertInfo = easyPermission.getAlertInfo();
        if (mAlertInfo == null || TextUtils.isEmpty(mAlertInfo.alertMessage)) {
            return;
        }
        final Activity topActivity = EasyPermissionHelper.getInstance().getTopActivity();
        if (topActivity == null) return;
        String title = mAlertInfo.alertTitle;
        String message = mAlertInfo.alertMessage;
        String confirm = topActivity.getString(R.string.setting_alert_button_confirm);
        String cancel = topActivity.getString(R.string.setting_alert_button_cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(topActivity);
        builder.setTitle(title);
        StringBuilder msg = new StringBuilder();
        msg.append("\n");
        msg.append(message);
        builder.setMessage(msg.toString());
        builder.setCancelable(false);
        builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EasyPermissionHelper.getInstance().goToAppSettings(requestCode);
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(easyPermission.getResult() != null){
                    easyPermission.onPermissionsDismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Permissions are disabled from pop-ups(default popover style)
     * 权限被禁止弹窗(自定义弹窗样式)
     */
    public static void showDialogWithCustomStyle(@Nullable final EasyPermission easyPermission, EasyAppSettingDialogStyle mStyle) {
        final int requestCode = easyPermission.getRequestCode();
        PermissionAlertInfo mAlertInfo = easyPermission.getAlertInfo();
        if (mAlertInfo == null || TextUtils.isEmpty(mAlertInfo.alertMessage)) {
            return;
        }
        final Activity topActivity = EasyPermissionHelper.getInstance().getTopActivity();
        if (topActivity == null) return;
        View contentView = View.inflate(topActivity, R.layout.dialog_info_middle, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(topActivity, R.style.theme_alertdialog_transparent);
        builder.setView(contentView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        View.OnClickListener cancelClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(easyPermission.getResult() != null){
                    easyPermission.onPermissionsDismiss();
                }
            }
        };
        View.OnClickListener confirmClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EasyPermissionHelper.getInstance().goToAppSettings(requestCode);
            }
        };
        //按钮点击
        EasyAlphaTouchListener alphaTouchListener = new EasyAlphaTouchListener();
        contentView.findViewById(R.id.btnCancel).setOnTouchListener(alphaTouchListener);
        contentView.findViewById(R.id.btnConfirm).setOnTouchListener(alphaTouchListener);
        //获取控件
        TextView title = contentView.findViewById(R.id.tvAlertTitle);
        TextView message = contentView.findViewById(R.id.tvAlertMessage);
        TextView btnCancel = contentView.findViewById(R.id.btnCancel);
        TextView btnConfirm = contentView.findViewById(R.id.btnConfirm);

        title.setText(mAlertInfo.alertTitle);
        message.setText(mAlertInfo.alertMessage);
        btnCancel.setOnClickListener(cancelClick);
        btnConfirm.setOnClickListener(confirmClick);
        dialog.show();
        if (mStyle == null) {
            return;
        }
        //设置自定义属性
        //Set custom properties
        title.setGravity(mStyle.getTitleGravity());
        title.setTextSize(mStyle.getTitleSize());
        message.setTextSize(mStyle.getMessageSize());
        btnCancel.setTextSize(mStyle.getButtonTextSize());
        btnConfirm.setTextSize(mStyle.getButtonTextSize());
        //按钮文本
        btnCancel.setText(mStyle.getCancelText(topActivity));
        btnConfirm.setText(mStyle.getConfirmText(topActivity));
        try {
            title.setTextColor(Color.parseColor(mStyle.getTitleColor()));
            message.setTextColor(Color.parseColor(mStyle.getMessageColor()));
            String buttonColor = mStyle.getButtonThemeColor();
            String buttonFillColor = "#00000000";
            btnCancel.setTextColor(Color.parseColor(buttonColor));
            btnCancel.setBackground(
                    EasyShapeUtil.createShape(
                            EasyViewUtil.dip2px(topActivity, 1),
                            EasyViewUtil.dip2px(topActivity, 20),
                            GradientDrawable.RECTANGLE,
                            buttonColor, buttonFillColor
                    ));
            btnConfirm.setBackground(
                    EasyShapeUtil.createShape(
                            0,
                            EasyViewUtil.dip2px(topActivity, 20),
                            GradientDrawable.RECTANGLE,
                            buttonColor, buttonColor
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permission request top alert
     * 权限申请顶部提醒
     */
    public static Dialog showTopAlertStyle(@Nullable final PermissionAlertInfo mAlertInfo, EasyTopAlertStyle mStyle) {
        final Activity topActivity = EasyPermissionHelper.getInstance().getTopActivity();
        if (topActivity == null) return null;
        EasyPermissionLog.d("showAlert："+topActivity.getLocalClassName());
        View alertView = View.inflate(topActivity, R.layout.alert_info_top, null);
        Dialog alertDialog = new Dialog(topActivity, R.style.theme_alertdialog_transparent);
        alertDialog.setOwnerActivity(topActivity);
        TextView titleView = alertView.findViewById(R.id.tvAlertTitle);
        titleView.setText(mAlertInfo.alertTitle);
        TextView messageView = alertView.findViewById(R.id.tvAlertMessage);
        messageView.setText(mAlertInfo.alertMessage);
        ViewGroup alertContainer = alertView.findViewById(R.id.alertContainer);
        Window win = alertDialog.getWindow();
//        int with = EasyViewUtil.getScreenWidth(topActivity);
//        int side = EasyViewUtil.dip2px(topActivity,5);
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        win.setAttributes(lp);
        alertDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(topActivity.getResources(), (Bitmap) null));
        alertDialog.setContentView(alertView);
        alertDialog.show();
        //设置自定义属性
        if(mStyle != null && mStyle.getStyle() == EasyTopAlertStyle.AlertStyle.STYLE_CUSTOM){
            try {
                titleView.setGravity(mStyle.getTitleGravity());
                titleView.setTextSize(mStyle.getTitleSize());
                messageView.setTextSize(mStyle.getMessageSize());
                titleView.setTextColor(Color.parseColor(mStyle.getTitleColor()));
                messageView.setTextColor(Color.parseColor(mStyle.getMessageColor()));
                //设置背景样式
                String bgColor = mStyle.getBackgroundColor();
                alertContainer.setBackground(
                        EasyShapeUtil.createShape(
                                0,
                                EasyViewUtil.dip2px(topActivity, mStyle.getBackgroundRadius()),
                                GradientDrawable.RECTANGLE,
                                bgColor, bgColor
                        ));
                //设置间距
                int sideMargin = EasyViewUtil.dip2px(topActivity,mStyle.getSideMargin());
                int topMargin = EasyViewUtil.dip2px(topActivity,mStyle.getTopMargin());
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) alertContainer.getLayoutParams();
                params.leftMargin = sideMargin;
                params.rightMargin = sideMargin;
                params.topMargin = topMargin;
                params.bottomMargin = topMargin;
                //设置阴影
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    float elevation = EasyViewUtil.dip2px(topActivity, mStyle.getBackgroundElevation());
                    alertContainer.setElevation(elevation);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return alertDialog;
    }
}
