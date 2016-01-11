package com.sunflower.rxandroiddemo.dto;

import com.google.gson.reflect.TypeToken;
import com.sunflower.rxandroiddemo.utils.JsonUtil;

/**
 * Created by ewhale on 2015/10/10.
 */
public class SunrayResponse {

    /**
     * Ret : 0
     * Code : 1101
     * Message :
     * Total : 1
     * Object : null
     */

    private int Ret;
    private int Code;
    private String Message;
    private int Total;
    private Object Object;

    public void setRet(int Ret) {
        this.Ret = Ret;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public void setObject(Object Object) {
        this.Object = Object;
    }

    public int getRet() {
        return Ret;
    }

    public int getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }

    public int getTotal() {
        return Total;
    }

    public Object getObject() {
        return Object;
    }

    public boolean isOK() {
        return true;
    }

    public <T> T getObjectToClass(Class<T> clazz) {
        return JsonUtil.fromObject(Object, clazz);
    }

    public <T> T getObjectToClass(TypeToken<T> token) {
        return JsonUtil.fromObject(Object, token);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}
