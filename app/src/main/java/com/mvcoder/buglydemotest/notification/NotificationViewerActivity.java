package com.mvcoder.buglydemotest.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationViewerActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_viewer);
        ButterKnife.bind(this);
        LogUtils.d("NotificationViewerActivity on create");
        getArgment(getIntent());
    }


    private void getArgment(Intent intent) {
        String notificationContent = intent.getStringExtra("content");
        if(!TextUtils.isEmpty(notificationContent)){
            tvContent.setText(notificationContent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(NotificationViewerActivity.class.getSimpleName() + "onDestroy");
    }
}
