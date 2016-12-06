package demo.com.lxy.demo.util.http;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.protocol.HTTP;
import demo.com.lxy.demo.R;
import demo.com.lxy.demo.util.BaseActivity;
import demo.com.lxy.demo.util.Constants;
import demo.com.lxy.demo.util.DemoLog;

/**
 * Created by lxy on 16-12-6.
 */
public class DemoHttpClient {

    private static AsyncHttpClient mClient = new AsyncHttpClient();


    public static void cancelAllRequest(Context context) {
        mClient.cancelRequests(context, true);
    }

    public static void cancelActivityRequest(Activity mActivity) {
        mClient.cancelRequests(mActivity, true);
    }


    // 发起get请求
    public static void get(final BaseActivity activity, String url, final RequestParams params,
                           final DemoHttpHandler demoHttpHandler) {
        final Context context = activity.getApplicationContext();

        mClient.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        mClient.get(activity, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (!activity.getIsAlive()) {
                    DemoLog.d("!activity.getIsAlive()");
                    return;
                }
                handlerResponseStatus(responseBody, demoHttpHandler);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (!activity.getIsAlive()) {
                    return;
                }

                if (responseBody != null && responseBody.length > 0) {
                    String failRes = new String(responseBody);
                    DemoLog.d("failRes=" + failRes);
                }

                demoHttpHandler.onFailure(Constants.HTTP_ERROR);
                Toast.makeText(context, R.string.http_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void post(final BaseActivity activity, String url, final String json,
                            final DemoHttpHandler demoHttpHandler) {
        final Context context = activity.getApplicationContext();

        StringEntity entity = null;
        entity = new StringEntity(json, HTTP.UTF_8);
        if (entity == null) {
            demoHttpHandler.onFailure(Constants.HTTP_ERROR);
            Toast.makeText(context, R.string.http_network_error, Toast.LENGTH_SHORT).show();
            return;
        }

        mClient.post(activity, url, entity, "application/x-www-form-urlencoded",
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {
                        if (!activity.getIsAlive()) {
                            return;
                        }
                        handlerResponseStatus(responseBody, demoHttpHandler);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        DemoLog.d("http onFailure : ");
                        if (!activity.getIsAlive()) {
                            return;
                        }
                    }
                });
    }

    private static void handlerResponseStatus(final byte[] responseBody, final DemoHttpHandler demoHttpHandler) {
        // 将responseBody解析成字符串
        String responseString;
        try {
            responseString = new String(responseBody);
            demoHttpHandler.onSuccess(responseString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            demoHttpHandler.onFailure(Constants.HTTP_ERROR);
            return;
        }
    }


}
