package com.sunflower.rxandroiddemo.dto;

import java.util.List;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class BindAreaAndHospitalInfo {
    String hospitalName;
    int hospitalId;
    List<Area> areas;


    public String getHospitalName() {
        return hospitalName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public String getAreaId() {
        return areas.get(areas.size() - 1).getId();
    }

    public String getFullName() {
        return areas.get(0).getName() + areas.get(1).getName() + areas.get(2).getName();
    }

    class Area {
        int id;
        String name;

        public String getId() {
            return String.valueOf(id);
        }

        public String getName() {
            return name;
        }
    }
}
