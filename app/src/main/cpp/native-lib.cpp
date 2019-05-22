#include <jni.h>
#include <string>
#include <android/log.h>
#include <iostream>
#include <sys/system_properties.h>
#include <unistd.h>
#include <sys/wait.h>

using namespace std;

#define TAG "JNI_TAG"

extern "C" JNIEXPORT jstring JNICALL
Java_com_song_androidstudy_testcpp_TestCppActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

std::string getPackageName();

std::string getPackageName() {

    pid_t pid = getpid();
    __android_log_print(ANDROID_LOG_ERROR, TAG, "process id %d\n", pid);
    char path[64] = {0};
    sprintf(path, "/proc/%d/cmdline", pid);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "path-->%s", path);
    FILE *cmdline = fopen(path, "r");
    if (cmdline) {
        char application_id[64] = {0};
        fread(application_id, sizeof(application_id), 1, cmdline);
//        __android_log_print(ANDROID_LOG_ERROR, TAG, "application id %s\n", application_id);
        fclose(cmdline);
        return application_id;
    }
    return "";
}

int chmod(const char *cmdstring);

int chmod(const char *cmdstring) {
    pid_t pid;
    int status;
    if (cmdstring == NULL) {
        return (1);
    }

    if ((pid = fork()) < 0) {
        status = -1;
    } else if (pid == 0) {
        execl("/system/bin/sh", "sh", "-c", cmdstring, (char *) 0);
    } else {
        while (waitpid(pid, &status, 0) < 0) {
            break;
        }
    }
    return status;
}

int mysystem(char *cmd) {
    pid_t status;

    status = system(cmd);
    if (-1 == status) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "system error! status=%d\n", status);
    } else {
        if (WIFEXITED(status)) {
            if (0 == WEXITSTATUS(status)) {
                __android_log_print(ANDROID_LOG_ERROR, TAG, "run shell script successfully.\n");
                return 0;
            } else {
                __android_log_print(ANDROID_LOG_ERROR, TAG,
                                    "run script fail, script exit code: %d\n", WEXITSTATUS(status));
                return WEXITSTATUS(status);
            }
        } else {
            __android_log_print(ANDROID_LOG_ERROR, TAG, "exit  %d\n", WEXITSTATUS(status));
            return WEXITSTATUS(status);
        }
    }
}

string getCmdResult(string &strCmd) {
    char buf[10240] = {0};
    FILE *pf = NULL;

    if ((pf = popen(strCmd.c_str(), "r")) == NULL) {
        return "";
    }

    string strResult;
    while (fgets(buf, sizeof buf, pf)) {
        strResult += buf;
    }

    pclose(pf);

    unsigned int iSize = strResult.size();
    if (iSize > 0 && strResult[iSize - 1] == '\n')  // linux
    {
        strResult = strResult.substr(0, iSize - 1);
    }

    return strResult;
}

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

    std::string packageName = getPackageName();
    __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", packageName.c_str());

    char pwd[64] = "ls";
    chmod(pwd);

    mysystem(pwd);

    pid_t pid = getpid();
//    __android_log_print(ANDROID_LOG_ERROR, TAG, "process id %d\n", pid);
    char path[64] = {0};
    sprintf(path, "/proc/%d/cmdline", pid);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "path-->%s", path);

    string test = path;

    string ls = "adb shell dumpsys battery";
    string result = getCmdResult(ls);
    __android_log_print(ANDROID_LOG_ERROR, TAG, "result-->%s", result.c_str());

    return env->NewStringUTF(hello.c_str());
}
