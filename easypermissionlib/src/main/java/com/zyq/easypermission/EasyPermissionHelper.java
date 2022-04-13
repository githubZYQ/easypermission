package com.zyq.easypermission;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tencent.mmkv.MMKV;
import com.zyq.easypermission.bean.EasyAppSettingDialogStyle;
import com.zyq.easypermission.bean.EasyTopAlertStyle;
import com.zyq.easypermission.bean.PermissionAlertInfo;
import com.zyq.easypermission.util.EasyAppDialogTool;
import com.zyq.easypermission.util.EasyCacheData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Specific implementation of permission application function
 * 权限申请功能的具体实现
 *
 * @author Zhang YanQiang
 * @date 2019/6/2　15:45.
 */
public class EasyPermissionHelper {

    /**
     * The code for requesting permission, default 0, does not handle the result of the request
     * 请求权限的code，默认0，不处理请求结果
     */
    private static final int REQUESTCODE_DEFAULT = 0;
    /**
     * Singleton object mHelper
     * 单例对象 mHelper
     */
    private static volatile EasyPermissionHelper mHelper;
    /**
     * The lastEasyPermission
     * 最后一个权限请求对象
     */
    private volatile EasyPermission mCurrentEasyPermission;
    /**
     * The Application
     * 当前application
     */
    private Application mApplication;

    /**
     * The top activity
     * 当前最顶部的activity
     * 为避免内存泄漏使用弱引用
     */
    private volatile WeakReference<Activity> mCurrentActivity;

    /**
     * ResultMap, a singleton object
     * 单例对象 resultMap
     */
    private static volatile SparseArray<EasyPermissionResult> mResults;
    /**
     *  The record has been initialized
     * 已经完成初始化
     */
    public static boolean alreadyInitialized;

    /**
     * Set the popover style
     * 设置弹窗样式
     */
    private EasyAppSettingDialogStyle mDialogStyle;
    /**
     * Set the top prompt style
     * 设置顶部提示样式
     */
    private EasyTopAlertStyle mTopAlertStyle;

    /**
     * Privatize constructors
     * 构造函数私有化
     */
    private EasyPermissionHelper() {
    }

    /**
     * Constructor privatization
     * 获取单例对象 mHelper
     *
     * @return
     */
    public static EasyPermissionHelper getInstance() {
        if (mHelper == null) {
            synchronized (EasyPermissionHelper.class) {
                if (mHelper == null) {
                    mHelper = new EasyPermissionHelper();
                }
            }
        }
        return mHelper;
    }

    /**
     * Complete initialization
     * 1. Initialize the cache
     * 2. Start listening for activity changes
     * 完成初始化
     * 1、初始化缓存
     * 2、开始监听activity变化
     *
     * @param application
     */
    public void init(@NonNull Application application) {
        this.mApplication = application;
        //初始化mmkv缓存
        MMKV.initialize(mApplication.getApplicationContext());
        alreadyInitialized = true;
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                EasyPermissionLog.d(activity.getLocalClassName()+":"+"Created");
                updateTopActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                EasyPermissionLog.d(activity.getLocalClassName()+":"+"Started");
                updateTopActivity(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                EasyPermissionLog.i(activity.getLocalClassName() + ":" + "Resumed");
                updateTopActivity(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                EasyPermissionLog.i(activity.getLocalClassName()+":"+"Paused");
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                EasyPermissionLog.i(activity.getLocalClassName()+":"+"Stopped");
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                EasyPermissionLog.i(activity.getLocalClassName()+":"+"SaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                EasyPermissionLog.i(activity.getLocalClassName()+":"+"Destroyed");
                dismissAlert(activity);
            }
        });
    }

    /**
     * Set the top prompt style
     * 设置顶部提示样式，将影响 {@link #showAlert(PermissionAlertInfo)}
     * @param mTopAlertStyle {@link EasyTopAlertStyle}
     * 
     */
    public void setTopAlertStyle(EasyTopAlertStyle mTopAlertStyle) {
        this.mTopAlertStyle = mTopAlertStyle;
    }

    /**
     * Set the popover style
     * 设置弹窗样式，将影响 {@link #showDialog(EasyPermission)}
     * @param mDialogStyle {@link EasyAppSettingDialogStyle}
     *
     */
    public void setDialogStyle(EasyAppSettingDialogStyle mDialogStyle) {
        this.mDialogStyle = mDialogStyle;
    }

    /**
     * To get the Context,Preferentially return Activity, if not mApplication
     * 获取 Context
     * 优先返回 Activity，如果没有返回mApplication
     */
    private Context getContext() {
        Activity topActivity = getTopActivity();
        Context context = topActivity != null ? topActivity : mApplication;
        if(context == null){
            throw new RuntimeException("请先完成初始化：init(application)");
        }
        return context;
    }

    /**
     * Get the top Activity
     * 获取最顶层 Activity
     */
    public Activity getTopActivity() {
        Activity topActivity = mCurrentActivity != null ? mCurrentActivity.get() : null;
        if(topActivity == null){
            throw new RuntimeException("需要获取activity：请先完成初始化：init(application)");
        }
        return topActivity;
    }

    /**
     * Update the top Activity
     * 更新顶层Activity
     * @param activity
     */
    public void updateTopActivity(Activity activity){
        mCurrentActivity = new WeakReference<>(activity);
    }


    /**
     * Get the singleton mResults
     * 获取单例对象 mResults
     *
     * @return
     */
    private static SparseArray<EasyPermissionResult> getResults() {
        if (mResults == null) {
            synchronized (EasyPermissionHelper.class) {
                if (mResults == null) {
                    mResults = new SparseArray<>(1);
                }
            }
        }
        return mResults;
    }

    /**
     * Check the current permissions
     * 检查当前的权限情况
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(@NonNull String... permissions) {
        //6.0以上才需要动态检查
        // above 6.0, dynamic check is required
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getTopActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                // False is returned as long as a permission is not granted
                return false;
            }
        }
        return true;
    }


    /**
     * Request permission but no permission result callback is required
     * 申请权限但是不需要权限结果回调
     *
     * @param requestCode 请求回调code
     * @param mAlertInfo  提示信息
     * @param permissions
     * @return
     */
    protected void requestPermission(int requestCode, PermissionAlertInfo mAlertInfo, @NonNull String... permissions) {
        //显示提示信息
        showAlert(mAlertInfo);
        this.doRequestPermission(requestCode, permissions);
    }


    /**
     * Request permission but no permission result callback is required
     * 申请权限但是不需要权限结果回调
     *
     * @param easyPermission
     * @return
     */
    protected void requestPermission(@NonNull EasyPermission easyPermission) {
        //更新请求的activity
        if(easyPermission.getContext() != null){
            updateTopActivity(easyPermission.getContext());
        }
//        this.mCurrentEasyPermission = easyPermission;
        if (easyPermission.getResult() == null) {
            //无需回调
            requestPermission(easyPermission.getAlertInfo(), easyPermission.getPerms());
        } else {
            requestPermission(easyPermission.getRequestCode(),
                    easyPermission.getResult(), easyPermission.getAlertInfo(), easyPermission.getPerms());
        }
    }

    /**
     * Request permission but no permission result callback is required
     * 申请权限但是不需要权限结果回调
     *
     * @param mAlertInfo  提示信息
     * @param permissions
     * @return
     */
    protected void requestPermission(PermissionAlertInfo mAlertInfo, @NonNull String... permissions) {
        this.requestPermission(REQUESTCODE_DEFAULT, mAlertInfo, permissions);
    }


    /**
     * To do request permission
     * 执行申请权限
     *
     * @param requestCode
     * @param permissions
     * @return
     */
    protected void doRequestPermission(int requestCode, @NonNull String... permissions) {
        ActivityCompat.requestPermissions(getTopActivity(),
                permissions, requestCode);
    }

    /**
     * Requesting permission requires a callback of the permission result
     * 申请权限 需要权限结果回调
     *
     * @param permissions
     * @return
     */
    protected void requestPermission(int requestCode, EasyPermissionResult result, PermissionAlertInfo mAlertInfo, @NonNull String... permissions) {
        //保存结果回调
        //Save the result callback
        if (requestCode != REQUESTCODE_DEFAULT || result != null) {
            getResults().append(requestCode, result);
        }
        this.requestPermission(requestCode, mAlertInfo, permissions);
    }

    /**
     * 顶部提示框
     */
    private Dialog alertDialog;

    /**
     * Display permission description information
     * 权限说明信息展示
     */
    private void showAlert(PermissionAlertInfo mAlertInfo) {
        if (mAlertInfo == null || TextUtils.isEmpty(mAlertInfo.alertMessage)) {
            return;
        }
        //避免弹出多个
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        Activity topActivity = getTopActivity();
        if(topActivity == null) return;
        EasyPermissionLog.d("showAlert："+topActivity.getLocalClassName());
        alertDialog = EasyAppDialogTool.showTopAlertStyle(mAlertInfo,mTopAlertStyle);

//
//        View alertView = View.inflate(topActivity, R.layout.alert_info_top, null);
//        alertDialog = new Dialog(topActivity, R.style.theme_alertdialog_transparent);
//        alertDialog.setOwnerActivity(topActivity);
//        TextView titleView = alertView.findViewById(R.id.tvAlertTitle);
//        titleView.setText(mAlertInfo.alertTitle);
//        TextView messageView = alertView.findViewById(R.id.tvAlertMessage);
//        messageView.setText(mAlertInfo.alertMessage);
//        Window win = alertDialog.getWindow();
////        int with = EasyViewUtil.getScreenWidth(topActivity);
////        int side = EasyViewUtil.dip2px(topActivity,5);
//        win.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.TOP;
//        win.setAttributes(lp);
//        alertDialog.getWindow().setBackgroundDrawable(new BitmapDrawable(topActivity.getResources(), (Bitmap) null));
//        alertDialog.setContentView(alertView);
//        alertDialog.show();
    }

    /**
     * When an activity needs to be destroyed, its attached dialog also needs to be destroyed
     * activity需要销毁时，依附的dialog也需要销毁
     */
    private void dismissAlert(Activity activity) {
        if (alertDialog != null && alertDialog.isShowing()) {
            EasyPermissionLog.d(" dismissAlert:activity："+activity.getLocalClassName());
            EasyPermissionLog.d(" dismissAlert:getOwnerActivity："+alertDialog.getOwnerActivity().getLocalClassName());
            boolean isCurrentAlert = activity.equals(alertDialog.getOwnerActivity());
            if(isCurrentAlert){
                alertDialog.dismiss();
            }
        }
    }

    /**
     * Hide the instructions when the authorization result is called back
     * 授权结果回调时，隐藏说明提示
     */
    private void dismissAlert() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
    /**
     * Permissions are disabled from pop-ups
     * 权限被禁止弹窗
     */
    private void showDialog(EasyPermission easyPermission) {
        if(mDialogStyle == null){
            //默认样式
            //The default styles
            EasyAppDialogTool.showDialogWithDefaultStyle(easyPermission);
        }else {
            switch (mDialogStyle.getStyle()){
                case STYLE_CUSTOM:
                    //自定义样式
                    //Custom styles
                    EasyAppDialogTool.showDialogWithCustomStyle(easyPermission,mDialogStyle);
                    break;
                case STYLE_SYSTEM:
                    //系统样式
                    //System style
                    EasyAppDialogTool.showDialogWithSystemStyle(easyPermission);
                    break;
                default:
                    //默认样式
                    //The default styles
                    EasyAppDialogTool.showDialogWithDefaultStyle(easyPermission);
                    break;
            }
        }
    }

    /**
     * After requesting a callback, you can determine whether a second request has been denied
     * The original intention of Google is:
     * 1,If you have not applied for permission, just apply, so return false.
     * 2. If the application is rejected by the user, you will prompt the user, so return true;
     * 3. If the user chooses to reject the application and does not prompt the user any more,
     * then you should not apply or prompt the user, so return false.
     * 4. It has been allowed. No application or prompt is required, so return false.
     * So the use of pure shouldShowRequestPermissionRationale to do judgment,
     * it is no use, can only be used request permission again after correction.
     * <p>
     * 在请求回调后可以判断是否被拒绝再次请求
     * Google的原意是：
     * 1，没有申请过权限，申请就是了，所以返回false；
     * 2，申请了用户拒绝了，那你就要提示用户了，所以返回true；
     * 3，用户选择了拒绝并且不再提示，那你也不要申请了，也不要提示用户了，所以返回false；
     * 4，已经允许了，不需要申请也不需要提示，所以返回false；
     * 所以单纯的使用shouldShowRequestPermissionRationale去做什么判断，是没用的，只能在请求权限回调后再使用。
     *
     * @param permission
     */
    private boolean shouldShowRequestPermissionRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getTopActivity(), permission);
    }

    /**
     * you can determine whether a second request has been denied
     * <p>
     * 可以判断是否被拒绝再次请求
     *
     * @param permissions
     */
    public boolean hasBeanDismissAsk(String... permissions) {
        boolean tempDismissAsk = false;
        for (String permission : permissions) {
            //读取本地存储的被拒绝权限
            tempDismissAsk = EasyCacheData.getInstance().getBoolean(permission);
            EasyPermissionLog.d("haveBeanDismissAsk：" + tempDismissAsk);
            if (tempDismissAsk) return true;
        }
        return false;
    }

    /**
     * Update forbidden permission information
     * 更新被禁止的权限信息
     *
     * @param permissions 权限
     * @param state       true被禁止，false不被禁止可以询问
     */
    protected void updateDismissState(String[] permissions, boolean state) {
        for (String permission : permissions) {
            updateDismissState(permission, state);
        }
    }

    /**
     * Update forbidden permission information
     * 更新被禁止的权限信息
     *
     * @param permission 权限
     * @param permission true被禁止，false不被禁止可以询问
     */
    protected void updateDismissState(String permission, boolean state) {
        EasyCacheData.getInstance().save(permission, state);
    }

    /**
     * Denied permission and forbidden to ask
     * <p>
     * 被拒绝请求权限，且禁止询问
     *
     * @param permissions
     * @return
     */
    protected boolean hasDismissedAsk(@NonNull String... permissions) {
        return !hasPermission(permissions)
                && hasBeanDismissAsk(permissions);
    }

    /**
     * Open the APP's permission details Settings
     * Receive the permission callback result of requestCode in the onActivityResult, and re-execute the permission related logic
     * 打开 APP 的权限详情设置
     * 在onActivityResult中接收requestCode的权限回调结果，重新执行权限相关逻辑
     */
    protected void openAppDetails(@NonNull EasyPermission easyPermission) {
        this.mCurrentEasyPermission = easyPermission;
        //更新请求的activity
        if(easyPermission.getContext() != null){
            updateTopActivity(easyPermission.getContext());
        }
        showDialog(easyPermission);
    }

    /**
     * Skip To the Settings
     * 跳转设置页
     */
    public void goToAppSettings(@NonNull EasyPermission easyPermission) {
        //更新请求的activity
        if(easyPermission.getContext() != null){
            updateTopActivity(easyPermission.getContext());
        }
        this.mCurrentEasyPermission = easyPermission;
        goToAppSettings(easyPermission.getRequestCode());
    }

    /**
     * Skip To the Settings
     * 跳转设置页
     *
     * @param requestCode
     */
    public void goToAppSettings(int requestCode) {
        Activity topActivity = getTopActivity();
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + topActivity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        topActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * Handle permissions result callbacks
     * In the activity of need to access the callback rewrite onRequestPermissionsResult calls to this function
     * <p>
     * 处理权限结果回调
     * 在需要权限回调的activity中重写onRequestPermissionsResult调用此函数
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults, @NonNull Activity context) {
        dismissAlert();
        EasyPermissionLog.d("权限结果回调，隐藏弹窗");
        // no callback required
        //不需要回调的情况
        if (requestCode == REQUESTCODE_DEFAULT) {
            return;
        }
        if (mResults == null || mResults.size() == 0) {
            return;
        }
        EasyPermissionResult permissionResult = mResults.get(requestCode);
        if (permissionResult == null) {
            return;
        }
        if (permissions.length <= 0) {
            return;
        }
        //Here are the situations where a callback is required
        //以下是需要回调的情况

        //grantResults is empty to indicate that permissions are not being asked but the request is being invoked
        //grantResults为空表示权限被禁止询问了但是还调用了申请请求
        if (grantResults == null) {
            //Do not process onpermissionsseparation when true
            //为true不处理onPermissionsDismiss
            boolean onPermissionsDismiss = false;
            //Permissions are forbidden to ask
            //权限被禁止询问
            EasyPermissionLog.d("权限已经被禁止询问");
            //保存被禁止的权限
            updateDismissState(permissions, true);
            onPermissionsDismiss = permissionResult.onDismissAsk(requestCode, Arrays.asList(permissions),true);
            //Permission not passed
            //权限未通过
            if (!onPermissionsDismiss) {
                permissionResult.onPermissionsDismiss(requestCode, Arrays.asList(permissions));
            }
            return;
        }
        if (grantResults.length < permissions.length) {
            return;
        }
        // permission to pass
        //通过的权限
        List<String> granted = new ArrayList<>();
        // denied access
        //拒绝的权限
        List<String> denied = new ArrayList<>();
        // rejected permissions that cannot be asked for
        //拒绝的且不能询问的权限
        List<String> noask = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
                // judgment cannot be questioned
                //判断不能被询问(未通化切不需要提示即为被禁止了)
                if (!shouldShowRequestPermissionRationale(perm)) {
                    //保存被禁止的权限
                    updateDismissState(perm, true);
                    noask.add(perm);
                }
            }
        }
        //Do not process onpermissionsseparation when true
        //为true不处理onPermissionsDismiss
        boolean onPermissionsDismiss = false;
        // permission is forbidden to ask
        //有权限被禁止询问
        if (!noask.isEmpty()) {
            EasyPermissionLog.d("权限现在被禁止询问");
            onPermissionsDismiss = permissionResult.onDismissAsk(requestCode, noask,true);
        }
        // permission not passed
        //有权限未通过
        if (!onPermissionsDismiss && !denied.isEmpty()) {
            EasyPermissionLog.d("权限被拒绝");
            permissionResult.onPermissionsDismiss(requestCode, denied);
        }
        // all permissions passed
        // 所有权限都通过
        if (!granted.isEmpty() && granted.size() == permissions.length) {
            EasyPermissionLog.d("权限已通过");
            permissionResult.onPermissionsAccess(requestCode);
        }
    }

    /**
     * Handle permissions result callbacks from App-Settings
     * In the activity of need to access the callback rewrite onActivityResult calls to this function
     * <p>
     * 处理从系统设置权限结果回调
     * 在需要权限回调的activity中重写onActivityResult调用此函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mCurrentEasyPermission == null || mCurrentEasyPermission.getResult() == null) {
            return;
        }
        if (mCurrentEasyPermission.getRequestCode() == requestCode) {
            //设置界面返回
            //Result from system setting
            if (mCurrentEasyPermission.hasPermission()) {
                //权限已通过
                //Permission approved
                mCurrentEasyPermission.onPermissionsAccess();
            } else {
                //从设置回来还是没给你权限
                //You still didn't get permission from Settings
                mCurrentEasyPermission.onPermissionsDismiss();
            }
        }
    }


}
