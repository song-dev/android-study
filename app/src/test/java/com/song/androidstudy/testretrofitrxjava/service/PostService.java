package com.song.androidstudy.testretrofitrxjava.service;

import com.song.androidstudy.testretrofitrxjava.bean.Blog;
import com.song.androidstudy.testretrofitrxjava.bean.Result;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * post：包括form和json
 * <p>
 * Created by chensongsong on 2018/10/13.
 */
public interface PostService {

    /**
     * {@link FormUrlEncoded} 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);


    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);

    // TODO Json格式
    @POST("blog")
    Call<Result<Blog>> createBlog(@Body Blog blog);

}
