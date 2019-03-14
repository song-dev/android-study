//
// Created by 陈颂颂 on 2019/3/5.
//

#include <stdio.h>
#include <string>
#include <sys/system_properties.h>
#include <android/log.h>
#include <jni.h>

#define TAG "JNI_TAG"

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_MainActivity_test(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "test";
    return env->NewStringUTF(hello.c_str());
}


//string getImei();
//
//string getImei() {
//    char imei_start[64] = {0};
//    char g_imei[64] = {0};
//    int ir = __system_property_get("ro.gsm.imei", imei_start);
//    __android_log_print(ANDROID_LOG_ERROR, TAG, "imei-->%d", ir);
//    return "";
//}

/**
 * TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
 * tm.getDeviceId();
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_MainActivity_getIMEI(JNIEnv *env, jobject context) {

    string error_string = "";

    if (context == NULL) {
        error_string = "[+]Error : Context is 0";
        return env->NewStringUTF(error_string.c_str());
    }

    jclass cls_context = env->FindClass("android/content/Context");
    if (cls_context == NULL) {
        error_string = "[+] Error: FindClass <android/content/Context> Error";
        return env->NewStringUTF(error_string.c_str());
    }

    jmethodID getSystemService = env->GetMethodID(cls_context, "getSystemService",
                                                  "(Ljava/lang/String;)Ljava/lang/Object;");
    if (getSystemService == NULL) {
        return env->NewStringUTF("[+] Error : GetMethodID failed");
    }

    jfieldID TELEPHONY_SERVICE = env->GetStaticFieldID(cls_context, "TELEPHONY_SERVICE",
                                                       "Ljava/lang/String;");
    if (TELEPHONY_SERVICE == NULL) {
        return env->NewStringUTF("[+] Error : GetStaticFieldID failed");
    }

    jstring str = (jstring) env->GetStaticObjectField(cls_context, TELEPHONY_SERVICE);
    jobject telephonymanager = env->CallObjectMethod(context, getSystemService, str);
    if (telephonymanager == NULL) {
        return env->NewStringUTF("[+] Error: CallObjectMethod failed");
    }

    jclass cls_TelephoneManager = env->FindClass("android/telephony/TelephonyManager");
    if (cls_TelephoneManager == NULL) {
        return env->NewStringUTF("[+] Error: FindClass TelephoneManager failed");
    }

    jmethodID getDeviceId = (env->GetMethodID(cls_TelephoneManager, "getDeviceId",
                                              "()Ljava/lang/String;"));
    if (getDeviceId == NULL) {
        return env->NewStringUTF("[+] Error: GetMethodID getDeviceID failed");
    }

    jobject DeviceID = env->CallObjectMethod(telephonymanager, getDeviceId);
    return (jstring) DeviceID;
}


/**
 * Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID)
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_MainActivity_getAndroidId(JNIEnv *env, jobject context) {

    jclass contextClass = env->GetObjectClass(context);
    if (contextClass == NULL) {
        return NULL;
    }
    jmethodID getContentResolverMID = env->GetMethodID(contextClass, "getContentResolver",
                                                       "()Landroid/content/ContentResolver;");
    if (getContentResolverMID == NULL) {
        return NULL;
    }
    jobject contentResolver = env->CallObjectMethod(context, getContentResolverMID);
    if (contentResolver == NULL) {
        return NULL;
    }
    jclass settingsSecureClass = env->FindClass("android/provider/Settings$Secure");
    if (settingsSecureClass == NULL) {
        return NULL;
    }
    jmethodID getStringMID = env->GetStaticMethodID(settingsSecureClass, "getString",
                                                    "(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;");
    if (getStringMID == NULL) {
        return NULL;
    }
    jstring idStr = (jstring) env->NewStringUTF("android_id");
    jstring androidId = (jstring) env->CallStaticObjectMethod(settingsSecureClass, getStringMID,
                                                              contentResolver, idStr);
    return androidId;
}




