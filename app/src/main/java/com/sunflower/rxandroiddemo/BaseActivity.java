package com.sunflower.rxandroiddemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by sunflower on 2016/1/11.
 */
public class BaseActivity extends AppCompatActivity {

    protected final String TAG = "RxJava";
    private DialogLoading loading;
    protected AppCompatActivity activity;
    protected Toast mToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    protected void showLoadingDialog() {
        if (loading == null) {
            loading = new DialogLoading(this);
        }
        loading.show();
    }

    protected void hideLoadingDialog() {
        if (loading != null) {
            loading.dismiss();
        }

    }

    protected <T> Subscriber subscribe(final Action1<? super T> onNext) {
        return new Subscriber<T>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                hideLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                    showToast(exception.message);
                } else if (e instanceof SocketTimeoutException) {
                    showToast("连接超时");
//                    Log.e(TAG, "onError " + e.getMessage());
                } else if (e instanceof ConnectException) {
                    Log.e(TAG, "onError " + "无法连接");
                } else {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "call " + e.toString());
                hideLoadingDialog();
            }

            @Override
            public void onNext(T t) {
                onNext.call(t);
            }
        };
    }


    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
//        Toast.makeText(this, content, SHOW_SHORT_TIME).show();
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }


}
