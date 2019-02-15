package com.mvcoder.buglydemotest.workmanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
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
        startService();

        Data outputData = new Data.Builder()
                .putString("result", "simple task 结果返回")
                .build();

        return Result.success(outputData);
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
