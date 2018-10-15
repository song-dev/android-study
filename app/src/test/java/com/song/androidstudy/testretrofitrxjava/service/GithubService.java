package com.song.androidstudy.testretrofitrxjava.service;


import com.song.androidstudy.testretrofitrxjava.bean.Repository;
import com.song.androidstudy.testretrofitrxjava.bean.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    String BASE_URL = "https://api.github.com/";

    @GET("users/{username}/repos")
    Call<List<Repository>> publicRepositories(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<User>> followingUser(@Path("username") String username);

    @GET("users/{username}")
    Call<User> user(@Path("username") String username);

}

