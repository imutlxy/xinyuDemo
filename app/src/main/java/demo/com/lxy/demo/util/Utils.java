package demo.com.lxy.demo.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxy on 16-12-6.
 */
public class Utils {

    public static boolean isListNull(List list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static boolean isListNotNull(List list) {
        if (list == null || list.size() == 0)
            return false;
        return true;
    }

    //获取数组中出现频率最高的字符串
    public static String findMaxString(String[] arr) {

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                int value = map.get(arr[i]);
                value ++;
                map.put(arr[i], value);
            } else if (arr[i] != null){
                map.put(arr[i], 1);
            }
        }

        String maxString = "";
        int max = 0;
        for (String key : map.keySet()) {
            if (map.get(key) >= max) {
                max = map.get(key);
                maxString = key;
            }
        }

        return maxString;
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyBoard(BaseActivity mActivity, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyBoard(BaseActivity mActivity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
