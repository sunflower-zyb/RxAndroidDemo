package com.sunflower.rxandroiddemo.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.sunflower.rxandroiddemo.api.APIService;
import com.sunflower.rxandroiddemo.dto.Response;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class RetrofitUtil {

    /**
     * 服务器地址
     */
    private static final String API_HOST = SecretConstant.API_HOST;

    private static APIService service;
    private static Retrofit retrofit;

    public static APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RxJava", message);
                }
            });
            //网络缓存路径文件
            // File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "responses");
            //通过拦截器设置缓存，暂未实现
            //CacheInterceptor cacheInterceptor = new CacheInterceptor();

            OkHttpClient client = new OkHttpClient.Builder()
                    //设置缓存
                    //      .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(interceptor)
                            //网络请求缓存，未实现
                            //    .addInterceptor(cacheInterceptor)
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
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.object);
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new APIException(response.code, response.message));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
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

        @Override
        public String getMessage() {
            return message;
        }
    }


    /**
     * http://www.jianshu.com/p/e9e03194199e
     * <p/>
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
//    protected <T> Observable.Transformer<T, T> applySchedulers() {
////        return new Observable.Transformer<T, T>() {
////            @Override
////            public Observable<T> call(Observable<T> observable) {
////                return observable.subscribeOn(Schedulers.io())
////                        .observeOn(AndroidSchedulers.mainThread());
////            }
////        };
//
//        return (Observable.Transformer<T, T>) schedulersTransformer;
//    }
//
//    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
//        @Override
//        public Object call(Object observable) {
//            return ((Observable) observable).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    ;
//        }
//    };

    protected <T> Observable.Transformer<Response<T>, T> applySchedulers() {
//        return new Observable.Transformer<Response<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<Response<T>> responseObservable) {
//                return responseObservable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap(new Func1<Response<T>, Observable<T>>() {
//                            @Override
//                            public Observable<T> call(Response<T> tResponse) {
//                                return flatResponse(tResponse);
//                            }
//                        })
//                        ;
//            }
//        };
        return (Observable.Transformer<Response<T>, T>) transformer;
    }

    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((Response<Object>)response);
                        }
                    })
                    ;
        }
    };


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
