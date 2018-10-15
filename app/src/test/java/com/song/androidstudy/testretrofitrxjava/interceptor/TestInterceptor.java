package com.song.androidstudy.testretrofitrxjava.interceptor;

import android.content.Context;
import android.util.Log;

import com.song.androidstudy.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 创建各种拦截器
 * <p>
 * Created by chensongsong on 2018/10/14.
 */
public class TestInterceptor {

    private static final String TAG = "TestInterceptor";

    /**
     * 创建日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {

                    @Override
                    public void log(String message) {
                        Log.e(TAG, "log = " + message);
                    }

                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 创建日志拦截器
     *
     * @return
     */
    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("OkHttp", "HttpHelper1" + String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            Log.d("OkHttp", "HttpHelper2" + String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    /**
     * 拦截器Interceptors
     * 使用addHeader()不会覆盖之前设置的header,若使用header()则会覆盖之前的header
     *
     * @return
     */
    public static Interceptor getRequestHeader() {
        Interceptor headerInterceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder();
                builder.addHeader("version", "1");
                builder.addHeader("time", System.currentTimeMillis() + "");

                Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }

        };
        return headerInterceptor;
    }

    /**
     * 拦截器Interceptors
     * 统一的请求参数
     */
    private Interceptor commonParamsInterceptor() {
        Interceptor commonParams = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();
                Request request;
                HttpUrl httpUrl = originRequest.url().newBuilder().
                        addQueryParameter("paltform", "android").
                        addQueryParameter("version", "1.0.0").build();
                request = originRequest.newBuilder().url(httpUrl).build();
                return chain.proceed(request);
            }
        };

        return commonParams;
    }

    /**
     * 拦截器Interceptors
     * 通过响应拦截器实现了从响应中获取服务器返回的time
     *
     * @return
     */
    public static Interceptor getResponseHeader() {
        Interceptor interceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                String timestamp = response.header("time");
                if (timestamp != null) {
                    //获取到响应header中的time
                }
                return response;
            }
        };
        return interceptor;
    }

    public void setCache(OkHttpClient.Builder httpClientBuilder, Context context){
        //创建Cache
        File httpCacheDirectory = new File(context.getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        httpClientBuilder.cache(cache);
        //设置缓存
        httpClientBuilder.addNetworkInterceptor(getCacheInterceptor2());
        httpClientBuilder.addInterceptor(getCacheInterceptor2());
    }

    /**
     * 在无网络的情况下读取缓存，有网络的情况下根据缓存的过期时间重新请求,
     *
     * @return
     */
    public Interceptor getCacheInterceptor2() {
        Interceptor commonParams = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected()) {
                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                okhttp3.Response response = chain.proceed(request);
                if (NetworkUtils.isConnected()) {//有网络情况下，根据请求接口的设置，配置缓存。
                    //这样在下次请求时，根据缓存决定是否真正发出请求。
                    String cacheControl = request.cacheControl().toString();
                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
                    //将其超时时间这是为0即可:String cacheControl="Cache-Control:public,max-age=0"
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    return response.newBuilder()
//                            .header("Cache-Control", cacheControl)
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    //无网络
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return response.newBuilder()
//                            .header("Cache-Control", "public,only-if-cached,max-stale=360000")
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

            }
        };
        return commonParams;
    }

    /**
     * 设置cookiejar
     *
     * @param httpClientBuilder
     */
    protected void setCookie(OkHttpClient.Builder httpClientBuilder) {
        httpClientBuilder.cookieJar(new CookieJar() {
            final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);//保存cookie
                //也可以使用SP保存
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);//取出cookie
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
    }

}
