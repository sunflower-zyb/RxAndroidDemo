package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.Response;
import com.sunflower.rxandroiddemo.dto.SunrayResponse;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Sunflower on 2015/11/4.
 */
public interface APIService {

    /**
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("api/gravida/personal/area.json")
    Observable<Response> loadDefaultAreaAndHospital(@Field("id") String id);


    @FormUrlEncoded
    @POST("api/gravida/product/findByArea.json")
    Observable<Response> loadProductByArea(@Field("id") String id);

    /**
     * 获取pdf地址
     *
     * @param userid
     * @param documentid
     */
    @FormUrlEncoded
    @POST("document/getdocument")
    Observable<SunrayResponse> downloadPDF(@Field("userid") String userid, @Field("documentid") String documentid);

    @FormUrlEncoded
    @POST("api/common/msg.json")
    Observable<Response> getSms(@Field("mobile") String mobile, @Field("appType") String appType);

    @FormUrlEncoded
    @POST("api/common/msg.json")
    Observable<Response<String>> getSmsCode(@Field("mobile") String mobile, @Field("appType") String appType);


    @POST("api/gravida/article/categories.json")
    Observable<Response<List<ArticleCategory>>> getArticleCategory();


}
