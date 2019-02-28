package com.mvcoder.buglydemotest.update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mvcoder.buglydemotest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateTestActivity extends AppCompatActivity {

    @BindView(R.id.bt_test)
    Button btTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_test)
    public void onViewClicked() {
    }
}
