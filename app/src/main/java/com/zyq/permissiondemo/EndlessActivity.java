package com.zyq.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

/**
 * 当权限必需时你才能进行下一步你怎么办
 * demo for endless
 *
 * @author Zhang YanQiang
 * @date 2019 06 03
 */
public class EndlessActivity extends BaseActivity {
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

        easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
//                .mContext(EndlessActivity.this)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //做你想做的
                        textView2.setText("权限已通过");
                        mToast("权限通过");
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        mToast("权限被拒绝");
                        //你的权限被用户拒绝了你怎么办
                        textView2.setText(permissions.toString() + " 被拒绝了");
                        //just request it again
                        easyPermission.requestPermission();
                    }

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        mToast("权限被禁止");
                        //你的权限被用户禁止了并且不能请求了你怎么办
                        textView2.setText(permissions.toString() + " 被禁止了，需要向用户说明情况");
                        easyPermission.openAppDetails( getString(R.string.permission_explain_camera));
                        return true;
                    }
                });
        easyPermission.requestPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (easyPermission.getRequestCode() == requestCode) {
            //设置界面返回
            //Result from system setting
            if (easyPermission.hasPermission()) {
                //做你想做的
                textView2.setText("权限已通过");
            } else {
                //从设置回来还是没给你权限
                textView2.setText("I still don't have access from setting");
            }

        }
    }
}
