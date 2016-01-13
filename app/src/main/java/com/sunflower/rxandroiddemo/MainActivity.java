package com.sunflower.rxandroiddemo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunflower.rxandroiddemo.api.ApiWrapper;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.ArticleListDTO;
import com.sunflower.rxandroiddemo.dto.VersionDto;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

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
                .subscribe(newSubscriber(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "call " + s);
                    }
                }))
        ;
    }


    /**
     * 分类id
     */
    long categoryId;

    @OnClick(R.id.get_article_btn)
    void getArticleList() {
        final ApiWrapper wrapper = new ApiWrapper();
        wrapper.getArticleCategory()
                .doOnNext(new Action1<List<ArticleCategory>>() {
                    @Override
                    public void call(List<ArticleCategory> articleCategories) {
                        categoryId = articleCategories.get(0).getId();
                    }
                })
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        Log.e(TAG, "call " + integer);
                        if (throwable instanceof SocketTimeoutException && integer < 2)
                            return true;
                        else
                            return false;
                    }
                })
                .flatMap(new Func1<List<ArticleCategory>, Observable<List<ArticleListDTO>>>() {
                    @Override
                    public Observable<List<ArticleListDTO>> call(List<ArticleCategory> articleCategories) {

                        return wrapper.getArticleList(categoryId, 1);
                    }
                })
                .subscribe(newSubscriber(new Action1<List<ArticleListDTO>>() {
                    @Override
                    public void call(List<ArticleListDTO> articleList) {
                        for (ArticleListDTO article : articleList) {
                            Log.i(TAG, article.getId() + " " + article.getTitle() + " " + article.getIntro());
                        }
                    }
                }));
        ;
    }


    @OnClick(R.id.get_home_btn)
    void getHome() {
        ApiWrapper wrapper = new ApiWrapper();
        Subscription s1 = wrapper
                .checkVersion()
                .subscribe(newSubscriber(new Action1<VersionDto>() {
                    @Override
                    public void call(VersionDto dto) {
                        Log.i(TAG, "checkVersion--" + dto.toString());
                    }
                }));
        mCompositeSubscription.add(s1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCompositeSubscription.unsubscribe();

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
