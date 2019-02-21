package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class StudentDataSourceFactory extends DataSource.Factory<String, StudentBean> {

    private MutableLiveData<StudentDataSource> sourceLiveData;

    public MutableLiveData<StudentDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

    public void setSourceLiveData(MutableLiveData<StudentDataSource> sourceLiveData) {
        this.sourceLiveData = sourceLiveData;
    }

    @Override
    public DataSource<String, StudentBean> create() {
        StudentDataSource source = new StudentDataSource();
        sourceLiveData.postValue(source);
        return source;
    }
}
