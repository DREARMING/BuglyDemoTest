package com.mvcoder.buglydemotest.paging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.R;
import com.mvcoder.buglydemotest.greendao.StudentBeanDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class PagingActivity extends AppCompatActivity {


    @BindView(R.id.rv_student)
    RecyclerView recyclerView;
    @BindView(R.id.srf)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.bt_add)
    Button btAdd;

    private StudentViewModel viewModel;

    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initData() {
        viewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                StudentRepository repository = new StudentDataRepository();
                StudentViewModel model = new StudentViewModel(getApplication(), repository);
                return (T) model;
            }
        }).get(StudentViewModel.class);

        insertDataInDB();
    }

    private void insertDataInDB() {
        StudentBeanDao dao = DBUtil.getInstance().getDaoSession().getStudentBeanDao();
        List<StudentBean> list = new ArrayList<>();
        for(int i = 0 ; i < 100; i++){
            StudentBean bean = new StudentBean(i, "学生@" + i);
            list.add(bean);
        }
        dao.insertOrReplaceInTx(list);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//添加分割线

        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //viewModel.refresh();
            }
        });

        adapter = new StudentAdapter(StudentAdapter.diffCallback);
        recyclerView.setAdapter(adapter);

        registerObserver();

    }

    private void registerObserver(){
        viewModel.getRefreshState().observe(this, new Observer<Resource<String>>() {
            @Override
            public void onChanged(@Nullable Resource<String> stringResource) {
                if (stringResource == null) return;
                Resource.Status status = stringResource.getStatus();
                LogUtils.d("refresh state change");
                if (status == Resource.Status.SUCCESS) {
                    refreshLayout.setRefreshing(false);
                } else if (status == Resource.Status.ERROR) {
                    LogUtils.d(stringResource.getMessage());
                    refreshLayout.setRefreshing(false);
                } else if (status == Resource.Status.LOADING) {
                    refreshLayout.setRefreshing(true);
                }
            }
        });

        viewModel.getPosts().observe(this, new Observer<PagedList<StudentBean>>() {
            @Override
            public void onChanged(@Nullable PagedList<StudentBean> studentBeans) {
                // Gson gson = new GsonBuilder().setPrettyPrinting().create();
                //LogUtils.d("data set json:\n" + gson.toJson(studentBeans.snapshot()));
                LogUtils.d("data on change");
                adapter.submitList(studentBeans);
                AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(0);
                    }
                },100, TimeUnit.MILLISECONDS);
            }
        });
    }

    @OnClick(R.id.bt_add)
    public void onViewClicked() {
        //adapter.submitList(null);
        //viewModel.resetPagingLibrary();
        viewModel.addItem();

        //registerObserver();
    }
}
