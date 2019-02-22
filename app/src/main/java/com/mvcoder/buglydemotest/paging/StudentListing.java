package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.LiveData;

public class StudentListing<T> extends Listing<T> {

    private LiveData<StudentDataSource> dataSource;

    public LiveData<StudentDataSource> getDataSource() {
        return dataSource;
    }

    public void setDataSource(LiveData<StudentDataSource> dataSource) {
        this.dataSource = dataSource;
    }

    public void retry(){
        if(dataSource.getValue() != null) {
            dataSource.getValue().retry();
        }
    }

    public void refresh(){
        if(dataSource.getValue() != null) {
            dataSource.getValue().invalidate();
        }
    }


}
