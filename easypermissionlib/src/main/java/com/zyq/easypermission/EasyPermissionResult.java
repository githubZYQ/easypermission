package com.zyq.easypermission;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Request permission result callback
 * 请求权限结果回调
 *
 * @author Zhang YanQiang
 * @date 2019/6/2　14:55.
 */
public abstract class EasyPermissionResult {
    /**
     * The currently bound permission application object
     * 当前绑定的权限申请对象
     */
    public EasyPermission mEasyPermission = null;

    public EasyPermissionResult() {
    }

    public EasyPermission getEasyPermission() {
        return mEasyPermission;
    }

    /**
     * Bind the permission application object for subsequent invocation
     * 绑定权限申请对象，方便后续调用
     * @param mEasyPermission
     */
    public void setEasyPermission(EasyPermission mEasyPermission) {
        this.mEasyPermission = mEasyPermission;
    }

    /**
     * Permissions allowed
     * do what you need to do
     * 权限被允许
     * 执行自己需要的操作
     *
     * @param requestCode 请求的code
     */
    public void onPermissionsAccess(int requestCode) {
        EasyPermissionLog.d("onPermissionsAccess：code =" + requestCode);
    }

    /**
     * Permissions are questioned and denied
     * 权限被询问并拒绝
     *
     * @param requestCode 请求的code
     * @param permissions 被拒绝的权限
     */
    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
        EasyPermissionLog.d("onPermissionsDismiss：code =" + requestCode + " " + permissions.toString());
    }
    /**
     * Permissions are simply refused to be asked
     * 权限被直接拒绝询问,如果设置了mAlertInfo并且autoOpenAppDetails = true,就会自动处理，不需要再单独处理
     *
     * @param requestCode 请求的code
     * @param permissions 被拒绝的权限
     * @return 默认返回false，如果返回为true，将只处理onDismissAsk的回调，不再往下继续处理onPermissionsDismiss或者别的权限请求
     * Returns false by default, and if so, only the callback onDismissAsk will be processed,
     * so that the onPermissionsDismiss or other permission request will not be processed further down
     */
    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
        EasyPermissionLog.d("onDismissAsk：code =" + requestCode + " " + permissions.toString());
        if(autoOpenDialog()){
            openAppDetails();
            return true;
        }else {
            return false;
        }
    }

    /**
     * Permissions are simply refused to be asked
     * 权限被直接拒绝询问,如果设置了mAlertInfo并且autoOpenAppDetails = true,就会自动处理，不需要再单独处理
     *
     * @param requestCode 请求的code
     * @param permissions 被拒绝的权限
     * @param firstDismissAsk 是否首次被禁止
     * @return 默认返回false，如果返回为true，将只处理onDismissAsk的回调，不再往下继续处理onPermissionsDismiss或者别的权限请求
     * Returns false by default, and if so, only the callback onDismissAsk will be processed,
     * so that the onPermissionsDismiss or other permission request will not be processed further down
     */
    protected boolean onDismissAsk(int requestCode, @NonNull List<String> permissions,boolean firstDismissAsk) {
        EasyPermissionLog.d("onDismissAsk：code =" + requestCode + " " + permissions.toString() + " firstDismissAsk:"+firstDismissAsk);
        if(firstDismissAsk){
            return false;
        }else {
            return onDismissAsk(requestCode,permissions);
        }
    }

    /**
     * Open the APP's permission details Settings
     * 打开 APP 的权限详情设置提示弹框
     */
    public void openAppDetails(){
        if(mEasyPermission != null){
            mEasyPermission.openAppDetails();
        }
    }

    /**
     * Determines whether the current request opens the Settings popup automatically
     * 判断当前请求是否自动打开设置弹窗
     * @return
     */
    private boolean autoOpenDialog(){
        if(mEasyPermission == null){
            return false;
        }else if(mEasyPermission.getAlertInfo() == null){
            return false;
        }else {
            return mEasyPermission.isAutoOpenAppDetails();
        }
    }

    /**
     * Skip To the Settings
     * 跳转设置页
     */
    public void goToAppSettings() {
        if(mEasyPermission != null){
            mEasyPermission.goToAppSettings();
        }
    }
}
