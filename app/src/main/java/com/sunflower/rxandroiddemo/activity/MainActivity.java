package com.sunflower.rxandroiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunflower.rxandroiddemo.R;
import com.sunflower.rxandroiddemo.api.ApiWrapper;
import com.sunflower.rxandroiddemo.dto.ArticleCategory;
import com.sunflower.rxandroiddemo.dto.ArticleListDTO;
import com.sunflower.rxandroiddemo.dto.HomeRequest;
import com.sunflower.rxandroiddemo.dto.PersonalConfigs;
import com.sunflower.rxandroiddemo.dto.PersonalInfo;
import com.sunflower.rxandroiddemo.dto.RemindDTO;
import com.sunflower.rxandroiddemo.dto.VersionDto;
import com.sunflower.rxandroiddemo.utils.CropCircleTransformation;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

public class MainActivity extends BaseActivity {


    @InjectView(R.id.avatar)
    ImageView mAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @OnClick(R.id.test_github_btn)
    void gotoGithubAPIActivity() {
        Intent intent = new Intent(this, GithubAPIActivity.class);
        startActivity(intent);
    }


    /**
     * 分类id
     */
    long categoryId;

    @OnClick(R.id.get_article_btn)
    void getArticleList() {
        final ApiWrapper wrapper = new ApiWrapper();
        showLoadingDialog();
        Subscription subscription = wrapper.getArticleCategory()
                //可以在doOnNext处理数据
                .doOnNext(new Action1<List<ArticleCategory>>() {
                    @Override
                    public void call(List<ArticleCategory> articleCategories) {
                        categoryId = articleCategories.get(0).getId();
                    }
                })
                        //设置请求次数
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
        mCompositeSubscription.add(subscription);

    }


    @OnClick(R.id.get_home_btn)
    void getHome() {
        //同时请求多个接口
        ApiWrapper wrapper = new ApiWrapper();
        showLoadingDialog();
        //将多个接口的返回结果结合成一个对象
        Subscription subscription = Observable.zip(wrapper.checkVersion(), wrapper.getPersonalInfo(), wrapper.getPersonalConfigs(),
                new Func3<VersionDto, PersonalInfo, PersonalConfigs, HomeRequest>() {
                    @Override
                    public HomeRequest call(VersionDto versionDto, PersonalInfo personalInfo, PersonalConfigs personalConfigs) {
                        HomeRequest request = new HomeRequest();
                        request.setVersionDto(versionDto);
                        request.setPersonalInfo(personalInfo);
                        request.setPersonalConfigs(personalConfigs);
                        return request;
                    }
                })
                .subscribe(newSubscriber(new Action1<HomeRequest>() {
                    @Override
                    public void call(HomeRequest request) {
                        Log.i(TAG, "versionDto--" + request.getVersionDto().toString());
                        Log.i(TAG, "personalInfo--" + request.getPersonalInfo().toString());
                        Log.i(TAG, "PersonalConfigs--" + request.getPersonalConfigs().toString());
                    }
                }));
        mCompositeSubscription.add(subscription);
    }


    @OnClick(R.id.upload_avatar_btn)
    void updatePersonalInfo() {
        ApiWrapper wrapper = new ApiWrapper();
        showLoadingDialog();
        String path = "/storage/emulated/0/Tencent/QQfile_recv/111355.60083131_1280.jpg";
        Subscription subscription = wrapper.updatePersonalInfo(path)
                .subscribe(newSubscriber(new Action1<PersonalInfo>() {
                    @Override
                    public void call(PersonalInfo personalInfo) {
                        Log.i(TAG, "updatePersonalInfo---" + personalInfo.avatar);
                        //设置圆形头像
                        Glide.with(MainActivity.this)
                                .load(personalInfo.avatar)
                                .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                                .into(mAvatar);
                    }
                }));
        mCompositeSubscription.add(subscription);
    }

    @OnClick(R.id.comment_product_btn)
    void commentProduct() {
        ApiWrapper wrapper = new ApiWrapper();
        showLoadingDialog();
        long orderId = 511;
        long productId = 9;
        String content = "xixi";
        List<String> paths = Arrays.asList("/storage/emulated/0/UCDownloads/640.jpg",
                "/storage/emulated/0/Pictures/Screenshots/Screenshot_2016-01-11-16-34-44.jpeg");
        Subscription subscription = wrapper.commentProduct(orderId, productId, content, paths)
                .subscribe(newSubscriber(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
//                        Log.i(TAG, "")
                    }
                }));
        mCompositeSubscription.add(subscription);
    }


    @OnClick(R.id.notification_btn)
    void getNotification() {
        ApiWrapper wrapper = new ApiWrapper();
        showLoadingDialog();
        Subscription subscription = wrapper.getNotificationList()
                .doOnNext(new Action1<List<RemindDTO>>() {
                    @Override
                    public void call(List<RemindDTO> remindDTOs) {

                    }
                })
                .subscribe(newSubscriber(new Action1<List<RemindDTO>>() {
                    @Override
                    public void call(List<RemindDTO> remindDTOs) {
                        Log.i(TAG, "getNotification---" + remindDTOs.toString());
                    }
                }));
        mCompositeSubscription.add(subscription);
    }

    @OnClick(R.id.cancel_favorite_btn)
    void cancelFavorite() {
        showLoadingDialog();
        List<Long> articleId = Arrays.asList(1L, 91L);
        ApiWrapper wrapper = new ApiWrapper();
        Subscription subscription = wrapper.cancelFavorite(articleId)
                .subscribe(newSubscriber(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.i(TAG, "cancelFavorite-- " + "Success");
                    }
                }));
        mCompositeSubscription.add(subscription);
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
        } else if (id == R.id.action_github) {

            Intent intent = new Intent(this, GithubAPIActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
