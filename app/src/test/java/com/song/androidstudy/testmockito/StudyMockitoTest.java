package com.song.androidstudy.testmockito;

import android.content.Context;

import com.song.androidstudy.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


/**
 * 1. 依赖org.mockito:mockito-core:2.21.0
 */
@RunWith(MockitoJUnitRunner.class)
public class StudyMockitoTest {

    private static final String FAKE_STRING = "AndroidUnitTest";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        //模拟方法调用的返回值，隔离对Android系统的依赖
        when(mMockContext.getString(R.string.app_name)).thenReturn(FAKE_STRING);
        assertThat(mMockContext.getString(R.string.app_name), is(FAKE_STRING));

        when(mMockContext.getPackageName()).thenReturn("com.jdqm.androidunittest");
        System.out.println(mMockContext.getPackageName());
    }

}
