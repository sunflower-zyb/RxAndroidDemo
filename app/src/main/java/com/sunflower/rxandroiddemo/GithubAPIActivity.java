package com.sunflower.rxandroiddemo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sunflower.rxandroiddemo.api.GithubHelper;
import com.sunflower.rxandroiddemo.dto.github.User;

import rx.functions.Action1;

public class GithubAPIActivity extends BaseActivity {

    GithubHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_api);
        helper = new GithubHelper();


//        helper.getFeed("sunflower-zyb")
//                .subscribe(newSubscriber(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.i(TAG, user.getLogin());
//                    }
//                }));
        helper.updateLocation("GuangZhou")
                .subscribe(newSubscriber(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, String.valueOf(user.getLocation()));
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
