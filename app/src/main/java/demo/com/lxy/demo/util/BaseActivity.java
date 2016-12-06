package demo.com.lxy.demo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import demo.com.lxy.demo.util.http.DemoHttpClient;

/**
 * Created by lxy on 16-12-6.
 */
public class BaseActivity extends Activity {

    private boolean isAlive = false;

    public Context context;
    public BaseActivity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        isAlive = true;
        context = getApplicationContext();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        isAlive = true;
        mActivity = this;
        context = getApplicationContext();
    }

    @Override
    protected void onStop() {
        isAlive = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
        DemoHttpClient.cancelActivityRequest(mActivity);
    }


    public <T> T decodeJson(Class<T> mClass, String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, mClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mLog(String text) {
        DemoLog.d(text);
    }



    public boolean getIsAlive() {
        return isAlive;
    }

}
