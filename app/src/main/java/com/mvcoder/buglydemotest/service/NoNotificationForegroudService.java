package com.mvcoder.buglydemotest.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

public class NoNotificationForegroudService extends Service {

    private static int notificationId = 0x022;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //该方案在8.0上没用，通知还是会弹出来
        increasingPriority();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while (num <=40){
                    LogUtils.d("running now " + num);
                    num++;
                    try {
                        Thread.sleep(1000*3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void increasingPriority() {
        /*String channelId = getString(R.string.channel_id);
        String channelName = getString(R.string.channel_name);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                manager.createNotificationChannel(notificationChannel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,intent , PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = builder.setContentTitle("Bugly前台服务")
                .setContentText("这是一个前台服务测试通知例子")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();*/
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(notificationId, new Notification());
        } else {
            startForeground(notificationId, new Notification());
            // start InnerService
            startService(new Intent(this, InnerService.class));
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class InnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            try {
                /*String channelId = getString(R.string.channel_id);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,intent , PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = builder.setContentTitle("Bugly前台服务")
                        .setContentText("这是一个前台服务测试通知例子")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();*/
                startForeground(notificationId,new Notification());
            } catch (Throwable e) {
                LogUtils.e("InnerService set service for push exception:%s.", e);
            }
            stopSelf();
        }

        @Override
        public void onDestroy() {
            stopForeground(true);
            super.onDestroy();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
