package com.mvcoder.buglydemotest.workmanager;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.R;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkManagerActivity extends AppCompatActivity {

    @BindView(R.id.btSimpleTask)
    Button btSimpleTask;
    @BindView(R.id.btChainTask)
    Button btChainTask;
    @BindView(R.id.btWifiTask)
    Button btWifiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btSimpleTask, R.id.btChainTask, R.id.btWifiTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSimpleTask:
                createSimpleTask();
                break;
            case R.id.btChainTask:
                createChainTask();
                break;
            case R.id.btWifiTask:
                createRetryWorker();
                break;
        }
    }

    private void createSimpleTask(){
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(SimpleWork.class);
        Constraints constraints = new Constraints.Builder()
                //.setRequiredNetworkType(NetworkType.METERED) //收费网络，即移动网络
                .setRequiredNetworkType(NetworkType.UNMETERED)  //wifi,免费网络

                .build();
        OneTimeWorkRequest request = builder
                .setInitialDelay(10, TimeUnit.SECONDS)      //..延迟10秒启动
                .setConstraints(constraints)
                .setInputData(new Data.Builder()
                        .putString("data","simple task : 赶紧给我启动Simple Service")
                        .build())
                .build();

        WorkManager.getInstance().enqueue(request);

        UUID uuid = request.getId();

        WorkManager.getInstance().getWorkInfoByIdLiveData(uuid).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if(workInfo.getState().isFinished()){
                    Data outputData = workInfo.getOutputData();
                    String result = outputData.getString("result");
                    LogUtils.d("simple task finish : " + result);
                }
            }
        });

    }

    private void createChainTask(){
        OneTimeWorkRequest request1 = new OneTimeWorkRequest.Builder(SimpleWork.class)
                .setInitialDelay(5, TimeUnit.SECONDS)      //..延迟10秒启动
                .setInputData(new Data.Builder()
                        .putString("data","simple task : 赶紧给我启动Simple Service")
                        .build())
                .build();


        OneTimeWorkRequest request2 = new OneTimeWorkRequest.Builder(ChainWorker.class)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance()
                .beginWith(request1)
                .then(request2)
                .enqueue();

        WorkManager.getInstance().getWorkInfoByIdLiveData(request2.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if(workInfo.getState().isFinished()){
                    Data outputData = workInfo.getOutputData();
                    String result = outputData.getString("result");
                    LogUtils.d("simple task finish : " + result);
                }
            }
        });

    }


    private void createRetryWorker(){
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(RetryWorker.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS);
        }
        OneTimeWorkRequest request = builder.build();
        WorkManager.getInstance().enqueue(request);

        UUID requestId = request.getId();

        WorkManager.getInstance().getWorkInfoByIdLiveData(requestId).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if(workInfo != null){
                    LogUtils.d("work state : " + workInfo.getState().toString());
                }
            }
        });

    }




}
