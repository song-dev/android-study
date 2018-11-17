package com.song.androidstudy.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
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
    protected void onHandleIntent(@Nullable Intent intent) {
        // 判断当前线程是否在主线程
        Log.e(TAG, "onHandleIntent: " + (Thread.currentThread() == Looper.getMainLooper().getThread()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }
}
