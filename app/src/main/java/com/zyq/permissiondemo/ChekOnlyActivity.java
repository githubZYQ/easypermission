package com.zyq.permissiondemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;

/**
 * 只检查权限和结果并显示它
 * only check the permission and result and show it
 * @author zyq
 * @date 2019 06 03
 */
public class ChekOnlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekonly);
        TextView textView = findViewById(R.id.textView);
        textView.setText(EasyPermission.build().hasPermission(this, Manifest.permission.CALL_PHONE) ? "允许" : "拒绝");
    }
}
