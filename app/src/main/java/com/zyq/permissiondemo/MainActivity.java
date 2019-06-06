package com.zyq.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zyq.easypermission.EasyPermission;

/**
 * @author Zyq
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
        //buttonCheck
        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //demo for only check
                //演示只供检查
                startOneActivity(ChekOnlyActivity.class);
            }
        });
        //buttonOnlyApply
        findViewById(R.id.buttonOnlyApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Only requestPermission", Toast.LENGTH_SHORT).show();
                //demo for only apply needn't result
                //演示只适用于不需要结果
                EasyPermission.build().requestPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
            }
        });
        //buttonNeedResult
        findViewById(R.id.buttonNeedResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(NeedReslutActivity.class);
            }
        });
        //buttonDisMissAsk
        findViewById(R.id.buttonDisMissAsk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOneActivity(DismissAskActivity.class);
            }
        });
        //buttonEndlessly
        findViewById(R.id.buttonEndlessly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                When you need a permission endlessly.
                startOneActivity(EndlessActivity.class);
            }
        });

    }
}
