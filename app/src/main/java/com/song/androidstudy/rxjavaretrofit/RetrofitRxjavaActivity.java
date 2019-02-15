package com.song.androidstudy.rxjavaretrofit;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.song.androidstudy.R;
import com.song.androidstudy.rxjavaretrofit.bean.GtApi1Bean;
import com.song.androidstudy.rxjavaretrofit.bean.MovieBean;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit+rxjava+okhttp测试封装
 * <p>
 * Created by chensongsong on 2018/10/13.
 */
public class RetrofitRxjavaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RetrofitRxjavaActivity";

    public static final String DOUBAN_BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String BASE_URL = "https://www.geetest.com/";

    private Button retrofitget;
    private Button retrofitpost;
    private Button retrofitMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava);
        this.retrofitpost = (Button) findViewById(R.id.retrofit_post);
        this.retrofitget = (Button) findViewById(R.id.retrofit_get);
        this.retrofitMovie = (Button) findViewById(R.id.retrofit_get_movie);

        retrofitget.setOnClickListener(this);
        retrofitpost.setOnClickListener(this);
        retrofitMovie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit_get:
                testGet();
                break;
            case R.id.retrofit_post:
                testPost();
                break;
            case R.id.retrofit_get_movie:
                testMovie();
                break;
        }

    }

    /**
     * get测试
     */
    private void testGet() {
        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 代理生成Service
        GtService gtService = retrofit.create(GtService.class);
        Observable<GtApi1Bean> api1 = gtService.getApi1();
        api1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GtApi1Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GtApi1Bean gtApi1Bean) {
                        Log.e(TAG, "onNext: " + gtApi1Bean.toString());
                        Log.e(TAG, "onNext: " + Looper.myLooper());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        // 只能在主线程调用
//        api1.enqueue(new Callback<GtApi1Bean>() {
//            @Override
//            public void onResponse(Call<GtApi1Bean> call, Response<GtApi1Bean> response) {
//                Log.e(TAG, "onResponse: " + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<GtApi1Bean> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        // 只能在子线程调用
        // try {
        //    Response<GtApi1Bean> response = api1.execute();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

    /**
     * post测试
     */
    private void testPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GtService gtService = retrofit.create(GtService.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("geetest_challenge", "geetest_challenge");
            jsonObject.put("geetest_validate", "geetest_validate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> api2 = gtService.getApi2(jsonObject);
        // 只能在主线程调用
        api2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "onResponse: " + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * movice测试
     */
    private void testMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOUBAN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieBean> top250 = movieService.getTop250(0, 20);
        // 只能在主线程调用
        top250.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                try {
                    Log.e(TAG, "onResponse: " + response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
