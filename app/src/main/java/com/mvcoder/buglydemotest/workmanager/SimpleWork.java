package com.mvcoder.buglydemotest.workmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.mvcoder.buglydemotest.MainActivity;
import com.mvcoder.buglydemotest.R;
import com.mvcoder.buglydemotest.notification.NotificationViewerActivity;
import com.mvcoder.buglydemotest.service.ForegroundSimpleService;
import com.mvcoder.buglydemotest.service.SimpleService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SimpleWork extends Worker {


    public SimpleWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String arg =  data.getString("data");
        LogUtils.d("time : " + getTime() + ",  传进来的参数是：" + arg);

        //随便启动一个服务
        //如果应用程序处于后台的话，将会发生 Runtime 异常。
        //startService();

        //如果应用程序处于后台的话，可以启动前台服务而不是普通服务。
        //如文档所说，通过这种方式启动服务后，必须在服务中调用前台通知，否则会发生错误
        startForegroundService();

        //应用程序处于后台的话，可以弹通知。
        //showNotification();


        Data outputData = new Data.Builder()
                .putString("result", "simple task 结果返回")
                .build();

        return Result.success(outputData);
    }

    private void startForegroundService() {
        Intent intent = new Intent(getApplicationContext(), ForegroundSimpleService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getApplicationContext().startForegroundService(intent);
        }else{
            getApplicationContext().startService(intent);
        }
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager == null) return;

        NotificationCompat.Builder builder =  null;


        String channelId = getApplicationContext().getString(R.string.channel_id);
        String channelName = getApplicationContext().getString(R.string.channel_name);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel temp = manager.getNotificationChannel(channelId);
            if(temp == null){
                temp = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                temp.enableLights(true);
                temp.setLightColor(Color.GREEN);
                manager.createNotificationChannel(temp);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(), temp.getId());
        }else{
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("notification",true);
        intent.putExtra("targetActivity", NotificationViewerActivity.class.getName());
        intent.putExtra("content", "hello , this is bugly notification content");

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


       /* TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(NotificationViewerActivity.class);
        taskStackBuilder.addNextIntent(intent);*/

        // PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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

        manager.notify(10, notification);
    }


    private void startService(){
        Intent intent = new Intent(getApplicationContext(), SimpleService.class);
        getApplicationContext().startService(intent);
    }

    private String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return TimeUtils.date2String(Calendar.getInstance().getTime(), formatter);
    }

}
