#include <jni.h>
#include <string>
using namespace std;

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
        int b1 = 0;
        int result = a1 / b1;
        string test = "hello";
        char c = test.at(6);
        printf("%s", c);
        printf("%d / %d == %s" , a1, b1, result);
    }

JNIEXPORT jstring JNICALL Java_com_mvcoder_buglydemotest_CrashActivity_stringFromJNI(
        JNIEnv *env,
        jobject  /*this*/){
    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
}


