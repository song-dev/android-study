package com.song.androidstudy.rxjavaretrofit;

import com.song.androidstudy.rxjavaretrofit.bean.MovieBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 豆瓣测试service
 * <p>
 * Created by chensongsong on 2018/10/13.
 */
public interface MovieService {

    /**
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Call<MovieBean> getTop250(@Query("start") int start, @Query("count") int count);

    /**
     * @param start
     * @param count
     * @return
     */
    @FormUrlEncoded
    @POST("top250")
    Call<MovieBean> postTop250(@Field("start") int start, @Field("count") int count);

}
