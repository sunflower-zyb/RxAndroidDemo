package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.dto.github.User;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Github API 帮助类
 * Created by Sunflower on 2016/1/26.
 */
public class GithubHelper {

    protected Retrofit retrofit;
    protected GithubService service;

    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;

    }

    protected GithubService getService() {
        if (service == null) {
            service = getRetrofit().create(GithubService.class);
        }
        return service;
    }

    public Observable<User> getFeed(String user) {
        return getService().getFeed(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> updateLocation(String location) {
        return getService().updateLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
