package com.zyq.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;
import com.zyq.easypermission.bean.PermissionAlertInfo;
import com.zyq.easypermission.util.EasyLocationTool;

import java.util.List;

/**
 * 演示定位服务GPS
 * demo for LocationService
 * @author Zhang YanQiang
 * @date 2022 03 22
 */
public class LocationServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_reslut);
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请权限
                //To apply for permission
                requestPermission();
//                openGps();
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

    /**
     * Display the results
     * 展示结果
     */
    private void showResult(){
        TextView textView2 = findViewById(R.id.textView2);
        if(EasyPermission.build().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)){
            textView2.setText(EasyLocationTool.isLocationEnabled(mContext)?"定位服务已打开":"定位服务关闭中");
        }else {
            textView2.setText("还没有获取到定位权限哦");
        }
    }
    EasyPermission mEasyPermission;
    /**
     * To apply for permission
     * 申请权限
     */
    private void requestPermission(){
        mEasyPermission = EasyPermission.build()
                .mPerms(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .mAlertInfo(new PermissionAlertInfo("权限极简需要定位权限","当前功能以及以后许多功能都需要您的位置信息，请允许定位权限。如果不允许将无法使用某些位置相关功能，其它不相关服务不受影响"))
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        mToast("权限被拒绝");
                    }

                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        //开启定位服务
                        //Enabling the Location Service
                        openGps();
                    }

                    @Override
                    public boolean onDismissAsk(int requestCode, @NonNull List<String> permissions) {
                        //提示用户权限的重要性
                        //Indicates the importance of user permissions
                        openAppDetails();
                        return true;
                    }
                }).requestPermission();
    }
    /**
     * Enable GPS service
     * 打开GPS服务
     */
    private void openGps(){
        showResult();
        if(!EasyLocationTool.isLocationEnabled(mContext)){
            //去打开GPS服务
            //Turn on the GPS service
            EasyLocationTool.gotoAppSettings(mContext);
        }
    }

}
