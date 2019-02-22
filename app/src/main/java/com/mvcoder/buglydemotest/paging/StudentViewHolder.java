package com.mvcoder.buglydemotest.paging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.mvcoder.buglydemotest.R;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.paging_tv_name);
    }

    public void bindTo(StudentBean studentBean){
        textView.setText(studentBean.getName());
        if(!studentBean.getName().contains("@")){
            LogUtils.d("发现没有@ 的学生");
        }
    }


}
