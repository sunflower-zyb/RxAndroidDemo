package com.sunflower.rxandroiddemo.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }


}
