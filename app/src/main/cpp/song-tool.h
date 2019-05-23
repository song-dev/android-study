//
// Created by chensongsong on 2019/5/22.
//

#ifndef ANDROIDSTUDY_SONG_TOOL_H
#define ANDROIDSTUDY_SONG_TOOL_H

#endif //ANDROIDSTUDY_SONG_TOOL_H

#include <string>

#include "song-log.h"

using namespace std;

/**
 * path: 路径
 * 返回值 1:文件存在; 0:文件不存在
 */
static int existsFile(const string &path);

static string readFile(const string &path);

string shellExecute(const string &cmdStr);

/**
 * 读取包名
 * @param pid 进程id
 * @return
 */
static string getPackageName(const string &pid);
