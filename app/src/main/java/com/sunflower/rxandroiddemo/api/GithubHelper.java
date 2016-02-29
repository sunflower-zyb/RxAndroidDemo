package com.sunflower.rxandroiddemo.api;

import android.util.Base64;
import android.util.Log;

import com.sunflower.rxandroiddemo.dto.github.Repos;
import com.sunflower.rxandroiddemo.dto.github.User;
import com.sunflower.rxandroiddemo.utils.SecretConstant;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private final static String API_HOST = "https://api.github.com";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * 通过账号密码获取{@link GithubService},有问题
     *
     * @param serviceClass
     * @param username
     * @param password
     * @param <S>
     * @return
     */
    protected <S> S getService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            String credentials = username + ":" + password;
            // create Base64 encodet string
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Log.i("RxJava", "getService " + basic);
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "applicaton/json")
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }

            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * 通过token获取{@link GithubService}
     *
     * @param serviceClass
     * @param authToken
     * @param <S>
     * @return
     */
    public <S> S getService(Class<S> serviceClass, final String authToken) {
        if (authToken != null) {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", "token " + authToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public GithubService getService() {
        return getService(GithubService.class, null);

    }


    public Observable<User> loginWithPwd(String username, String password) {
        return getService(GithubService.class, username, password)
                .login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> loginWithToken(String authToken) {
        String username = "sunflower-zyb";
        return getService(GithubService.class, authToken)
                .login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<User> updateLocation(String location) {
        return getService(GithubService.class, SecretConstant.GITHUB_AUTH_TOKEN)
                .updateLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<User>> getFollowing(String user) {
        return getService().getFollowing(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> getMyFollowing() {
        return getService(GithubService.class, SecretConstant.GITHUB_AUTH_TOKEN)
                .getMyFollowing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<Repos>> listMyRepositories() {
        return getService(GithubService.class, SecretConstant.GITHUB_AUTH_TOKEN)
                .listMyRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
