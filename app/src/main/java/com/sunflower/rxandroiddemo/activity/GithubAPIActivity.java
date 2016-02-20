package com.sunflower.rxandroiddemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunflower.rxandroiddemo.R;
import com.sunflower.rxandroiddemo.api.GithubHelper;
import com.sunflower.rxandroiddemo.dto.github.Repos;
import com.sunflower.rxandroiddemo.dto.github.User;
import com.sunflower.rxandroiddemo.utils.SecretConstant;

import java.util.List;

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
        showLoadingDialog();
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
        String location = "BeiJing";
        helper.updateLocation(location)
                .subscribe(newSubscriber(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, "" + user.getLocation());
                    }
                }));
    }

    @OnClick(R.id.following_btn)
    void getFollowing() {
        showLoadingDialog();
        final String name = "sunflower-zyb";
//        helper.getFollowing(name)
        helper.getMyFollowing()
                .subscribe(newSubscriber(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        StringBuilder sb = new StringBuilder();
//                        for (User user : users) {
//                            sb.append("user name is ");
//                            sb.append(user.login);
//                            sb.append("\n");
//                        }
                        Log.i(TAG, sb.toString());
//                        Log.i(TAG, "getFollowing---" + users.toString());
                    }
                }));
    }


    @OnClick(R.id.repos_btn)
    void listMyRepositories() {
        showLoadingDialog();
        helper.listMyRepositories()
                .subscribe(newSubscriber(new Action1<List<Repos>>() {
                    @Override
                    public void call(List<Repos> reposes) {
                        Log.i(TAG, "listMyRepositories----" + reposes.size());
                    }
                }));
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
