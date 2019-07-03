package com.song.androidstudy.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chensongsong on 2018/10/28.
 */
public class TestIntentService extends IntentService {

    private static final String TAG = "TestIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public TestIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: " + (Looper.getMainLooper() == Looper.myLooper()));
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart: " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 判断当前线程是否在主线程
        Log.e(TAG, "onHandleIntent: " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
        Log.e(TAG, "onHandleIntent: " + "Thread Name-->" + Thread.currentThread().getName());
        Log.e(TAG, "onHandleIntent: " + Thread.currentThread().getId());

        Log.e(TAG, "onHandleIntent: uid-->"+ Process.myUid());
        Log.e(TAG, "onHandleIntent: pid-->"+ Process.myPid());
        Log.e(TAG, "onHandleIntent: tid-->"+ Process.myTid());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
    }
}
