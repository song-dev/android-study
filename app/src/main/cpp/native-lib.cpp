#include <jni.h>
#include <string>

#include "include/native-lib.h"
#include "include/song-mobinfo.h"

JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_getData(JNIEnv *env, jobject obj,
                                                           jobject context) {

    string data = getData();
    return env->NewStringUTF(data.c_str());
}
