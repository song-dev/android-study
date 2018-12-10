package com.song.androidstudy.ipc;

/**
 * Created by chensongsong on 2018/12/7.
 */
public class IPCIntroduction {

    {
        /**
         * 1. 进程间的通讯(Inter-Prosses communication),简称IPC
         * 2. 进程间通讯方式有很多，如Bundle、Socket、文件、管道、Messager、Provider、AIDL等
         * 3. 设置多进程方式，manifest配置android:process=":remote"或者android:process="com.song.androidstudy.remote"
         * 4. :remote方式进程名为包名+:remote,否则为com.song.androidstudy.remote
         * 5. :remote开头的进程是当前应用的私有进程，其他进程的组件不可以跑在一起，但是非：开头的进程可以通过相同的sharedUID跑在同一个进程当中，为全局进程。
         * 6. 静态成员和单例对于完全失效，线程同步完全失效，Application会创建多次，SharedPreferences可靠性下降（虽然可以进程间通讯，但系统会缓存）
         *
         */
    }

    {
        /**
         * 各种通讯之间的优劣
         * 1. bundle 利用intent传递数据，只支持序列化的数据，且数据量不宜过大
         * 2. 文件通讯，并发支持弱，因为android文件读写没有锁
         * 3. SharedPreferences，可靠性弱，且不支持并发
         * 4. Messager底层实现为AIDL，轻量级的IPC机制，一次只执行一次请求，不支持并发，通过Message传输数据
         * 5.
         */
    }

}
