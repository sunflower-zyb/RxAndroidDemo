package com.sunflower.rxandroiddemo;

import android.app.Application;

/**
 * Created by Sunflower on 2016/1/19.
 */
public class BaseApplication extends Application {
    static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
