package com.song.androidstudy.testretrofitrxjava;

import com.song.androidstudy.BuildConfig;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by chensongsong on 2018/10/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RetrofitTest {

    /**
     * 初始化service
     */
    @Before
    public void setUp(){

    }



}
