package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.BuildConfig;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.ArticleListDTO;
import com.sunflower.rxandroiddemo.dto.PersonalConfigs;
import com.sunflower.rxandroiddemo.dto.PersonalInfo;
import com.sunflower.rxandroiddemo.dto.Response;
import com.sunflower.rxandroiddemo.dto.VersionDto;
import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class ApiWrapper extends RetrofitUtil {

    private static final String TAG = "RxJava";
    private final int pageSize = 10;


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

    /**
     * 获取帖子分类
     *
     * @return
     */
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


    public Observable<List<ArticleListDTO>> getArticleList(long id, int pageNumber) {
        return getService().getArticleList(id, pageNumber, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<ArticleListDTO>>, Observable<List<ArticleListDTO>>>() {
                    @Override
                    public Observable<List<ArticleListDTO>> call(Response<List<ArticleListDTO>> articleListDTOs) {
                        return flatResponse(articleListDTOs);
                    }
                });
    }

    /**
     * 版本更新
     *
     * @return
     */
    public Observable<VersionDto> checkVersion() {
        return getService().checkVersion(BuildConfig.VERSION_NAME, "GRAVIDA", "ANDROID")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<VersionDto>, Observable<VersionDto>>() {
                    @Override
                    public Observable<VersionDto> call(Response<VersionDto> versionDtoResponse) {
                        return flatResponse(versionDtoResponse);
                    }
                });
    }

    public Observable<PersonalInfo> getPersonalInfo() {
        return getService().getPersonalInfo("139")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<PersonalInfo>, Observable<PersonalInfo>>() {
                    @Override
                    public Observable<PersonalInfo> call(Response<PersonalInfo> personalInfoResponse) {
                        return flatResponse(personalInfoResponse);
                    }
                });
    }

    public Observable<PersonalConfigs> getPersonalConfigs() {
        return getService().getPersonalConfigs("139")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<PersonalConfigs>, Observable<PersonalConfigs>>() {
                    @Override
                    public Observable<PersonalConfigs> call(Response<PersonalConfigs> personalConfigsResponse) {
                        return flatResponse(personalConfigsResponse);
                    }
                });
    }


}
