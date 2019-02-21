package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

public class Listing<T> {


    private LiveData<PagedList<T>> pageList;

    private LiveData<Resource<String>> networkState;

    private LiveData<Resource<String>> refreshState;


    public LiveData<PagedList<T>> getPageList() {
        return pageList;
    }

    public void setPageList(LiveData<PagedList<T>> pageList) {
        this.pageList = pageList;
    }

    public LiveData<Resource<String>> getNetworkState() {
        return networkState;
    }

    public void setNetworkState(LiveData<Resource<String>> networkState) {
        this.networkState = networkState;
    }

    public LiveData<Resource<String>> getRefreshState() {
        return refreshState;
    }

    public void setRefreshState(LiveData<Resource<String>> refreshState) {
        this.refreshState = refreshState;
    }


}
