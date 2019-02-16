package com.mvcoder.buglydemotest.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.MainActivity;
import com.mvcoder.buglydemotest.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @BindString(R.string.channel_id)
    String channelId;
    @BindString(R.string.channel_name)
    String channelName;

    @BindView(R.id.bt_simple_notification)
    Button btSimpleNotification;
    @BindView(R.id.bt_notification_back_stack)
    Button btNotificationBackStack;
    @BindView(R.id.bt_channel_notification)
    Button btChannelNotification;

    private final int notification_id = 11;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showSimpleTextNotification();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        LogUtils.d("NotificationActivity on create");
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_simple_notification, R.id.bt_notification_back_stack, R.id.bt_channel_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_simple_notification:
                mHandler.sendMessageDelayed(Message.obtain(mHandler,1),5 * 1000);
                break;
            case R.id.bt_notification_back_stack:
                deleteChannel();
                break;
            case R.id.bt_channel_notification:
                showTaskStackNotification();
                break;
        }
    }

    private void deleteChannel(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager == null) return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel temp = manager.getNotificationChannel(channelId);
            if(temp != null){
                manager.deleteNotificationChannel(temp.getId());
            }
        }
    }

    private void showSimpleTextNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager == null) return;

        NotificationCompat.Builder builder =  null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel temp = manager.getNotificationChannel(channelId);
            if(temp == null){
                temp = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                temp.enableLights(true);
                temp.setLightColor(Color.GREEN);
                manager.createNotificationChannel(temp);
            }
            builder = new NotificationCompat.Builder(this, temp.getId());
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification",true);
        intent.putExtra("targetActivity", NotificationViewerActivity.class.getName());
        intent.putExtra("content", "hello , this is bugly notification content");

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


       /* TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(NotificationViewerActivity.class);
        taskStackBuilder.addNextIntent(intent);*/

       // PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = builder.setContentTitle("Bugly通知测试")
                .setContentText("Bugly notification content")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                //.setTimeoutAfter(10000)  //通知发送出去后，10秒后自动取消
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        manager.notify(notification_id, notification);
    }

    private void showTaskStackNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager == null) return;

        NotificationCompat.Builder builder =  null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel temp = manager.getNotificationChannel(channelId);
            if(temp == null){
                temp = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                temp.enableLights(true);
                temp.setLightColor(Color.GREEN);
                manager.createNotificationChannel(temp);
            }
            builder = new NotificationCompat.Builder(this, temp.getId());
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        /*Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification",true);
        intent.putExtra("targetActivity", NotificationViewerActivity.class.getName());
        intent.putExtra("content", "hello , this is bugly notification content");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
*/

        Intent intent = new Intent(this, NotificationViewerActivity.class);
        intent.putExtra("content", "hello , this is bugly notification content");

        /*TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(NotificationViewerActivity.class);
        taskStackBuilder.addNextIntent(intent);

         PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = builder.setContentTitle("Bugly通知测试")
                .setContentText("Bugly notification content")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                //.setTimeoutAfter(10000)  //通知发送出去后，10秒后自动取消
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        manager.notify(notification_id, notification);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("notification on new intent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(NotificationActivity.class.getSimpleName() + "onDestroy");
    }
}
