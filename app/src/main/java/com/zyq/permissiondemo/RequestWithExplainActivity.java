package com.zyq.permissiondemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;
import com.zyq.easypermission.EasyPermissionLog;
import com.zyq.easypermission.EasyPermissionResult;
import com.zyq.easypermission.bean.PermissionAlertInfo;

import java.util.List;

/**
 * 演示带解释说明申请权限
 * demo for Request With Explain
 *
 * @author Zhang YanQiang
 * @date 2022 03 21
 */
public class RequestWithExplainActivity extends BaseActivity {
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
        //设置权限描述
        PermissionAlertInfo alertInfo = new PermissionAlertInfo("**需要申请摄像头权限",
                "**需要申请摄像头拍摄权限，以便您能够通过扫一扫实现扫描二维码；通过拍照更换您帐号的头像；拍照上传一些注册帐号需要的证件信息。拒绝或取消授权将影响以上功能，不影响使用其他服务");
        easyPermission = EasyPermission.build()
                .mRequestCode(RC_CODE_PERMISSION)
                .mAlertInfo(alertInfo)
                .setAutoOpenAppDetails(true)
                .mPerms(Manifest.permission.CAMERA)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        mToast("权限已通过");
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

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        mToast("权限被禁止");
                        //你的权限被用户禁止了并且不能请求了你怎么办，提示去系统-设置打开
                        textView2.setText(permissions.toString() + " 被禁止了，需要向用户说明情况");
                        return super.onDismissAsk(requestCode,permissions);//这里true表示拦截处理，不再回调onPermissionsDismiss；
                    }
                    @Override
                    public void openAppDetails() {
                        //弹出默认的权限详情设置提示弹出框，在设置页完成允许操作后，会自动回调到onPermissionsAccess()
                        super.openAppDetails();
                        //如果样式不满意，可以弹出自定义明弹窗，在用户确认时调用easyPermission.goToAppSettings();完成跳转设置页
                    }
                }).requestPermission();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EasyPermissionLog.d(mContext.getLocalClassName()+": onNewIntent");
        //launchMode="singleTask" onNewIntent中如果需要请求权限，需要重新设置activity
        //注意：这里并不是说必须在onNewIntent这样做，而是非要在onNewIntent执行权限请求的话，才需要设置mContext。
        //正常情况下在onCreate和onResume中不需要设置
        //方式一：
        EasyPermissionHelper.getInstance().updateTopActivity(mContext);
        easyPermission.requestPermission();
//        //方式二:
//        easyPermission.mContext(mContext).requestPermission();
    }
}
