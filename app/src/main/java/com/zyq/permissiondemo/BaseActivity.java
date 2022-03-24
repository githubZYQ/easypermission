package com.zyq.permissiondemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.zyq.easypermission.EasyPermissionHelper;

/**
 * @author Zhang YanQiang
 * @date 2019/6/3　14:49.
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 不同的权限应该有不同的回调code
     */
    protected static final int RC_CODE_PERMISSION = 1024;
    protected BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    /**
     * 在baseActivity覆盖该方法,或者每个活动需要使用EasyPermission
     * Override this method in baseActivity ，or each Activity who need use EasyPermission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
//        Inject the callback using EasyPermissionHelper
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 需要从系统设置界面返回时判断权限情况的，在baseActivity覆盖该方法,或者每个活动需要使用EasyPermission
     * Override this method in baseActivity ，or each Activity who need use EasyPermission，
     * if you want to get the new state form settings page
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //使用EasyPermissionHelper注入回调
//        Inject the callback using EasyPermissionHelper
        EasyPermissionHelper.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * start one activity
     * @param newClass activity's name
     */
    protected void startOneActivity(@NonNull Class newClass) {
        startActivity(new Intent(this, newClass));
    }

    /**
     * A Tip
     * @param msg
     */
    protected void mToast(CharSequence msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
