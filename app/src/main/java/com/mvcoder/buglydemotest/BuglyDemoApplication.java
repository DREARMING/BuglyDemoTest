package com.mvcoder.buglydemotest;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;

public class BuglyDemoApplication extends Application {

    private static final String APP_ID = "547d84889a";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //initBugly();
    }

    private void initBugly() {

        BuglyStrategy strategy = new BuglyStrategy();

        Bugly.init(this, APP_ID, BuildConfig.DEBUG, strategy);
    }
}
