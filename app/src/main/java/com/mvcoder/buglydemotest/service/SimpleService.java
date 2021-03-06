package com.mvcoder.buglydemotest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

public class SimpleService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("service , onstartcommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("service on destroy");
    }
}
