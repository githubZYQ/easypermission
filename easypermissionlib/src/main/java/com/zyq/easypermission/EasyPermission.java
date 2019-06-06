package com.zyq.easypermission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Permissions related to the implementation of some functions
 * Including setting parameters：mRequestCode，mResult，mPerms，mContext
 * 请求权限的一些功能实现
 * 包括设置参数：code，结果，权限，mContext
 *
 * @author Zyq
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
     * 请求code
     */
    private int mRequestCode = 1;
    /**
     * Request a callback for permissions
     * 请求权限的结果回调
     */
    private EasyPermissionResult mResult = null;
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
    public void setContext(@NonNull Activity mContext) {
        this.mContext = mContext;
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
    public void setPerms(@NonNull String[] mPerms) {
        this.mPerms = mPerms;
    }

    protected int getRequestCode() {
        return mRequestCode;
    }

    /**
     * Reset the requested code
     * 请求的code
     *
     * @param requestCode A value of 0 means no callback is required
     * @return
     */
    public void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
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
    public void setResult(EasyPermissionResult result) {
        this.mResult = result;
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
     * Check the current permissions
     * 检查当前的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        return EasyPermissionHelper.getInstance().hasPermission(context, permissions);
    }

    /**
     * Denied permission and forbidden to ask
     * This is only useful in callbacks that apply callback authority
     * 权限被拒绝且禁止询问
     * 只有在申请的回调中使用
     *
     * @param context
     * @param permission
     * @return
     */
    protected boolean hasDismissAsk(@NonNull Activity context, @NonNull String permission) {
        return EasyPermissionHelper.getInstance().hasDismissAsk(context, permission);
    }

    /**
     * Determine whether the permission can be queried
     * return true when user is prompted for permission function, or false in other cases
     * returns fasle before first requesting permission
     * 判断该权限能否询问
     * 需要提示用户权限功能时返回true，其它情况返回false
     * 第一次请求权限前也返回fasle
     *
     * @param context
     * @param permission
     */
    public boolean shouldShowRequestPermissionRationale(@NonNull Activity context, String permission) {
        return EasyPermissionHelper.getInstance().shouldShowRequestPermissionRationale(context, permission);
    }

    /**
     * To apply for permission
     * 申请权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public void requestPermission(@NonNull Activity context, @NonNull String... permissions) {
        if (mResult == null) {
            //If you have access, you don't need to apply
            //如果权限都有了，不需要申请
            if (hasPermission(context, permissions)) {
                return;
            }
            //Request permission but no permission result callback is required
            //申请权限但是不需要权限结果回调
            EasyPermissionHelper.getInstance().requestPermission(context, permissions);
            return;
        }
        //If all permissions are available, call back directly
        //如果权限都有了，直接回调
        if (hasPermission(context, permissions)) {
            mResult.onPermissionsAccess(mRequestCode);
            return;
        }
        this.mContext = context;
        this.mPerms = permissions;
        //执行申请权限
        //Execute application authority
        EasyPermissionHelper.getInstance().requestPermission(context, mRequestCode, mResult, mPerms);

    }

    /**
     * Request permissions, using existing parameters
     * The corresponding parameters have been set
     * 申请权限，使用当前已有的参数
     * 已经设置好相应的参数
     *
     * @return
     */
    public void requestPermission() {
        if (mContext == null) {
            return;
        }
        if (mPerms == null) {
            return;
        }
        requestPermission(mContext, mPerms);
    }

    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置
     */
    public void openAppDetails(@NonNull final Activity context, String... permissionShow) {
        EasyPermissionHelper.getInstance().openAppDetails(context, permissionShow);
    }
    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置
     */
    public void openAppDetailsForEn(@NonNull final Activity context, String... permissionShow) {
        EasyPermissionHelper.getInstance().openAppDetailsForEn(context, permissionShow);
    }

}
