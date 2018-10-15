package com.song.androidstudy.testretrofitrxjava;

import android.arch.lifecycle.BuildConfig;
import android.util.Log;

import com.google.gson.Gson;
import com.song.androidstudy.testretrofitrxjava.bean.Repository;
import com.song.androidstudy.testretrofitrxjava.service.GithubService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class GithubServiceTest {

    private static final String TAG = "GithubServiceTest";
    private static final String BASE_URL = "https://api.github.com/";
    GithubService githubService;

    @Before
    public void setUp() throws URISyntaxException {
        //输出日志
        ShadowLog.stream = System.out;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        githubService = retrofit.create(GithubService.class);
    }

    /**
     * 某个用户公共仓库
     *
     * @throws IOException
     */
    @Test
    public void publicRepositories() throws IOException {
        Call<List<Repository>> call = githubService.publicRepositories("songsongbrother");
        Response<List<Repository>> execute = call.execute();

        List<Repository> list = execute.body();
        //可输出完整的响应结果，帮助我们调试代码
        Log.i(TAG, new Gson().toJson(list));
        assertTrue(list.size() > 0);
        assertNotNull(list.get(0).name);
    }

}