package com.sunflower.rxandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunflower.rxandroiddemo.api.ApiWrapper;
import com.sunflower.rxandroiddemo.api.APIService;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.BindAreaAndHospitalInfo;
import com.sunflower.rxandroiddemo.dto.JsonResponse;
import com.sunflower.rxandroiddemo.utils.RetrofitUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @OnClick(R.id.get_sms_btn)
    void getSms() {
        ApiWrapper manager = new ApiWrapper();
        manager.getSmsCode2("15813351726")
                .retry(2)
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        Log.i(TAG, "call " + integer);
                        if (throwable instanceof ConnectException && integer < 3)
                            return true;
                        else
                            return false;
                    }
                })
                .subscribe(subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "call " + s);
                    }
                }))
        ;
    }


    @OnClick(R.id.get_article_btn)
    void getArticleList() {
        ApiWrapper wrapper = new ApiWrapper();
        wrapper.getArticleCategory()
                .doOnNext(new Action1<List<ArticleCategory>>() {
                    @Override
                    public void call(List<ArticleCategory> articleCategories) {

                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "doOnError " + throwable.toString());
                    }
                })
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
//                        Log.e(TAG, "call " + integer + "," + throwable.toString());
                        Log.e(TAG, "call " + integer);
                        if (throwable instanceof SocketTimeoutException && integer < 2)
                            return true;
                        else
                            return false;
                    }
                })
                .subscribe(subscribe(new Action1<List<ArticleCategory>>() {
                    @Override
                    public void call(List<ArticleCategory> articleCategories) {
                        for (ArticleCategory category : articleCategories) {
                            Log.i(TAG, "call " + category.getId() + "," + category.getName());
                        }
                    }
                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_pdf) {
        }

        return super.onOptionsItemSelected(item);
    }


}
