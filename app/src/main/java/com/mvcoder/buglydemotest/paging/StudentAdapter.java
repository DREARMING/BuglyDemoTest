package com.mvcoder.buglydemotest.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvcoder.buglydemotest.R;

public class StudentAdapter extends PagedListAdapter<StudentBean, StudentViewHolder> {


    protected StudentAdapter(@NonNull DiffUtil.ItemCallback<StudentBean> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_paging, null);
        StudentViewHolder holder = new StudentViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        studentViewHolder.bindTo(getItem(i));
    }

    public static DiffUtil.ItemCallback diffCallback = new DiffUtil.ItemCallback<StudentBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull StudentBean studentBean, @NonNull StudentBean t1) {
            return studentBean.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StudentBean studentBean, @NonNull StudentBean t1) {
            return studentBean.getName().equals(t1.getName());
        }
    };



}
