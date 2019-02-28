package com.mvcoder.buglydemotest;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.mvcoder.buglydemotest.paging.DBUtil;
import com.mvcoder.buglydemotest.utils.BuglyUtil;
import com.mvcoder.buglydemotest.utils.MyCrashHandlerCallback;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;

public class BuglyDemoApplication extends Application {

    private static final String APP_ID = "547d84889a";

    private static final String tag = BuglyDemoApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //会在比onCreate方法先执行
        Log.e(tag, "attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(tag, "oncreate");
        Utils.init(this);
        DBUtil.getInstance().init(getApplicationContext(),"paging.db");
        initBugly();
    }

    private void initBugly() {

       BuglyStrategy strategy = new BuglyStrategy();

        //设置上报进程，这里只设置主进程，主进程的进程名和包名一致
        String packageName = getPackageName();
        String processName = BuglyUtil.getProcessName(Process.myPid());
        LogUtils.d("main process name's " + processName);
        if(packageName.equals(processName)) {
            strategy.setUploadProcess(true);
        }else{
            strategy.setUploadProcess(false);
        }

        strategy.setAppPackageName(packageName);
        strategy.setAppVersion(BuildConfig.VERSION_NAME);

        //用美团 Walle 方案配置渠道信息
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(getApplicationContext());
        if(channelInfo != null){
            String channelName = channelInfo.getChannel();
            strategy.setAppChannel(channelName);
        }

        strategy.setEnableANRCrashMonitor(true);
        strategy.setCrashHandleCallback(new MyCrashHandlerCallback());

        //升级配置
        Beta.autoInit = true;   //默认就是true

        Beta.upgradeDialogLayoutId = R.layout.dialog_update;



        /*
		 * 设置sd卡的Download为更新资源保存目录;
		 * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
		 */
        Beta.storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        Beta.upgradeCheckPeriod = 10 * 1000;

        Bugly.init(this, APP_ID, BuildConfig.DEBUG, strategy);
    }
}
