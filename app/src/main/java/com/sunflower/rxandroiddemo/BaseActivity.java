package com.sunflower.rxandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sunflower on 2016/1/11.
 */
public class BaseActivity extends AppCompatActivity {

    protected final String TAG = "RxJava";
    private DialogLoading loading;
    protected AppCompatActivity activity;
    protected Toast mToast = null;

    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mCompositeSubscription = new CompositeSubscription();
    }


    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected <T> Subscriber newSubscriber(final Action1<? super T> onNext) {
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
                if (!mCompositeSubscription.isUnsubscribed()) {
                    onNext.call(t);
                }
            }
        };
    }


    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        mCompositeSubscription.unsubscribe();

    }
}
