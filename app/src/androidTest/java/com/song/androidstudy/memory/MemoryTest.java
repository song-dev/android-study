package com.song.androidstudy.memory;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MemoryTest {

    private static final String TAG = "MemoryTest";

    private Instrumentation instrumentation;
    private Bundle arguments;
    private Context context;
    private Context targetContext;

    @Before
    public void test_prepare() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        arguments = InstrumentationRegistry.getArguments();
        context = InstrumentationRegistry.getContext();
        targetContext = InstrumentationRegistry.getTargetContext();
    }

    /**
     * 获取内存相关信息
     */
    @Test
    public void test_memory_info() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //最大分配内存
        int memory = activityManager.getMemoryClass();
        Log.e(TAG, "test_memory_info: "+memory);
        System.out.println("memory: " + memory);
        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
        //当前分配的总内存
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        //剩余内存
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
        System.out.println("maxMemory: " + maxMemory);
        System.out.println("totalMemory: " + totalMemory);
        System.out.println("freeMemory: " + freeMemory);
    }
}
