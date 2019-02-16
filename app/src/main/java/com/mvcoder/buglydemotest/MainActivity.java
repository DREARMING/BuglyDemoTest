package com.mvcoder.buglydemotest;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.notification.NotificationActivity;
import com.mvcoder.buglydemotest.service.ForegroundSimpleService;
import com.mvcoder.buglydemotest.service.SimpleService;
import com.mvcoder.buglydemotest.workmanager.WorkManagerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    @BindView(R.id.bt_crash)
    Button btCrash;
    @BindView(R.id.bt_update)
    Button btUpdate;
    @BindView(R.id.bt_notification)
    Button btNotification;
    @BindView(R.id.bt_work_manager)
    Button btWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getArgument(getIntent());
        LogUtils.d("MainActivity on Create");
        ButterKnife.bind(this);
        requestPermission();
    }

    private void getArgument(Intent intent) {
        boolean notificationEnter = intent.getBooleanExtra("notification", false);
        if (notificationEnter) {
            String activityName = intent.getStringExtra("targetActivity");
            Intent intent1 = new Intent();
            intent1.putExtra("content", intent.getStringExtra("content"));
            intent1.setClassName(this, activityName);
            startActivity(intent1);
        }
    }

    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.FOREGROUND_SERVICE};
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, "该软件需要一些权限", 1, permissions);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @OnClick({R.id.bt_crash, R.id.bt_update, R.id.bt_notification,R.id.bt_work_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_crash:
                joinToCrashActivity();
                break;
            case R.id.bt_update:
                startService();
                break;
            case R.id.bt_notification:
                joinToNotificationActivity();
                break;
            case R.id.bt_work_manager:
                joinToWorkManagerActivity();
                break;
        }
    }

    private void joinToWorkManagerActivity(){
        Intent intent = new Intent(this, WorkManagerActivity.class);
        startActivity(intent);
    }

    private void startService() {
        Intent intent = new Intent(this, SimpleService.class);
        startService(intent);
    }

    private void startForeService() {
        Intent intent = new Intent(this, ForegroundSimpleService.class);
        startService(intent);
    }

    private void joinToCrashActivity() {
        Intent intent = new Intent(this, CrashActivity.class);
        startActivity(intent);
    }

    private void joinToNotificationActivity() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("MainActivity on new intent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(MainActivity.class.getSimpleName() + "onDestroy");
    }
}
