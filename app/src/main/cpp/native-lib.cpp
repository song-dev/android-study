#include <jni.h>
#include <string>
#include <android/log.h>
#include <iostream>
#include <sys/system_properties.h>
#include <unistd.h>
#include <sys/wait.h>

#include "song-mobinfo.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_getData(JNIEnv *env, jobject obj, jobject context) {
    std::string hello = "getData";
    __android_log_print(ANDROID_LOG_ERROR, TAG,
                        "Java_com_song_androidstudy_MainActivity_getData");
    // 导入android头文件，获取对应信息
    int a = system("cat /proc/meminfo");
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%d 测试", a);
    //读取model
    char model[] = "";
    __system_property_get("ro.product.model", model);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", model);

    char version[] = "";
    __system_property_get("ro.build.version.sdk", version);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", version);

    char serialno[] = "";
    __system_property_get("ro.serialno", serialno);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "serialno-->%s", serialno);

    char brand[] = "";
    __system_property_get("ro.product.brand", brand);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", brand);

    char release[] = "";
    __system_property_get("ro.build.version.release", release);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", release);

    char lang[] = "";
    __system_property_get("user.language", lang);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "lang-->%s", lang);
    char country[] = "";
    __system_property_get("user.country", country);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "country-->%s", country);

    pid_t pid = getpid();
//    __android_log_print(ANDROID_LOG_ERROR, TAG, "process id %d\n", pid);
    char path[64] = {0};
    sprintf(path, "/proc/%d/cmdline", pid);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "path-->%s", path);

    string test = path;

    return env->NewStringUTF(hello.c_str());
}
