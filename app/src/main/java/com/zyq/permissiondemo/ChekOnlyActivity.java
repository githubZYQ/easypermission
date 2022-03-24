package com.zyq.permissiondemo;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zyq.easypermission.EasyPermission;

/**
 * 只检查权限和结果并显示它
 * only check the permission and result and show it
 * @author zyq
 * @date 2019 06 03
 */
public class ChekOnlyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekonly);
        TextView textView = findViewById(R.id.textView);
        textView.setText(EasyPermission.build().hasPermission(Manifest.permission.CAMERA) ? "已允许" : "未允许");
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,RequestWithExplainActivity.class));
            }
        });
    }
}
