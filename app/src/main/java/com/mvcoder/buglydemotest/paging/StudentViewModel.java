package com.mvcoder.buglydemotest.paging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.mvcoder.buglydemotest.greendao.StudentBeanDao;

public class StudentViewModel extends AndroidViewModel {

    private static final int PAGE_SIZE = 10;

    private StudentRepository repository;

    private StudentListing<StudentBean> repoResult;

    private LiveData<PagedList<StudentBean>> posts;

    private LiveData<Resource<String>> networkState;

    private LiveData<Resource<String>> refreshState;

    public StudentViewModel(@NonNull Application application){
        super(application);
    }

    public StudentViewModel(@NonNull Application application, @NonNull StudentRepository studentRepository){
        super(application);
        this.repository = studentRepository;
        repoResult = repository.getStudentList(PAGE_SIZE);
        posts = repoResult.getPageList();
        networkState = repoResult.getNetworkState();
        refreshState = repoResult.getRefreshState();
    }

    public void setRepository(@NonNull StudentRepository studentRepository){
        this.repository = studentRepository;
    }

    public void resetPagingLibrary(){
        repoResult = repository.getStudentList(PAGE_SIZE);
        posts = repoResult.getPageList();
        networkState = repoResult.getNetworkState();
        refreshState = repoResult.getRefreshState();
    }

    public void refresh(){
        if(repoResult != null && repoResult.getDataSource().getValue() != null) {
            repoResult.getDataSource().getValue().invalidate();
        }
    }

    public void retry(){
        if(repoResult != null && repoResult.getDataSource() != null){
            repoResult.getDataSource().getValue().retry();
        }
    }

    private void showData(){

    }

    public void addItem(){
        if(repoResult.getDataSource().getValue() != null && posts.getValue() != null) {
            //List<StudentBean> list = new ArrayList<>();
           // list.add(studentBean);
            //list.addAll(posts.getValue().snapshot());
           // repoResult.getDataSource().getValue().updateSource(list);
            StudentBeanDao dao = DBUtil.getInstance().getDaoSession().getStudentBeanDao();
            int size =  dao.queryBuilder().list().size();
            StudentBean studentBean = new StudentBean( size, "学生@" + size);
            dao.insert(studentBean);
            //repoResult.getDataSource().getValue().addStudentItem(studentBean);
            repoResult.getDataSource().getValue().invalidate();
        }
    }


    public StudentListing<StudentBean> getRepoResult() {
        return repoResult;
    }

    public void setRepoResult(StudentListing<StudentBean> repoResult) {
        this.repoResult = repoResult;
    }

    public LiveData<PagedList<StudentBean>> getPosts() {
        return posts;
    }

    public void setPosts(LiveData<PagedList<StudentBean>> posts) {
        this.posts = posts;
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
