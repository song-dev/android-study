package com.song.androidstudy.rxjavaretrofit;

import com.song.androidstudy.rxjavaretrofit.bean.GtApi1Bean;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by chensongsong on 2018/10/13.
 */
public interface GtService {

    /**
     * api1接口测试
     *
     * @return
     */
    @GET("demo/gt/register-fullpage")
    Observable<GtApi1Bean> getApi1();

    /**
     * api2接口测试, 上行jsonbody
     *
     * @return
     */
    @POST("demo/gt/validate-fullpage")
    Call<ResponseBody> getApi2(@Body JSONObject jsonObject);

}
