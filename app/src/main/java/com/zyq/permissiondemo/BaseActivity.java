package com.zyq.permissiondemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.zyq.easypermission.EasyPermissionHelper;

/**
 * @author Zyq
 * @date 2019/6/3　14:49.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 在baseActivity覆盖该方法,或者每个活动需要使用EasyPermission
     * Override this method in baseActivity ，or each Activity who need use EasyPermission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
//        Inject the callback using EasyPermissionHelper
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * start one activity
     * @param newClass activity's name
     */
    protected void startOneActivity(@NonNull Class newClass) {
        startActivity(new Intent(this, newClass));
    }
}
