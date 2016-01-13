package com.sunflower.rxandroiddemo.dto;

/**
 * 个人配置信息
 * Created by Sunflower on 2015/10/21.
 */
public class PersonalConfigs {

    /**
     * 接收消息
     */
    boolean receive;
    /**
     * 声音
     */
    boolean voice;
    /**
     * 是否已经补充身份证资料
     */
    boolean idcard;
    /**
     * 是否已经订购了套餐
     */
    boolean purchased;

    /**
     * 是否已经完善个人资料
     */
    boolean supplement;

    /**
     * 是否正在诊断
     */
    boolean diagnosing;

    /**
     * 是否有包含需要评论医生的
     */
    String doctor_comment_content;

    String bluetooth;

    /**
     * 是否购买了设备
     */
    boolean hasDevice;

    /**
     * 购买设备订单状态1：未发货2已发货 3已收货
     */
    int orderStatus;

    /**
     * 购买设备订单序号
     */
    String orderSn;


    public boolean isReceive() {
        return receive;
    }

    public boolean isVoice() {
        return voice;
    }

    public boolean isIdcard() {
        return idcard;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public boolean isSupplement() {
        return supplement;
    }


    public boolean isDiagnosing() {
        return diagnosing;
    }


    public String getBluetooth() {
        return String.valueOf(bluetooth);
    }

    public String getDoctorCommentContent() {
        return doctor_comment_content;
    }


    public boolean isHasDevice() {
        return hasDevice;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getOrderSn() {
        return orderSn;
    }
}
