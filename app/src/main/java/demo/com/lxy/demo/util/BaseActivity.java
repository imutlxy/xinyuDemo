package demo.com.lxy.demo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import demo.com.lxy.demo.util.http.DemoHttpClient;

/**
 * Created by gaohuang on 16-12-5.
 */
public class BaseActivity extends Activity {

    private boolean isAlive = false;
    private boolean isCreate = false;

    private String className;

    public Context context;
    public BaseActivity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        isAlive = true;
        context = getApplicationContext();

        className = this.getClass().getSimpleName();
//        try {
//            String logInfo = className + " create : " + Utils.getMemoryInfo();
//            mLog("PATH : " + logInfo);
////            LiuliuApplication.addToPath(logInfo);
//        } catch (Exception e) {
//        }
    }

    @Override
    protected void onStart() {
        isCreate = true;
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
        Runtime info = Runtime.getRuntime();
        long freeSize = info.freeMemory();
        long totalSize = info.totalMemory();
        long maxSize = info.maxMemory();
        double freeMemory = freeSize / 1048576.0;
        double totalMemory = totalSize / 1048576.0;
        double maxMemory = maxSize / 1048576.0;

        DemoLog.d("zs", "free:" + freeMemory + " total:" + totalMemory + " max:" + maxMemory);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
        DemoHttpClient.cancelActivityRequest(mActivity);

//        try {
//            String logInfo = className + " destroy : " + Utils.getMemoryInfo();
//            mLog("PATH : " + logInfo);
//        } catch (Exception e) {
//        }
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
