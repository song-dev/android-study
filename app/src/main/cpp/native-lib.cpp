#include <jni.h>
#include <string>

#include "song-mobinfo.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_getData(JNIEnv *env, jobject obj,
                                                           jobject context) {

    string data = getData();

    return env->NewStringUTF(data.c_str());
}
