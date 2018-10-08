package com.song.androidstudy.testrobolectric;

import com.song.androidstudy.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class , sdk = 21)
public class NewworkTest {

    private static final String TEST_URL = "http://www.geetest.com/demo/gt/register-test";

    /**
     * httpurlconne
     */
    @Test
    public void test_network(){
        String responseStr = HttpUtils.requsetUrl(TEST_URL);
        System.out.println(responseStr);
    }

    @Config(sdk = 23)
    @Test
    public void test_network_okhttp_get(){
        String responseStr = HttpUtils.requestGet(TEST_URL);
        System.out.println(responseStr);
    }

    @Config(sdk = 23)
    @Test
    public void test_network_okhttp_post(){
        String responseStr = HttpUtils.requestPost("http://www.geetest.com/demo/gt/volidate-test","");
        System.out.println(responseStr);
    }

    @Test
    public void test_network_(){
        String responseStr = HttpUtils.requestPost("http://www.geetest.com/demo/gt/volidate-test","");
        System.out.println(responseStr);
    }

    public interface GithubService {

    }

}
