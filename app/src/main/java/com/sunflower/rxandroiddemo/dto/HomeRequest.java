package com.sunflower.rxandroiddemo.dto;

/**
 * Created by Sunflower on 2016/1/15.
 */
public class HomeRequest {

    VersionDto versionDto;
    PersonalInfo personalInfo;
    PersonalConfigs personalConfigs;

    public VersionDto getVersionDto() {
        return versionDto;
    }

    public void setVersionDto(VersionDto versionDto) {
        this.versionDto = versionDto;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public PersonalConfigs getPersonalConfigs() {
        return personalConfigs;
    }

    public void setPersonalConfigs(PersonalConfigs personalConfigs) {
        this.personalConfigs = personalConfigs;
    }
}
