package com.mvcoder.buglydemotest.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.R;

public class ForegroundSimpleServide extends Service {

    private volatile boolean running = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showForeNotification();
        LogUtils.d("foreground service start");
        new Thread(new PrintRunnable()).start();
        return START_STICKY;
    }

    class PrintRunnable implements Runnable{

        @Override
        public void run() {
            while(running){
                try {
                    Thread.sleep(5000);
                    LogUtils.d("foreground service running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showForeNotification(){
        String channelId = getString(R.string.channel_id);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

        Notification notification = builder.setContentTitle("Bugly前台服务")
                .setContentText("这是一个前台服务测试通知例子")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        startForeground(10, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        LogUtils.d("foreground service ondestroy");
    }
}
