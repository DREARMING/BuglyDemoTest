package com.mvcoder.buglydemotest.paging;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

public class StudentDataRepository implements StudentRepository {

    @Override
    public StudentListing<StudentBean> getStudentList(int pageSize) {
        StudentDataSourceFactory factory = new StudentDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(pageSize * 2)
                .build();
        LiveData<PagedList<StudentBean>> pagedList = new LivePagedListBuilder<String, StudentBean>(factory, config)
                .build();

        MutableLiveData<StudentDataSource> source = factory.getSourceLiveData();

        StudentListing<StudentBean> listing = new StudentListing<>();
        listing.setPageList(pagedList);

        LiveData<Resource<String>> refreshState = Transformations.switchMap(source, new Function<StudentDataSource, LiveData<Resource<String>>>() {
            @Override
            public LiveData<Resource<String>> apply(StudentDataSource input) {
                return input.getInitialLoad();
            }
        });

        LiveData<Resource<String>> networkState = Transformations.switchMap(source, new Function<StudentDataSource, LiveData<Resource<String>>>() {
            @Override
            public LiveData<Resource<String>> apply(StudentDataSource input) {
                return input.getNetworkState();
            }
        });

        listing.setDataSource(source);
        listing.setRefreshState(refreshState);
        listing.setNetworkState(networkState);

        return listing;
    }

}
