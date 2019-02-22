package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.greendao.StudentBeanDao;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StudentDataSource extends ItemKeyedDataSource<String, StudentBean> {

    private int startPos = 0;

    private MutableLiveData<Resource<String>> networkState = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> initialLoad = new MutableLiveData<>();

    private LoadInitialCallback<StudentBean> callback;

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
        LogUtils.d("load init -- skip " + startPos + " , count : " + params.requestedLoadSize);
        this.callback = callback;
        networkState.postValue(Resource.loading("loading", ""));
        initialLoad.postValue(Resource.loading("loading", ""));

        LogUtils.d("insert flag " + insertFlag);
        List<StudentBean> list = loadData(startPos, params.requestedLoadSize);

        networkState.postValue(Resource.success(""));
        initialLoad.postValue(Resource.success(""));

        callback.onResult(list);
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
        return item.getId() + "";
    }

    public void retry(){
        LogUtils.d("retry now");

    }

    private List<StudentBean> loadData(int startPos, int limit){
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if(insertFlag){
            insertFlag = false;
            list.add(new StudentBean("-1","学生@新插入"));
            LogUtils.d("新增 " + 1 + "条数据");
            limit -= 1;
        }*/

        StudentBeanDao dao = DBUtil.getInstance().getDaoSession().getStudentBeanDao();
        List<StudentBean> list = dao.queryBuilder().orderDesc(StudentBeanDao.Properties.Id)
                .offset(startPos)
                .limit(limit).list();

        /*for(int i = 0; i < limit; i++){
            int pos = startPos + i;
            StudentBean bean = new StudentBean(pos+"", ("学生@" + pos));
            list.add(bean);
        }*/

        return list;
    }

    private volatile boolean insertFlag = false;

    private volatile CopyOnWriteArrayList<StudentBean> appendList = new CopyOnWriteArrayList<>();

    public void addStudentItem(StudentBean studentBean){
        insertFlag = true;
        appendList.add(studentBean);
        LogUtils.d("append list size : " + appendList.size());
    }

    public void updateSource(List<StudentBean> list){
       /* if(callback != null){
            callback.onResult(list);
        }*/
    }


}
