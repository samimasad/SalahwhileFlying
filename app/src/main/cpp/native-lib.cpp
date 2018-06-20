#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_net_ddns_samimasad_salahwhileflying_MainScreen_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
