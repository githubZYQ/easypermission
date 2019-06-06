package com.zyq.easypermission;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Request permission result callback
 * 请求权限结果回调
 *
 * @author Zyq
 * @date 2019/6/2　14:55.
 */
public abstract class EasyPermissionResult {

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
     * 权限被直接拒绝询问
     *
     * @param requestCode 请求的code
     * @param permissions 被拒绝的权限
     * @return 默认返回false，如果返回为true，将只处理onDismissAsk的回调，不再往下继续处理onPermissionsDismiss或者别的权限请求
     * Returns false by default, and if so, only the callback onDismissAsk will be processed,
     * so that the onPermissionsDismiss or other permission request will not be processed further down
     */
    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
        EasyPermissionLog.d("onDismissAsk：code =" + requestCode + " " + permissions.toString());
        return false;
    }
}
