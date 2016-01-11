package com.sunflower.rxandroiddemo.api;

import android.util.Log;
import android.widget.Toast;

import com.sunflower.rxandroiddemo.MainActivity;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.JsonResponse;
import com.sunflower.rxandroiddemo.dto.Response;
import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class ApiWrapper extends RetrofitUtil {

    private static final String TAG = "RxJava";


    public Observable<String> getSmsCode2(String mobile) {
        return getService().getSmsCode(mobile, "GRAVIDA")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Response response) {
                        return flatResponse(response);
                    }
                });
    }

    public Observable<List<ArticleCategory>> getArticleCategory() {
        return getService().getArticleCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<ArticleCategory>>, Observable<List<ArticleCategory>>>() {
                    @Override
                    public Observable<List<ArticleCategory>> call(Response<List<ArticleCategory>> listResponse) {
                        return flatResponse(listResponse);
                    }
                });
    }


}
