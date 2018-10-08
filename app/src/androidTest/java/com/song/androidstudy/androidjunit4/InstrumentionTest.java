package com.song.androidstudy.androidjunit4;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InstrumentionTest {

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

    @Test
    public void test_instrumention(){

    }

}
