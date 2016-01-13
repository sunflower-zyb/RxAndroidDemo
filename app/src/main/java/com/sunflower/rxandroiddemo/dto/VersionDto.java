package com.sunflower.rxandroiddemo.dto;

/**
 * 版本信息
 * Created by Sunflower on 2015/12/3.
 */
public class VersionDto {


    /**
     * createDate : 1442542101000
     * device : ANDROID
     * filepath : http://www.baidu.com
     * id : 2
     * introduce : 第一个版本
     * isDeleted : false
     * modifyDate : 1442542107000
     * type : GRAVIDA
     * version : 1.0
     */

    private String createDate;
    private String device;
    private String filepath;
    private int id;
    private String introduce;
    private boolean isDeleted;
    private String modifyDate;
    private String type;
    private String version;
    private boolean forced;

    public String getCreateDate() {
        return createDate;
    }

    public String getDevice() {
        return device;
    }

    public String getFilepath() {
        return filepath;
    }

    public int getId() {
        return id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public boolean isForced() {
        return forced;
    }


    @Override
    public String toString() {
        return "VersionDto{" +
                "createDate='" + createDate + '\'' +
                ", device='" + device + '\'' +
                ", filepath='" + filepath + '\'' +
                ", id=" + id +
                ", introduce='" + introduce + '\'' +
                ", isDeleted=" + isDeleted +
                ", modifyDate='" + modifyDate + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", forced=" + forced +
                '}';
    }
}
