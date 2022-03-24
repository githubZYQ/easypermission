package com.zyq.permissiondemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.util.EasyNotificationTool;

/**
 * 演示通知栏权限
 * demo for Notification
 * @author Zhang YanQiang
 * @date 2022 03 22
 */
public class NotificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_reslut);
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyNotificationTool.gotoAppSettings(mContext);
            }
        });
        findViewById(R.id.btnShowResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResult();
    }
    private void showResult(){
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(EasyNotificationTool.isNotificationEnabled(mContext)?"已通过":"未通过");
    }
}
