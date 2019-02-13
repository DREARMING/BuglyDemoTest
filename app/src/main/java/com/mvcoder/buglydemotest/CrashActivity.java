package com.mvcoder.buglydemotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrashActivity extends AppCompatActivity {

    private static final String TAG = CrashActivity.class.getSimpleName();

    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.btJavaCrash)
    Button btJavaCrash;
    @BindView(R.id.btNativeCrash)
    Button btNativeCrash;
    @BindView(R.id.btJavaScriptCrash)
    Button btJavaScriptCrash;
    @BindView(R.id.btANR)
    Button btANR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);
    }

    public native void throwNativeException();

    public native String stringFromJNI();


    private void javaCrash() {
        String hello = null;
        log("hello string length : " + hello.length());
    }

    private void nativeCrash() {
        log(stringFromJNI());
        log("call native method !! 将会发生除0异常");
        throwNativeException();
    }


    private void javascriptCrash() {

    }


    private void testANR() {
        try {
            Thread.sleep(10000);
            log("sleep 6 seconds to response user");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String log) {
        Log.d(TAG, log);
    }

    @OnClick({R.id.btJavaCrash, R.id.btNativeCrash, R.id.btJavaScriptCrash, R.id.btANR})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btJavaCrash:
                javaCrash();
                break;
            case R.id.btNativeCrash:
                nativeCrash();
                break;
            case R.id.btJavaScriptCrash:
                javascriptCrash();
                break;
            case R.id.btANR:
                testANR();
                break;
        }
    }
}