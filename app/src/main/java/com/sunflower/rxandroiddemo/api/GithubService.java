package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.dto.github.User;

import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Github API
 * Created by Sunflower on 2016/1/26.
 */
public interface GithubService {


    @GET("/users/{user}")
    Observable<User> getFeed(@Path("user") String user);


    @PATCH("/user")
    Observable<User> updateLocation(@Path("location") String location);

}
