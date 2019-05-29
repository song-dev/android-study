//
// Created by chensongsong on 2019/5/22.
//

#include <jni.h>
#include <string>
#include <zconf.h>

#include "song-mobinfo.h"
//#include "song-tool.h"

string getData() {
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
    return path;
}

/**
 *
 * TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
 * tm.getDeviceId();
 *
 * @param env
 * @param context
 * @return
 */
jstring getImei(JNIEnv *env, jobject context) {

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
jstring getAndroidId(JNIEnv *env, jobject context) {

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

jstring getInfo(JNIEnv *env, jobject /* this */) {

    string result = "";
    string cmd = "";

    result.append("读取相关硬件信息");
    result.append("\n");

// 读取包名
    pid_t pid = getpid();
    char packagePath[64] = {0};
    sprintf(packagePath, "cat /proc/%d/cmdline", pid);
    string packagePathStr = packagePath;
    result.append("package-->" + shellExecute(packagePathStr));
    result.append("\n");

    cmd = "ip addr | sed -n '/wlan0/{n;p}' | sed 's/^.*ether//g' | sed 's/brd.*$//g' | sed 's/ //g'";
    result.append("mac地址-->" + shellExecute(cmd));
    result.append("\n");

    cmd = "ifconfig lo | sed -nre '2s/[^0-9.]+/\\n/gp' | sed '/^[  ]*$/d' | sed -n '1p'";
    result.append("lo-->" + shellExecute(cmd));
    result.append("\n");

    cmd = "ifconfig | sed '/Bcast/!d' | grep -w 'inet addr'| sed 's/^.*addr://g' | sed 's/Bcast.*$//g' | sed 's/ //g' | sed -n '1p'";
    result.append("eth0-->" + shellExecute(cmd));
    result.append("\n");

    cmd = "ip -6 addr show";
    result.append("ifconfig-->" + shellExecute(cmd));
    result.append("\n");

    cmd = "ifconfig | grep -w 'inet6 addr' | sed '/Scope: Link/!d' | sed 's/^.*addr://g' | sed 's/Scope.*$//g' | sed 's/ //g' | sed -n '1p'";
    result.append("ipv6-->" + shellExecute(cmd));
    result.append("\n");

// 读取内存
    cmd = "cat /proc/meminfo | grep 'MemTotal' | sed -ne 's/[^0-9]/\\n/gp' | sed '/^[  ]*$/d'";
    result.append("总共内存-->" + shellExecute(cmd));
    result.append("\n");

// 当前进程内存
    char memPath[128] = {0};
    sprintf(memPath,
            "cat /proc/%d/status | grep 'VmSize' | sed -ne 's/[^0-9]/\\n /gp' | sed '/^[  ]*$/d'",
            pid);
    string memStr = memPath;
    result.append("app进程内存-->" + shellExecute(memStr));
    result.append("\n");

//    cmd = "getprop";
//    result.append("getprop-->" + shellExecute(cmd));
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");

    cmd = "getprop ro.build.version.sdk";
    result.append("系统api版本-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.product.locale.language";
    result.append("语言-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.product.locale.region";
    result.append("地区-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.product.brand";
    result.append("设备类型-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.build.id";
    result.append("完全设备类型-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.height";
    result.append("屏幕高-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.width";
    result.append("屏幕宽-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.density";
    result.append("density-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.build.version.release";
    result.append("操作系统版本-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop gsm.sim.operator.alpha";
    result.append("运营商-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop gsm.network.type";
    result.append("网络类型-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop debug.aps.current_battery";
    result.append("当前电量-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop persist.sys.root.status";
    result.append("是否root，0未root-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop gsm.version.baseband";
    result.append("基带版本-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop gsm.rssi.sim1";
    result.append("rssi-->" + shellExecute(cmd));
    result.append("\n");
    cmd = "getprop ro.dual.sim.phone";
    result.append("是否双卡-->" + shellExecute(cmd));
    result.append("\n");

    return env->NewStringUTF(result.c_str());
}
