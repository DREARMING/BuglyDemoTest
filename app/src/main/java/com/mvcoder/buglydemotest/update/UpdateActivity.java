package com.mvcoder.buglydemotest.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvcoder.buglydemotest.BuildConfig;
import com.mvcoder.buglydemotest.R;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends AppCompatActivity {

    @BindView(R.id.btCheck)
    Button btCheck;
    @BindView(R.id.btInfo)
    Button btInfo;
    @BindView(R.id.tvUpdateInfo)
    TextView tvUpdateInfo;
    @BindView(R.id.btNewActviity)
    Button btNewActviity;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StringBuilder builder = new StringBuilder();
        builder.append("当前版本号：" + BuildConfig.VERSION_NAME);
        tvUpdateInfo.setText(builder.toString());
    }

    @OnClick({R.id.btCheck, R.id.btInfo,R.id.btNewActviity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btCheck:
                checkUpdate();
                break;
            case R.id.btInfo:
                printUpdateInfo();
                break;
            case R.id.btNewActviity:
                joinToNewActivity();
                break;
        }
    }

    private void joinToNewActivity(){
        Intent intent = new Intent(this, UpdateTestActivity.class);
        startActivity(intent);
    }

    private void printUpdateInfo() {
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        StringBuilder builder = new StringBuilder();
        if (upgradeInfo != null) {
            builder.append("更新标题：" + upgradeInfo.title);
            builder.append("新版本号：" + upgradeInfo.versionName);
            builder.append("安装包大小：" + upgradeInfo.fileSize);
            builder.append("新特性：" + upgradeInfo.newFeature);
        }
        if (tvUpdateInfo != null) {
            tvUpdateInfo.setText("新安装包信息：\n" + builder.toString());
        }
    }

    private void checkUpdate() {
        Beta.strToastYourAreTheLatestVersion = "当前版本已经最新";
        Beta.checkUpgrade();
    }

}
