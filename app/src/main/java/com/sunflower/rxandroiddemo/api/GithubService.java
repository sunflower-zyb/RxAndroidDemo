package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.dto.github.Repos;
import com.sunflower.rxandroiddemo.dto.github.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Github API
 * Created by Sunflower on 2016/1/26.
 */
public interface GithubService {


    @GET("/users/{user}")
    void getUserInfo(@Path("user") String user, Callback<User> callback);


    @GET("/users/{user}")
    Observable<User> login(@Path("user") String user);


    @GET("/users/{user}")
    Observable<User> getFeed(@Path("user") String user);


    @FormUrlEncoded
    @POST("/user")
    Observable<User> updateLocation(@Field("location") String location);


    @GET("/users/{user}/following")
    Observable<List<User>> getFollowing(@Path("user") String user);


    @GET("/user/following")
    Observable<Object> getMyFollowing();


    @GET("/user/repos")
    Observable<List<Repos>> listMyRepositories();


}
