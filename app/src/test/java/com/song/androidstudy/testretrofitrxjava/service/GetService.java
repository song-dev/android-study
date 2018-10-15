package com.song.androidstudy.testretrofitrxjava.service;

import com.song.androidstudy.testretrofitrxjava.bean.Blog;
import com.song.androidstudy.testretrofitrxjava.bean.Result;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * get请求
 * 参考：https://github.com/ikidou/Retrofit2Demo
 * https://www.jianshu.com/p/308f3c54abdd
 * Created by chensongsong on 2018/10/13.
 */
public interface GetService {

    @GET("blog/{id}")
        //这里的{id} 表示是一个变量
    Call<ResponseBody> getBlog(/** 这里的id表示的是上面的{id} */@Path("id") int id);

    /**
     * method 表示请求的方法，区分大小写，retrofit 不会做处理
     * path表示路径
     * hasBody表示是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getBlog2(@Path("id") int id);

    /**
     * 支持配置header
     *
     * @param customHeaderValue3
     * @return
     */
    @GET("/headers?showAll=true")
    @Headers({"CustomHeader1: customHeaderValue1", "CustomHeader2: customHeaderValue2"})
    Call<ResponseBody> testHeader(@Header("CustomHeader3") String customHeaderValue3);

    /**
     * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
     * 对于Query和QueryMap，如果不是String（或Map的第二个泛型参数不是String）时
     * 会被默认会调用toString转换成String类型
     * Url支持的类型有 okhttp3.HttpUrl, String, java.net.URI, android.net.Uri
     * {@link retrofit2.http.QueryMap} 用法和{@link retrofit2.http.FieldMap} 用法一样，不再说明
     */
    @GET
    //当有URL注解时，这里的URL就省略了
    Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);

    /**
     * 反序列化，配置统一接口
     *
     * @param id
     * @return
     */
    @GET("blog/{id}")
    Call<Result<Blog>> getBlog3(@Path("id") int id);

    /**
     * 在get接口url后拼接参数
     *
     * @param username
     * @param password
     * @return
     */
    @GET("blog")
    Call<Result<Blog>> getBlog4(@Query("username") String username, @Query("password") String password);

    /**
     * @param map
     * @return
     */
    @GET("blog")
    Call<Result<Blog>> getBlog5(@QueryMap Map<String, String> map);

    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

}
