package com.mvcoder.buglydemotest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.notification.NotificationActivity;
import com.mvcoder.buglydemotest.paging.PagingActivity;
import com.mvcoder.buglydemotest.service.ForegroundSimpleService;
import com.mvcoder.buglydemotest.service.NoNotificationForegroudService;
import com.mvcoder.buglydemotest.service.SimpleService;
import com.mvcoder.buglydemotest.update.UpdateActivity;
import com.mvcoder.buglydemotest.workmanager.WorkManagerActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.ui.UILifecycleListener;

import java.util.ArrayList;
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
    @BindView(R.id.bt_paging)
    Button btPaging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getArgument(getIntent());
        LogUtils.d("MainActivity on Create");
        ButterKnife.bind(this);
        requestPermission();
        initView();
    }

    private void initView() {


        Beta.upgradeDialogLifecycleListener = new UILifecycleListener<UpgradeInfo>() {
            @Override
            public void onCreate(Context context, View view, UpgradeInfo upgradeInfo) {
                // 注：可通过这个回调方式获取布局的控件，如果设置了id，可通过findViewById方式获取，如果设置了tag，可以通过findViewWithTag，具体参考下面例子:

                // 通过id方式获取控件，并更改imageview图片
                // 通过tag方式获取控件，并更改布局内容

                // 更多的操作：比如设置控件的点击事件
            }

            @Override
            public void onStart(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onResume(Context context, View view, UpgradeInfo upgradeInfo) {

            }

            @Override
            public void onPause(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onStop(Context context, View view, UpgradeInfo upgradeInfo) {
            }

            @Override
            public void onDestroy(Context context, View view, UpgradeInfo upgradeInfo) {
            }

        };

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
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissionList.add(Manifest.permission.FOREGROUND_SERVICE);
        }
        String[] permissions = permissionList.toArray(new String[]{});
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(this, "该软件需要一些权限", 1, permissions);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @OnClick({R.id.bt_crash, R.id.bt_update, R.id.bt_notification, R.id.bt_work_manager, R.id.bt_paging})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_crash:
                joinToCrashActivity();
                break;
            case R.id.bt_update:
                //startNoNotificationStartService();
                joinUpdateActivity();
                break;
            case R.id.bt_notification:
                joinToNotificationActivity();
                break;
            case R.id.bt_work_manager:
                joinToWorkManagerActivity();
                break;
            case R.id.bt_paging:
                joinToPagingActvitiy();
                break;
        }
    }

    private void joinUpdateActivity(){
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    private void joinToPagingActvitiy(){
        Intent intent = new Intent(this, PagingActivity.class);
        startActivity(intent);
    }

    private void joinToWorkManagerActivity() {
        Intent intent = new Intent(this, WorkManagerActivity.class);
        startActivity(intent);
    }

    private void startService() {
        Intent intent = new Intent(this, SimpleService.class);
        startService(intent);
    }

    private void startNoNotificationStartService(){
        Intent intent = new Intent(this, NoNotificationForegroudService.class);
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
