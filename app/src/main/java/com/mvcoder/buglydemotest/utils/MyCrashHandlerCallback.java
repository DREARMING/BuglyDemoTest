package com.mvcoder.buglydemotest.utils;

import android.os.Build;

import com.mvcoder.buglydemotest.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

public class MyCrashHandlerCallback extends CrashReport.CrashHandleCallback {

    @Override
    public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
        Map<String, String> extraInfoMap = new HashMap<>();
        extraInfoMap.put("Android Version", Build.VERSION.RELEASE);
        String deviceInfo = Build.BRAND + " " + Build.MODEL;
        extraInfoMap.put("Device Info", deviceInfo);
        extraInfoMap.put("User", "110");
        extraInfoMap.put("Version", BuildConfig.VERSION_NAME);
        extraInfoMap.put("cpu",Build.CPU_ABI);
        return super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
    }
}
