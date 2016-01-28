package com.sunflower.rxandroiddemo.api;

import android.graphics.Bitmap;

import com.sunflower.rxandroiddemo.BuildConfig;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.ArticleListDTO;
import com.sunflower.rxandroiddemo.dto.PersonalConfigs;
import com.sunflower.rxandroiddemo.dto.PersonalInfo;
import com.sunflower.rxandroiddemo.dto.RemindDTO;
import com.sunflower.rxandroiddemo.dto.Response;
import com.sunflower.rxandroiddemo.dto.VersionDto;
import com.sunflower.rxandroiddemo.utils.ClippingPicture;
import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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


    /**
     * 根据类型id，获取文章列表
     *
     * @param id
     * @param pageNumber
     * @return
     */
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


    /**
     * 上传单个文件
     *
     * @param path
     * @return
     */
    public Observable<PersonalInfo> updatePersonalInfo(String path) {
        File file = new File(path);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "139");
//        RequestBody avatar = RequestBody.create(MediaType.parse("image/*"), file);
        Bitmap bitmap = ClippingPicture.decodeBitmapSd(path);
        RequestBody avatar = RequestBody.create(MediaType.parse("image/*"), ClippingPicture.bitmapToBytes(bitmap));
        Map<String, RequestBody> params = new HashMap<>();
        params.put("id", id);
        params.put("avatar\"; filename=\"" + file.getName() + "", avatar);
        return getService().updatePersonalInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<PersonalInfo>, Observable<PersonalInfo>>() {
                    @Override
                    public Observable<PersonalInfo> call(Response<PersonalInfo> personalInfoResponse) {
                        return flatResponse(personalInfoResponse);
                    }
                });

    }

    /**
     * 测试使用对象作为参数，失败
     *
     * @param info
     * @return
     */
    public Observable<PersonalInfo> updatePersonalInfo(PersonalInfo info) {
        return getService().updatePersonalInfo(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<PersonalInfo>, Observable<PersonalInfo>>() {
                    @Override
                    public Observable<PersonalInfo> call(Response<PersonalInfo> personalInfoResponse) {
                        return flatResponse(personalInfoResponse);
                    }
                });
    }

    /**
     * 同时传递多个文件
     *
     * @param orderId
     * @param productId
     * @param content
     * @param paths
     * @return
     */
    public Observable<Object> commentProduct(long orderId, long productId, String content, List<String> paths) {
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), "166");
        RequestBody orderIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(orderId));
        RequestBody productIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(productId));
//        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody contentBody = createRequestBody(content);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("id", id);
        params.put("orderId", orderIdBody);
        params.put("productId", productIdBody);
        params.put("content", contentBody);
        for (String image : paths) {
            File file = new File(image);
            RequestBody images = RequestBody.create(MediaType.parse("image/*"), file);
            //key值中为images
            params.put("images\"; filename=\"" + file.getName() + "", images);
        }
        return getService().commentProduct(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<Object>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Response<Object> objectResponse) {
                        return flatResponse(objectResponse);
                    }
                })
                ;

    }


    /*
    * https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java
    * http://stackoverflow.com/questions/29958881/download-progress-with-rxjava-okhttp-and-okio-in-android
    * 下载文件的进度
    * */


    public Observable<List<RemindDTO>> getNotificationList() {
        return getService().getNotificationList("139")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<List<RemindDTO>>, Observable<List<RemindDTO>>>() {
                    @Override
                    public Observable<List<RemindDTO>> call(Response<List<RemindDTO>> listResponse) {
                        return flatResponse(listResponse);
                    }
                });
    }


    /**
     * 传递数组
     *
     * @param articleId
     * @return
     */
    public Observable<Object> cancelFavorite(List<Long> articleId) {
        return getService().cancelFavorite("139", articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Response<Object>, Observable<Object>>() {
                    @Override
                    public Observable<Object> call(Response<Object> objectResponse) {
                        return flatResponse(objectResponse);
                    }
                });

    }

}
