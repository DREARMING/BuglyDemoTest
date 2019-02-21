package com.mvcoder.buglydemotest.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RetryWorker extends Worker {

    private final static String KEY_RETRY = "key_retry_worker";

    public RetryWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        boolean retry = SPUtils.getInstance().getBoolean(KEY_RETRY, true);
        if(retry){
            LogUtils.d("retry worker now");
            SPUtils.getInstance().put(KEY_RETRY, false);
            return Result.retry();
        }
        LogUtils.d("execute retry worker success");
        return Result.success();
    }
}
