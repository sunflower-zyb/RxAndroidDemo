package com.sunflower.rxandroiddemo.dto;

import com.google.gson.reflect.TypeToken;
import com.sunflower.rxandroiddemo.utils.JsonUtil;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class JsonResponse {

    public String code;
    public String message;
    public Object object;
    /**
     * 在分页时用到
     */
    public int totalPage;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public <T> T getData(Class<T> clazz) {
        return JsonUtil.fromObject(object, clazz);
    }

    public <T> T getData(TypeToken<T> token) {
        return JsonUtil.fromObject(object, token);
    }


    @Override
    public String toString() {
        return "JsonResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", object=" + object +
                ", totalPage=" + totalPage +
                '}';
    }
}
