package com.sunflower.rxandroiddemo.utils;

import android.util.Log;

import com.sunflower.rxandroiddemo.api.APIService;
import com.sunflower.rxandroiddemo.dto.Response;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class RetrofitUtil {

    /**
     * 服务器地址
     */
    private final String API_HOST = APIHost.API_HOST;

    private APIService service;
    private Retrofit retrofit;

    public APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {

//            OkHttpClient client = new OkHttpClient();
// set time out interval
//            client.setReadTimeout(10, TimeUnit.MINUTES);
//            client.setConnectTimeout(10, TimeUnit.MINUTES);
//            client.setWriteTimeout(10, TimeUnit.MINUTES);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RxJava", "LoggingInterceptor---" + message);
                }
            });

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                switch (response.code) {
                    case Constant.OK:
                        subscriber.onNext(response.object);
                        break;
                    default:
                        subscriber.onError(new APIException(response.code, response.message));
                        break;
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * http://www.jianshu.com/p/e9e03194199e
     * 木有十分懂，，
     *
     * @param <T>
     * @return
     */
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static class APIException extends Exception {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }


}
