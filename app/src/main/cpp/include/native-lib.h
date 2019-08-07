//
// Created by chensongsong on 2019/8/7.
//

#ifdef __cplusplus
extern "C" {
#endif

#ifndef ANDROIDSTUDY_NATIVE_LIB_H
#define ANDROIDSTUDY_NATIVE_LIB_H

JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_getData(JNIEnv *env, jobject obj,
                                                           jobject context);

#endif //ANDROIDSTUDY_NATIVE_LIB_H

#ifdef __cplusplus
}
#endif
