package com.song.androidstudy.testrobolectric;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.song.androidstudy.BuildConfig;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * 1. 依赖 testImplementation "org.robolectric:robolectric:3.8"
 * 2. 因为Robolectric是依赖android SDK所以，支持版本21以下，包括21。需要配送sdk=21或者以下
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class , sdk = 21)
public class RobolectricTestRunnerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {

        Log.e("TAG","hahahahaa...");
        System.out.println("test");
        System.out.println(TextUtils.isEmpty("test"));
        Assert.assertFalse(TextUtils.isEmpty("test"));

        String path = Environment.getRootDirectory().getAbsolutePath();
        System.out.println(path);
    }
}