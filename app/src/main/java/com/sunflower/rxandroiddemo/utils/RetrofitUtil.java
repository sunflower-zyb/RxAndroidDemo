package com.sunflower.rxandroiddemo.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.sunflower.rxandroiddemo.BaseApplication;
import com.sunflower.rxandroiddemo.api.APIService;
import com.sunflower.rxandroiddemo.dto.Response;

import java.io.File;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
    private static final String API_HOST = APIHost.API_HOST;

    private static APIService service;
    private static Retrofit retrofit;

    protected static APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {

//            OkHttpClient client = new OkHttpClient();
// set time out interval
//            client.setReadTimeout(10, TimeUnit.MINUTES);
//            client.setConnectTimeout(10, TimeUnit.MINUTES);
//            client.setWriteTimeout(10, TimeUnit.MINUTES);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RxJava", message);
                }
            });

            //网络缓存路径文件
            File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "responses");
            //通过拦截器设置缓存，暂未实现
            CacheInterceptor cacheInterceptor = new CacheInterceptor();

            OkHttpClient client = new OkHttpClient.Builder()
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                            //log请求参数
                    .addInterceptor(interceptor)
                            //网络请求缓存，未实现
                    .addInterceptor(cacheInterceptor)
//                    .addNetworkInterceptor(cacheInterceptor)
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


    /**
     * 自定义异常，当接口返回的{@link Response#code}不为{@link Constant#OK}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Exception {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    /**
     * 当{@link APIService}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

    /**
     * 已二进制传递图片文件，对图片文件进行了压缩
     *
     * @param path 文件路径
     * @return
     */
    protected RequestBody createPictureRequestBody(String path) {
        Bitmap bitmap = ClippingPicture.decodeResizeBitmapSd(path, 400, 800);
        return RequestBody.create(MediaType.parse("image/*"), ClippingPicture.bitmapToBytes(bitmap));
    }


}
