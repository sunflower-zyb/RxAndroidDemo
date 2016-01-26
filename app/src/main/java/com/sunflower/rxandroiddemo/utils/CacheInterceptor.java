package com.sunflower.rxandroiddemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sunflower on 2016/1/19.
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        if (!isNetworkReachable(BaseApplication.getInstance())) {
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//        }
//
//        Response response = chain.proceed(request);
//        if (isNetworkReachable(BaseApplication.getInstance())) {
//            int maxAge = 60; // read from cache for 1 minute
//            response.newBuilder()
//                    .removeHeader("Pragma")
////                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        } else {
//            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//            response.newBuilder()
//                    .removeHeader("Pragma")
////                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//        return response;
        Response originalResponse = chain.proceed(chain.request());
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "no-cache";
        }
        return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma").build();

    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
