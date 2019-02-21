package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource extends ItemKeyedDataSource<String, StudentBean> {

    private int startPos = 0;

    private MutableLiveData<Resource<String>> networkState = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> initialLoad = new MutableLiveData<>();

    public MutableLiveData<Resource<String>> getNetworkState() {
        return networkState;
    }

    public void setNetworkState(MutableLiveData<Resource<String>> networkState) {
        this.networkState = networkState;
    }

    public MutableLiveData<Resource<String>> getInitialLoad() {
        return initialLoad;
    }

    public void setInitialLoad(MutableLiveData<Resource<String>> initialLoad) {
        this.initialLoad = initialLoad;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<StudentBean> callback) {
        LogUtils.d("load init");
        networkState.postValue(Resource.loading("loading", ""));
        initialLoad.postValue(Resource.loading("loading", ""));

        List<StudentBean> list = loadData(startPos, params.requestedLoadSize);

        networkState.postValue(Resource.success(""));
        initialLoad.postValue(Resource.success(""));

        callback.onResult(list, startPos, list.size());
        startPos += list.size();

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<StudentBean> callback) {
        LogUtils.d("load after -- skip " + startPos + " , count : " + params.requestedLoadSize);

        networkState.postValue(Resource.loading("loading", ""));
        initialLoad.postValue(Resource.loading("loading", ""));

        List<StudentBean> list = loadData(startPos, params.requestedLoadSize);

        networkState.postValue(Resource.success(""));
        initialLoad.postValue(Resource.success(""));

        callback.onResult(list);
        startPos += list.size();


    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<StudentBean> callback) {
        LogUtils.d("load before");
    }

    @NonNull
    @Override
    public String getKey(@NonNull StudentBean item) {
        return item.getId();
    }

    private List<StudentBean> loadData(int startPos, int limit){
        List<StudentBean> list = new ArrayList<>();
        for(int i = 0; i < limit; i++){
            int pos = startPos + i;
            StudentBean bean = new StudentBean(pos+"", ("学生@" + pos));
            list.add(bean);
        }
        return list;
    }


}
