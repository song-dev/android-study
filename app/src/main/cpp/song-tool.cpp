//
// Created by chensongsong on 2019/5/22.
//

#include <jni.h>

#include <sys/stat.h>
#include <ctime>
#include <sys/time.h>
#include <zconf.h>
#include <dirent.h>
#include <sys/system_properties.h>
#include <wait.h>

#include "song-tool.h"

#define BUF_SIZE 512
#define BUF_SIZE_64 64

void errorCatch(JNIEnv *env) {
    if (env->ExceptionCheck()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
    }

}

/**
 * path: 路径
 * 返回值 1:文件存在; 0:文件不存在
 */
static int existsFile(const string &path) {
    int access_result = access(path.c_str(), F_OK);
    if (access_result == -1) {
        return 0;
    } else {
        return 1;
    }
}

static string readFile(const string &path) {

    if (path.empty()) {
        return "";
    }
    char buf[BUF_SIZE] = {0};
    FILE *pf = NULL;
    if ((pf = fopen(path.c_str(), "r")) == NULL) {
        return "";
    }
    string resultStr = "";
    while (fgets(buf, sizeof(buf), pf)) {
        resultStr += buf;
    }
    fclose(pf);
    unsigned long size = resultStr.size();
    if (size > 0 && resultStr[size - 1] == '\n') {
        resultStr = resultStr.substr(0, size - 1);
    }
    return resultStr;
}

string shellExecute(const string &cmdStr) {
    char buf[128];
    FILE *pf = NULL;
    if ((pf = popen(cmdStr.c_str(), "r")) == NULL) {
        return "";
    }
    string resultStr = "";
    while (fgets(buf, sizeof(buf), pf)) {
        resultStr += buf;
    }
    unsigned long size = resultStr.size();
    if (size > 0 && resultStr[size - 1] == '\n') {
        resultStr = resultStr.substr(0, size - 1);
    }
    return resultStr;
}

/**
 * 读取包名
 * @param pid 进程id
 * @return
 */
static string getPackageName(const string &pid) {
    if (pid.empty()) {
        return "";
    }
//    char *path = (char *) malloc(sizeof(char));
//    sprintf(path, "/proc/%s/cmdline", pid.c_str());
    string path = "/proc/" + pid + "/cmdline";
    string result = readFile(path);
    if (result.empty()) {
        return "";
    }
//    char *ret = (char *) malloc(sizeof(char));
//    strcpy(ret, result.c_str());
    return result;
}

/**
 * 获取pid
 * @return
 */
static string getMyPid() {
    pid_t pid = getpid();
    char str[BUF_SIZE_64];
//    char *str = (char *) malloc(sizeof(char));
    sprintf(str, "%d", pid);
//    return to_string(pid);
    return str;
}

/**
 * 获取父进程id
 * @return
 */
static string getMyPpid() {
    pid_t pid = getppid();
    char str[BUF_SIZE_64];
//    char *str = (char *) malloc(sizeof(char));
    sprintf(str, "%d", pid);
//    return to_string(pid);
    return str;
}

/**
 * 根据进程id获取pid
 * @param pid
 * @return
 */
static string getMyUid(const string &pid) {

    if (pid.empty()) {
        return "";
    }
    char path[BUF_SIZE_64] = {0};
    sprintf(path, "/proc/%s/status", pid.c_str());
    // 读取进程下status文件
    LOGE("=== getUid : %s", path);
    string statuStr = readFile(path);
    if (statuStr.empty()) {
        return "";
    }
    char *status = (char *) malloc(sizeof(char));
    strcpy(status, statuStr.c_str());
    // 截取uid
    char *uidStr = strstr(status, "Uid:");
    if (uidStr != NULL && strlen(uidStr) != 0) {
        char *firstStr = strtok(uidStr, "\t");
        if (firstStr != NULL && strlen(firstStr) != 0) {
            firstStr = strtok(NULL, "\t");
            if (firstStr != NULL && strlen(firstStr) != 0) {
                // 释放内存
                free(status);
                free(uidStr);
                return firstStr;
            }
        }
    }
    free(status);
    return "";
}

struct StringArray {
    char name[256];
    struct StringArray *next;
};

/**
 * @param dirName 文件夹路径
 * @return  获取文件夹下所有文件
 */
struct StringArray *getDirList(string dirName) {

    // check the parameter
    if (dirName.empty()) {
        LOGE("dir_name is null !");
        return NULL;
    }
    DIR *dir = opendir(dirName.c_str());

    if (NULL == dir) {
        LOGE("Can not open dir. Check path or permission!");
        return NULL;
    }

    // init StringArray list
    struct StringArray *p;
    struct StringArray *head;
    struct StringArray *q;
    head = (StringArray *) malloc(sizeof(StringArray));
    head->next = NULL;
    p = head;

    // read all the files in dir
    struct dirent *file = NULL;
    while ((file = readdir(dir)) != NULL) {

        // skip "." and ".."
        if (strcmp(file->d_name, ".") == 0 || strcmp(file->d_name, "..") == 0) {
            continue;
        }
        if (file->d_type == DT_DIR) {

            // 将节点添加到链表中
            q = (StringArray *) malloc(sizeof(StringArray));
            strcpy(q->name, file->d_name);
//            q->name = file->d_name;
            p->next = q;
            p = q;
            p->next = NULL;
        }
    }
    closedir(dir);
    free(p);
    free(q);
    return head->next == NULL ? NULL : head;
}

struct JavaClass {
    jclass mClass;
    jobject mObject;
};

/**
 * @param env
 * @return  JsonObject数据
 */
struct JavaClass *newJsonObject(JNIEnv *env) {

    jclass json_class = env->FindClass("org/json/JSONObject");
    if (json_class == NULL) {
        LOGE("lib gt-mob:the JSONObject class is null");
        errorCatch(env);
        return NULL;
    }
    jmethodID json_method = env->GetMethodID(json_class, "<init>", "()V");
    if (json_method == NULL) {
        LOGE("lib gt-mob:the JSONObject init method is null");
        errorCatch(env);
        return NULL;
    }
    jobject json_object = env->NewObject(json_class, json_method);
    if (json_object == NULL) {
        LOGE("lib gt-mob:the JSONObject object is null");
        errorCatch(env);
        return NULL;
    }

    struct JavaClass *jsonObject;
    void *temp = malloc(sizeof(JavaClass));
    if (temp == NULL) {
        return NULL;
    }
    jsonObject = (JavaClass *) temp;
    jsonObject->mClass = json_class;
    jsonObject->mObject = json_object;

    return jsonObject;

}

struct JavaClass *newJsonArray(JNIEnv *env) {
    jclass jsonArray_class = env->FindClass("org/json/JSONArray");
    if (jsonArray_class == NULL) {
        LOGE("lib gt-mob:the JSONArray class is null");
        errorCatch(env);
        return NULL;
    }
    jmethodID jsonArray_method = env->GetMethodID(jsonArray_class, "<init>", "()V");
    if (jsonArray_method == NULL) {
        LOGE("lib gt-mob:the JSONArray init method is null");
        errorCatch(env);
        return NULL;
    }
    jobject jsonArray_object = env->NewObject(jsonArray_class, jsonArray_method);
    if (jsonArray_object == NULL) {
        LOGE("lib gt-mob:the JSONArray object is null");
        errorCatch(env);
        return NULL;
    }

    struct JavaClass *jsonArray;
    void *temp = malloc(sizeof(JavaClass));
    if (temp == NULL) {
        return NULL;
    }
    jsonArray = (JavaClass *) temp;
    jsonArray->mClass = jsonArray_class;
    jsonArray->mObject = jsonArray_object;

    return jsonArray;
}

/**
 * 0. 真
 * 1. 假
 * -1. 报错
 * @return 判断str1是否以str2开头
 */
int startsWith(const char *str1, const char *str2) {

    if (str1 == NULL || str2 == NULL) {
        return -1;
    }
    size_t len1 = strlen(str1);
    size_t len2 = strlen(str2);
    if ((len1 < len2) || (len1 == 0 || len2 == 0)) {
        return -1;
    }
    const char *p = str2;
    int i = 0;
    while (*p != '\0') {
        if (*p != str1[i]) {
            return 1;
        }
        p++;
        i++;
    }
    return 0;
}

/**
 * @param env
 * @param defaultParam
 * @param value
 * @param javaClass
 */
void putJsonObject(JNIEnv *env, const char *defaultParam, const char *value, JavaClass *javaClass) {

    if (value == NULL) {
        return;
    }

    if (strlen(value) == 0) {
        return;
    }
    jstring param = env->NewStringUTF(defaultParam);
    jstring mValue = env->NewStringUTF(value);
    jmethodID put_method = env->GetMethodID(javaClass->mClass, "put",
                                            "(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;");

    env->CallObjectMethod(javaClass->mObject, put_method, param, mValue);
    env->DeleteLocalRef(param);
    env->DeleteLocalRef(mValue);
}

/**
 * @param env
 * @param defaultParam
 * @param value
 * @param javaClass
 */
void
putJsonObject(JNIEnv *env, const char *defaultParam, const string &value, JavaClass *javaClass) {

    if (value.empty()) {
        return;
    }
    jstring param = env->NewStringUTF(defaultParam);
    jstring mValue = env->NewStringUTF(value.c_str());
    jmethodID put_method = env->GetMethodID(javaClass->mClass, "put",
                                            "(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;");

    env->CallObjectMethod(javaClass->mObject, put_method, param, mValue);
    env->DeleteLocalRef(param);
    env->DeleteLocalRef(mValue);
}

/**
 * @param env
 * @param defaultParam
 * @param value
 * @param javaClass
 */
void putJsonObject(JNIEnv *env, const char *defaultParam, jobject value, JavaClass *javaClass) {

    if (value == NULL) {
        return;
    }
    jstring param = env->NewStringUTF(defaultParam);
    jmethodID put_method = env->GetMethodID(javaClass->mClass, "put",
                                            "(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;");

    env->CallObjectMethod(javaClass->mObject, put_method, param, value);
    env->DeleteLocalRef(param);
//    env->DeleteLocalRef(value);
}

/**
 * 向jsonArray put数据
 * @param env
 * @param value
 * @param jsonArrayObject
 * @param jsonArrayClass
 */
void putJsonArray(JNIEnv *env, const char *value, JavaClass *javaClass) {
    if (value == NULL) {
        return;
    }
    if (strlen(value) == 0) {
        return;
    }
    jstring param = env->NewStringUTF(value);
    jmethodID put_method = env->GetMethodID(javaClass->mClass, "put",
                                            "(Ljava/lang/Object;)Lorg/json/JSONArray;");
    env->CallObjectMethod(javaClass->mObject, put_method, param);
    errorCatch(env);
    env->DeleteLocalRef(param);
}

void putJsonArray(JNIEnv *env, jobject value, JavaClass *javaClass) {
    if (value == NULL) {
        return;
    }
    jmethodID put_method = env->GetMethodID(javaClass->mClass, "put",
                                            "(Ljava/lang/Object;)Lorg/json/JSONArray;");
    env->CallObjectMethod(javaClass->mObject, put_method, value);
    errorCatch(env);
}

/**
 * 0. 为空
 * 1. 不为空
 * -1. 报错
 * @return
 */
int jArrayEmpty(JNIEnv *env, JavaClass *javaClass) {

    if (javaClass == NULL) {
        return -1;
    }
    jmethodID length_method = env->GetMethodID(javaClass->mClass, "length", "()I");
    if (length_method == NULL) {
        LOGE("lib gt-mob:the Object length method is null");
        errorCatch(env);
        return -1;
    }
    jint len = (env->CallIntMethod(javaClass->mObject, length_method));
    if (len > 0) {
        return 1;
    }
    return 0;
}

void removeSpaces(char *source) {
    char *i = source;
    char *j = source;
    while (*j != 0) {
        *i = *j++;
        if (*i != ' ')
            i++;
    }
    *i = 0;
}

/**
 * 判断字符串是否为数字
 * @param str
 * @return
 */
static int isdigitstr(const char *str) {
    return (strspn(str, "0123456789") == strlen(str));
}

string jstring2string(JNIEnv *env, jstring jStr) {

    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    if (stringClass == NULL) {
        errorCatch(env);
        return "";
    }
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    if (getBytes == NULL) {
        errorCatch(env);
        return "";
    }
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                       env->NewStringUTF("UTF-8"));
    if (stringJbytes == NULL) {
        errorCatch(env);
        return "";
    }

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte *pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    string ret = string((char *) pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}


/**
 * id -u 获取当前进程uid
 * id -u root 获取root用户uid
 * pidof 通过进程名获取pid
 *
 * @param path
 * @return
 */


/**
 * 获取系统激活时间
 * @return
 */
string getSysActiveTime(string path) {

    // 读取/data/data目录创建时间
    struct stat buf;
    stat(path.c_str(), &buf);

    // 解析buf数据结构
    struct timespec bufTime = buf.st_atim;
//    LOGE("---------------------------struct timespec---------------------------------------\n");
//    LOGE("[time(NULL)]     :     %ld\n", time(NULL));
//    clock_gettime(CLOCK_REALTIME, &bufTime); 系统当前时间
    LOGE("active_gettime : tv_sec=%ld, tv_nsec=%ld\n", bufTime.tv_sec, bufTime.tv_nsec);

    struct tm t;
    char date_time[BUF_SIZE_64];
    strftime(date_time, sizeof(date_time), "%Y-%m-%d %H:%M:%S", localtime_r(&bufTime.tv_sec, &t));
    LOGE("active_gettime : date_time=%s, tv_nsec=%ld\n", date_time, bufTime.tv_nsec);

    // 将时间戳拼接
    char resultTime[BUF_SIZE_64];
    sprintf(resultTime, "%ld.%09ld", buf.st_atime, buf.st_atimensec);
    LOGE("拼接时间: %s", resultTime);

    // 将时间戳和日期拼接在一起
    char joinStr[256];
    sprintf(joinStr, "%s#%s", date_time, resultTime);
    LOGE("最后拼接时间=====%s", joinStr);

//    LOGE("---------------------------struct timespec end---------------------------------------\n");
//    LOGE("%s", ctime(&(buf.st_atime)));

    // deviceid
//    LOGE("%llx", buf.st_dev);
//    LOGE("%llu", buf.st_rdev);

    return joinStr;

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
