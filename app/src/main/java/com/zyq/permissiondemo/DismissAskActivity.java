package com.zyq.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

/**
 * 演示对DismissAsk
 * demo for DismissAsk
 *
 * @author Zyq
 * @date 2019 06 03
 */
public class DismissAskActivity extends BaseActivity {
    private static final int RC_CODE_CALLPHONE = 1024;
    TextView textView2;
    EasyPermission easyPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_reslut);
        textView2 = findViewById(R.id.textView2);

        easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_CALLPHONE)
                .mContext(DismissAskActivity.this)
                .mPerms(Manifest.permission.CALL_PHONE)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //做你想做的
                        textView2.setText("do you something here");
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        //你的权限被用户拒绝了你怎么办
                        textView2.setText("you " + permissions.toString() + " is onPermissionsDismiss");
                    }

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        //你的权限被用户禁止了并且不能请求了你怎么办
                        textView2.setText("you " + permissions.toString() + " is onDismissAsk");
                        easyPermission.openAppDetails(DismissAskActivity.this, "Call Phone - Give me the permission to dial the number for you");
                        return true;
                    }
                });
        easyPermission.requestPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (EasyPermission.APP_SETTINGS_RC == requestCode) {
            //设置界面返回
            //Result from system setting
            if (easyPermission.hasPermission(DismissAskActivity.this)) {
                //做你想做的
                textView2.setText("do you something here");
            } else {
                //从设置回来还是没给你权限
                textView2.setText("I still don't have access from setting");
            }

        }
    }
}
