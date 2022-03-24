package com.zyq.easypermission;

import android.app.Activity;
import androidx.annotation.NonNull;

import com.zyq.easypermission.bean.PermissionAlertInfo;

import java.util.Arrays;

/**
 * Permissions related to the implementation of some functions
 * Including setting parameters：mRequestCode，mResult，mPerms，mContext
 * 请求权限的一些功能实现
 * 包括设置参数：code，结果，权限，mContext
 *
 * @author Zhang YanQiang
 * @date 2019/6/2　16:26.
 */
public class EasyPermission {

    /**
     * System Settings interface callback
     * 从系统设置返回的code
     */
    public static final int APP_SETTINGS_RC = 2048;
    /**
     * The code to request permission
     * 请求code 默认2048
     */
    private int mRequestCode = APP_SETTINGS_RC;
    /**
     * Request a callback for permissions
     * 请求权限的结果回调
     */
    private EasyPermissionResult mResult = null;
    /**
     * Request permission message
     * 请求权限的提示信息
     */
    private PermissionAlertInfo mAlertInfo = null;
    /**
     * Automatically opens the APP's permission details Settings, when Permissions refused to be asked
     * 当被禁止时，是否自动打开 APP 的权限详情设置提示弹框，默认为true
     */
    private boolean autoOpenAppDetails = true;
    /**
     * Context in which permission is requested
     * 当前activity
     */
    private Activity mContext = null;
    /**
     * Requested permissions
     * 请求的权限
     */
    private String[] mPerms = null;

    protected EasyPermission() {
    }

    protected Activity getContext() {
        return mContext;
    }

    /**
     * Reset activity
     * 重设activity
     *
     * @param mContext
     */
    public EasyPermission setContext(@NonNull Activity mContext) {
        this.mContext = mContext;
        return this;
    }

    protected String[] getPerms() {
        return mPerms;
    }

    /**
     * Reset permissions
     * 重设请求的权限
     *
     * @param mPerms
     */
    public EasyPermission setPerms(@NonNull String[] mPerms) {
        this.mPerms = mPerms;
        return this;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public PermissionAlertInfo getAlertInfo() {
        return mAlertInfo;
    }

    public EasyPermission setAlertInfo(PermissionAlertInfo mAlertInfo) {
        this.mAlertInfo = mAlertInfo;
        return this;
    }

    public boolean isAutoOpenAppDetails() {
        return autoOpenAppDetails;
    }

    /**
     * Setting whether to automatically open APP permission details Setting prompt box
     * 设置是否自动打开 APP 的权限详情设置提示弹框
     * @param autoOpenAppDetails
     */
    public EasyPermission setAutoOpenAppDetails(boolean autoOpenAppDetails) {
        this.autoOpenAppDetails = autoOpenAppDetails;
        return this;
    }

    /**
     * Reset the requested code
     * 请求的code
     *
     * @param requestCode A value of 0 means no callback is required
     * @return
     */
    public EasyPermission setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    protected EasyPermissionResult getResult() {
        return mResult;
    }

    /**
     * reset result
     * 重新设置result
     *
     * @param result Null means no callback is required 空表示不需要回调
     * @return
     */
    public EasyPermission setResult(EasyPermissionResult result) {
        this.mResult = result;
        mResult.setEasyPermission(this);
        return this;
    }

    /**
     * Returns a new EasyPermission object
     * 返回一个EasyPermission的对象
     *
     * @return
     */
    public static EasyPermission build() {
        return new EasyPermission();
    }

    /**
     * Set the code for the request
     * 设置请求的code
     *
     * @param requestCode A value of 0 indicates that no callback processing is required 0表示不需要回调
     * @return
     */
    public EasyPermission mRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * Setting result to null means that no callback processing is required
     * 设置结果回调 null表示不要回调
     *
     * @param result
     * @return
     */
    public EasyPermission mResult(EasyPermissionResult result) {
        this.mResult = result;
        mResult.setEasyPermission(this);
        return this;
    }

    /**
     * set context
     * 设置当前activity
     *
     * @param context
     * @return
     */
    public EasyPermission mContext(@NonNull Activity context) {
        this.mContext = context;
        return this;
    }

    /**
     * set permissions
     * 设置请求的权限
     *
     * @param permissions
     * @return
     */
    public EasyPermission mPerms(@NonNull String... permissions) {
        this.mPerms = permissions;
        return this;
    }

    /**
     * set PermissionAlertInfo
     * 设置请求的权限提示信息
     *
     * @param mAlertInfo
     * @return
     */
    public EasyPermission mAlertInfo(@NonNull PermissionAlertInfo mAlertInfo) {
        this.mAlertInfo = mAlertInfo;
        return this;
    }

    /**
     * Check the current permissions
     * 检查当前的权限
     *
     * @return
     */
    public boolean hasPermission() {
        return EasyPermissionHelper.getInstance().hasPermission(mPerms);
    }


    /**
     * Check the current permissions
     * 检查当前的权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(@NonNull String... permissions) {
        mPerms = permissions;
        return EasyPermissionHelper.getInstance().hasPermission(permissions);
    }

    /**
     * Denied permission and forbidden to ask
     * 权限被拒绝且禁止询问(本地记录了被禁止过的)
     *
     *
     * @param permissions 有一个被禁止即返回true
     * @return
     */
    protected boolean hasDismissAsk(@NonNull String... permissions) {
        mPerms = permissions;
        return EasyPermissionHelper.getInstance().hasDismissAsk(permissions);
    }
    /**
     * To apply for permission
     * 申请权限
     *
     * @param permissions
     * @return
     */
    public EasyPermission requestPermission(@NonNull String... permissions) {
        this.requestPermission(mContext,permissions);
        return this;
    }

    /**
     * To apply for permission
     * 申请权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public EasyPermission requestPermission(@NonNull Activity context, @NonNull String... permissions) {
        if(context != null){
            this.mContext = context;
        }
        mPerms = permissions;
        if(permissions == null || permissions.length == 0){
            EasyPermissionLog.d("permissions.length == 0");
            return this;
        }
        if (mResult == null) {
            //If you have access, you don't need to apply
            //如果权限都有了，不需要申请
            if (hasPermission(permissions)) {
                //Do nothing
                //什么也不需要做了
            }else {
                //Request permission but no permission result callback is required
                //申请权限但是不需要权限结果回调
                EasyPermissionHelper.getInstance().requestPermission(this);
            }
        }else{
            if (hasPermission(permissions)) {
                //If all permissions are available, call back directly
                //如果权限都有了，直接回调
                mResult.onPermissionsAccess(mRequestCode);
            }else if(EasyPermissionHelper.getInstance().haveBeanDismissAsk(permissions)){
                //If all permissions are dismissed to ask, call back directly
                //如果权限被禁止，直接回调
                mResult.onDismissAsk(mRequestCode, Arrays.asList(permissions));
            }else {
                this.mContext = context;
                this.mPerms = permissions;
                //执行申请权限
                //Execute application authority
                EasyPermissionHelper.getInstance().requestPermission(this);
            }
        }
        return this;
    }

    /**
     * Request permissions, using existing parameters
     * The corresponding parameters have been set
     * 申请权限，使用当前已有的参数
     * 已经设置好相应的参数
     * 如果设置了提示信息PermissionAlertInfo，因为需要弹窗提示，建议在onResume()之后执行
     *
     * @return
     */
    public EasyPermission requestPermission() {
        if (mPerms == null) {
            return this;
        }
        requestPermission(mPerms);
        return this;
    }

    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置
     * @param permissionShow 权限描述 推荐样式“定位-帮助您推荐上车地点”
     *                       Permission description recommended style "location - help you recommend places to get on"
     */
    public void openAppDetails(String... permissionShow) {
        Activity topActivity = EasyPermissionHelper.getInstance().getTopActivity();
        String title = topActivity.getString(R.string.setting_alert_title);
        String message = topActivity.getString(R.string.setting_alert_message);
        StringBuilder msg = new StringBuilder();
        if (permissionShow != null && permissionShow.length > 0) {
            for (int i = 0; i < permissionShow.length; i++) {
                msg.append(permissionShow[i]);
                msg.append("\n");
            }
        }
        msg.append(message);
        this.mAlertInfo = new PermissionAlertInfo(title,msg.toString());
        EasyPermissionHelper.getInstance().openAppDetailsDefaultStyle(this);
    }

    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置
     * @param alertInfo 附带弹窗提示
     */
    public void openAppDetails(PermissionAlertInfo alertInfo) {
        this.mAlertInfo = alertInfo;
        EasyPermissionHelper.getInstance().openAppDetailsDefaultStyle(this);
    }
    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置提示弹框
     */
    public void openAppDetails() {
        EasyPermissionHelper.getInstance().openAppDetailsDefaultStyle(this);
    }
    /**
     * Skip To the Settings
     * 跳转设置页
     */
    public void goToAppSettings() {
        EasyPermissionHelper.getInstance().goToAppSettings(this);
    }

}
