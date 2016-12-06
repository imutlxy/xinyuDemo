package demo.com.lxy.demo.util.http;

/**
 * Created by lxy on 16-12-6.
 */
public interface DemoHttpHandler {
    void onSuccess(String responseString);
    void onFailure(int response_status);
}
