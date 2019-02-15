package com.mvcoder.buglydemotest.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ChainWorker extends Worker {

    public ChainWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String lastResult = getInputData().getString("result");
        LogUtils.d("time " + getTime() + "chain worker - last result is : " + lastResult);

        Data outputData = new Data.Builder()
                .putString("result", "hello ! this is chain worker!!")
                .build();
        return Result.success(outputData);
    }

    private String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return TimeUtils.date2String(Calendar.getInstance().getTime(), formatter);
    }

}
