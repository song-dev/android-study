//
// Created by 陈颂颂 on 2019/3/11.
//

#include <jni.h>
#include <string>
#include <unistd.h>

using namespace std;

#define TAG "JNI_TAG"

// 执行shell命令
string getCmdResult(string &cmd);

string getCmdResult(string &cmd) {
    // 定义buf大小
    char buf[1024] = {0};
    FILE *pf = NULL;
    if ((pf = popen(cmd.c_str(), "r")) == NULL) {
        return NULL;
    }
    string resultStr;
    while (fgets(buf, sizeof buf, pf)) {
        resultStr += buf;
    }
    pclose(pf);

    unsigned int size = resultStr.size();
    if (size > 0 && resultStr[size - 1] == '\n') {
        // 去掉最后的换行
        resultStr = resultStr.substr(0, size - 1);
    }
    return resultStr;
}

std::string jstring2string(JNIEnv *env, jstring jStr);

std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                       env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte *pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *) pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

string getJavaShellResult(JNIEnv *env, string &cmd);

string getJavaShellResult(JNIEnv *env, string &cmd) {
    // 读取java方法

    jclass ShellHelperClass = env->FindClass("com/song/androidstudy/shell/ShellHelper");
    if (ShellHelperClass == NULL) {
        return "";
    }
    jmethodID executeShellMID = env->GetStaticMethodID(ShellHelperClass, "executeShell",
                                                       "(Ljava/lang/String;)Ljava/lang/String;");
    if (executeShellMID == NULL) {
        return "";
    }
    jstring cmdStr = (jstring) env->NewStringUTF(cmd.c_str());
    jstring result = (jstring) env->CallStaticObjectMethod(ShellHelperClass, executeShellMID,
                                                           cmdStr);
    if (result == NULL) {
        return "";
    }
    return jstring2string(env, result);

}


extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_getInfo(
        JNIEnv *env,
        jobject /* this */) {

    string result = "";
    string cmd = "";

    result.append("读取相关硬件信息");
    result.append("\n");

    // 读取包名
    pid_t pid = getpid();
    char packagePath[64] = {0};
    sprintf(packagePath, "cat /proc/%d/cmdline", pid);
    string packagePathStr = packagePath;
    result.append("package-->" + getCmdResult(packagePathStr));
    result.append("\n");

    cmd = "ip addr | sed -n '/wlan0/{n;p}' | sed 's/^.*ether//g' | sed 's/brd.*$//g' | sed 's/ //g'";
    result.append("mac地址-->" + getCmdResult(cmd));
    result.append("\n");

    cmd = "ifconfig lo | sed -nre '2s/[^0-9.]+/\\n/gp' | sed '/^[  ]*$/d' | sed -n '1p'";
    result.append("lo-->" + getCmdResult(cmd));
    result.append("\n");

    cmd = "ifconfig | sed '/Bcast/!d' | grep -w 'inet addr'| sed 's/^.*addr://g' | sed 's/Bcast.*$//g' | sed 's/ //g' | sed -n '1p'";
    result.append("eth0-->" + getCmdResult(cmd));
    result.append("\n");

    cmd = "ip -6 addr show";
    result.append("ifconfig-->" + getCmdResult(cmd));
    result.append("\n");

    cmd = "ifconfig | grep -w 'inet6 addr' | sed '/Scope: Link/!d' | sed 's/^.*addr://g' | sed 's/Scope.*$//g' | sed 's/ //g' | sed -n '1p'";
    result.append("ipv6-->" + getCmdResult(cmd));
    result.append("\n");

    // 读取内存
    cmd = "cat /proc/meminfo | grep 'MemTotal' | sed -ne 's/[^0-9]/\\n/gp' | sed '/^[  ]*$/d'";
    result.append("总共内存-->" + getCmdResult(cmd));
    result.append("\n");

    // 当前进程内存
    char memPath[128] = {0};
    sprintf(memPath,
            "cat /proc/%d/status | grep 'VmSize' | sed -ne 's/[^0-9]/\\n /gp' | sed '/^[  ]*$/d'",
            pid);
    string memStr = memPath;
    result.append("app进程内存-->" + getCmdResult(memStr));
    result.append("\n");

//    cmd = "getprop";
//    result.append("getprop-->" + getCmdResult(cmd));
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");
//    result.append("\n");

    cmd = "getprop ro.build.version.sdk";
    result.append("系统api版本-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.product.locale.language";
    result.append("语言-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.product.locale.region";
    result.append("地区-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.product.brand";
    result.append("设备类型-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.build.id";
    result.append("完全设备类型-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.height";
    result.append("屏幕高-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.width";
    result.append("屏幕宽-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop sys.rog.density";
    result.append("density-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.build.version.release";
    result.append("操作系统版本-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop gsm.sim.operator.alpha";
    result.append("运营商-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop gsm.network.type";
    result.append("网络类型-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop debug.aps.current_battery";
    result.append("当前电量-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop persist.sys.root.status";
    result.append("是否root，0未root-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop gsm.version.baseband";
    result.append("基带版本-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop gsm.rssi.sim1";
    result.append("rssi-->" + getCmdResult(cmd));
    result.append("\n");
    cmd = "getprop ro.dual.sim.phone";
    result.append("是否双卡-->" + getCmdResult(cmd));
    result.append("\n");

    return env->NewStringUTF(result.c_str());
}

