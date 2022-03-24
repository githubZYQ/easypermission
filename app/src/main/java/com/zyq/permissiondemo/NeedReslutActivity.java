package com.zyq.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

/**
 * 演示需要结果的
 * demo for NeedReslut
 * @author Zhang YanQiang
 * @date 2019 06 03
 */
public class NeedReslutActivity extends BaseActivity {
    TextView textView2;
    EasyPermission easyPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_reslut);
        textView2 = findViewById(R.id.textView2);
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyPermission.requestPermission();
            }
        });
        findViewById(R.id.btnShowResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(ChekOnlyActivity.class);
            }
        });
        //权限请求
        easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
//                .mContext(NeedReslutActivity.this)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        mToast("权限通过");
                        //做你想做的
                        textView2.setText("权限已通过");
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        mToast("权限被拒绝");
                        //你的权限被用户拒绝了你怎么办
                        textView2.setText(permissions.toString() + " 被拒绝了");
                    }
                }).requestPermission();
    }
}
