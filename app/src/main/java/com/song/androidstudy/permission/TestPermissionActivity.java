package com.song.androidstudy.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.androidstudy.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Android Permission
 * Github项目地址  https://github.com/permissions-dispatcher/PermissionsDispatcher
 * <p>
 * Created by chensongsong on 2018/10/19.
 */
@RuntimePermissions
public class TestPermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button requestPermissionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission);

        requestPermissionBtn = ((Button) findViewById(R.id.btn_permission_request));
        requestPermissionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_permission_request:
                TestPermissionActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: 将权限处理委托给生成的方法
        TestPermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        // NOTE: 执行申请权限的操作。权限被授予
        Toast.makeText(this, "showCamera", Toast.LENGTH_LONG).show();
    }

//    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
//    void showContacts() {
//        // NOTE: 执行申请权限的操作。权限被授予
//        // 申请多个权限
//        Toast.makeText(this, "showContacts", Toast.LENGTH_LONG).show();
//    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        // NOTE: 若用户拒绝权限，说明需要权限的理由，如dialog。
        // 在dialog上调用proceed()或cancel()以继续或中止
//        showRationaleDialog("showRationaleForCamera", request);
        request.proceed();
    }

//    @OnShowRationale({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
//    void showRationaleForContact(PermissionRequest request) {
//        // NOTE: 若用户拒绝权限，说明需要权限的理由，如dialog。
//        // 在dialog上调用proceed()或cancel()以继续或中止
//        showRationaleDialog("showRationaleForContact", request);
//    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        // NOTE: 处理被拒绝的权限，例如显示特定的UI或禁用某些功能
        Toast.makeText(this, "onCameraDenied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        // NOTE 用户选择进制提示，可以跳转到设置界面开启权限
        Toast.makeText(this, "onCameraNeverAskAgain", Toast.LENGTH_SHORT).show();

        // 唯一方案
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRationaleDialog(String messageRes, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageRes)
                .show();
    }

}
