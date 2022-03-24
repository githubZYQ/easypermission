package com.zyq.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;

/**
 * @author Zhang YanQiang
 * @date 2019 06 03
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initOnclick();
    }

    private void initOnclick() {
        //buttonCheck 只检测权限状态
        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //demo for only check
                //演示只供检查
                startOneActivity(ChekOnlyActivity.class);
            }
        });
        //buttonOnlyApply 仅申请，不需要结果
        findViewById(R.id.buttonOnlyApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Only requestPermission", Toast.LENGTH_SHORT).show();
                //demo for only apply needn't result
                //演示只适用于不需要结果
                EasyPermission.build().requestPermission(Manifest.permission.CAMERA);
            }
        });
        //buttonNeedResult 需要权限申请通过/拒绝回调
        findViewById(R.id.buttonNeedResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(NeedReslutActivity.class);
            }
        });
        //buttonDisMissAsk 需要权限禁止询问回调
        findViewById(R.id.buttonDisMissAsk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(DismissAskActivity.class);
            }
        });
        //buttonEndlessly 强制需要权限(慎用)
        findViewById(R.id.buttonEndlessly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                When you need a permission endlessly.
                startOneActivity(EndlessActivity.class);
            }
        });
        //buttonWithExplain 申请权限附带说明(推荐)
        findViewById(R.id.buttonWithExplain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                When you need a permission with explain.
                startOneActivity(RequestWithExplainActivity.class);
            }
        });
        //buttonForFloatWindow 通知栏权限
        findViewById(R.id.buttonForFloatWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(FloatWindowActivity.class);
            }
        });
        //buttonForNotification 悬浮窗权限
        findViewById(R.id.buttonForNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(NotificationActivity.class);
            }
        });
        //buttonForGps 定位服务
        findViewById(R.id.buttonForGps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(LocationServiceActivity.class);
            }
        });

    }
}
