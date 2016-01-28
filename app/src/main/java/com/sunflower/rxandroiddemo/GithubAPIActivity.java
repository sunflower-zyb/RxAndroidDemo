package com.sunflower.rxandroiddemo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunflower.rxandroiddemo.api.GithubHelper;
import com.sunflower.rxandroiddemo.dto.github.User;
import com.sunflower.rxandroiddemo.utils.SecretConstant;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * GithubAPI测试，不会用啊
 */
public class GithubAPIActivity extends BaseActivity {

    GithubHelper helper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_api);
        helper = new GithubHelper();


    }

    @OnClick(R.id.test_login_btn)
    void loginGithub() {
        helper.loginWithToken(SecretConstant.GITHUB_AUTH_TOKEN)
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        GithubAPIActivity.this.user = user;
                    }
                })
                .subscribe(newSubscriber(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, "login--" + user.toString());
                    }
                }));
    }


    @OnClick(R.id.test_update_btn)
    void update() {
        // TODO: 2016/1/28  更新个人资料失败
//        user.location = "BeiJing";
//        helper.updateLocation(user)
//                .subscribe(newSubscriber(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.i(TAG, "" + user.getLocation());
//                    }
//                }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_github_api, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
