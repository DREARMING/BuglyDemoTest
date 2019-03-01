#include <jni.h>
#include <string>
#include <android/log.h>
using namespace std;

#define TAG "JNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型

extern "C" {
    JNIEXPORT jstring JNICALL Java_com_mvcoder_buglydemotest_MainActivity_stringFromJNI(
            JNIEnv *env,
            jobject /* this */) {
        string hello = "Hello from C++";
        return env->NewStringUTF(hello.c_str());
    }

    JNIEXPORT void JNICALL Java_com_mvcoder_buglydemotest_CrashActivity_throwNativeException(
            JNIEnv *env,
            jobject  /*this*/){

        int a1 = 10;
        int b1 = 2;
        int result = a1 / b1;
        /*string test = "hello";
        char c = test.at(6);
        printf("%s", c);
        printf("%d / %d == %s" , a1, b1, result);*/
        LOGD("result: %d", result);
    }

JNIEXPORT jstring JNICALL Java_com_mvcoder_buglydemotest_CrashActivity_stringFromJNI(
        JNIEnv *env,
        jobject  /*this*/){
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
}


