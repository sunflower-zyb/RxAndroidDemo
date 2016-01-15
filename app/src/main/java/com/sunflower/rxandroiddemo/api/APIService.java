package com.sunflower.rxandroiddemo.api;

import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.ArticleListDTO;
import com.sunflower.rxandroiddemo.dto.PersonalConfigs;
import com.sunflower.rxandroiddemo.dto.PersonalInfo;
import com.sunflower.rxandroiddemo.dto.RemindDTO;
import com.sunflower.rxandroiddemo.dto.Response;
import com.sunflower.rxandroiddemo.dto.VersionDto;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @FormUrlEncoded
    @POST("api/common/msg.json")
    Observable<Response> getSms(@Field("mobile") String mobile, @Field("appType") String appType);

    @FormUrlEncoded
    @POST("api/common/msg.json")
    Observable<Response<String>> getSmsCode(@Field("mobile") String mobile, @Field("appType") String appType);


    @POST("api/gravida/article/categories.json")
    Observable<Response<List<ArticleCategory>>> getArticleCategory();

    /**
     * 根据分类获取帖子列表
     *
     * @param id         分类id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("api/gravida/article/list.json")
    Observable<Response<List<ArticleListDTO>>> getArticleList(@Field("id") long id,
                                                              @Field("pageNumber") int pageNumber,
                                                              @Field("pageSize") int pageSize);

    /**
     * 检查版本
     *
     * @param version
     * @param type
     * @param device
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/version.json")
    Observable<Response<VersionDto>> checkVersion(@Field("version") String version,
                                                  @Field("type") String type,
                                                  @Field("device") String device);

    /**
     * 获取个人信息
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("api/gravida/personal/info.json")
    Observable<Response<PersonalInfo>> getPersonalInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("api/gravida/personal/configs.json")
    Observable<Response<PersonalConfigs>> getPersonalConfigs(@Field("id") String id);

    @Multipart
    @POST("api/gravida/personal/update.json")
    Observable<Response<PersonalInfo>> updatePersonalInfo(@Part("avatar") RequestBody avatar,
                                                          @Part("id") String id);

    @Multipart
    @POST("api/gravida/personal/update.json")
    Observable<Response<PersonalInfo>> updatePersonalInfo(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/gravida/product/comment.json")
    Observable<Response<Object>> commentProduct(@PartMap Map<String, RequestBody> params);


    @FormUrlEncoded
    @POST("api/gravida/remind/flow.json")
    Observable<Response<List<RemindDTO>>> getNotificationList(@Field("id") String id);




}
