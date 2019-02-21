package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

public class StudentDataRepository implements StudentRepository {

    @Override
    public Listing<StudentBean> getStudentList(int pageSize) {
        StudentDataSourceFactory factory = new StudentDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(pageSize * 2)
                .build();
        LiveData<PagedList<StudentBean>> pagedList = new LivePagedListBuilder<String,StudentBean>(factory,config)
                .build();

        MutableLiveData<StudentDataSource> source = factory.getSourceLiveData();

        Listing<StudentBean> listing = new Listing<>();
        listing.setPageList(pagedList);

        return listing;
    }

}
