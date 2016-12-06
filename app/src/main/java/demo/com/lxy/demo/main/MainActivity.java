package demo.com.lxy.demo.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.com.lxy.demo.R;
import demo.com.lxy.demo.adapter.UserListAdapter;
import demo.com.lxy.demo.moduel.EachRepos;
import demo.com.lxy.demo.moduel.GithubUser;
import demo.com.lxy.demo.moduel.GithubUserDetail;
import demo.com.lxy.demo.moduel.User;
import demo.com.lxy.demo.util.BaseActivity;
import demo.com.lxy.demo.util.Constants;
import demo.com.lxy.demo.util.ParserJson;
import demo.com.lxy.demo.util.http.DemoHttpClient;
import demo.com.lxy.demo.util.http.DemoHttpHandler;

/**
 * Created by lxy on 16-12-6.
 */
public class MainActivity extends BaseActivity {

    @Bind((R.id.list_view))
    ListView listView;

    @Bind((R.id.user_name))
    EditText userNameEt;

    @Bind((R.id.search))
    Button search;


    private UserListAdapter adapter;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        listView.setAdapter(adapter);

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getUserList(userNameEt.getText().toString().trim());
//            }
//        });
        getUserList("imutlxy");

    }

    private void initData() {
        adapter = new UserListAdapter(mActivity);
        users = new ArrayList<>();
    }

    //获取用户列表
    private void getUserList(String userName) {
        String url = Constants.BASE_URL + "search/users";
        RequestParams params = new RequestParams();
        params.put("q", userName);
        DemoHttpClient.get(mActivity, url, params, new DemoHttpHandler() {
            @Override
            public void onSuccess(String responseString) {
//                mLog("responseString:" + responseString);
                GithubUser githubUser = mActivity.decodeJson(GithubUser.class, responseString);

//                mLog("getTotal_count=" + githubUser.getTotal_count() + ";size=" + githubUser.get_userDetails().size());

                List<GithubUserDetail> userDetails = githubUser.get_userDetails();
                if (userDetails != null && userDetails.size() > 0) {
                    for (int i = 0; i < userDetails.size(); i++) {
                        GithubUserDetail userDetail = userDetails.get(i);

                        User user = new User();
                        user.setUserName(userDetail.getLogin());
                        user.setUserAvatarUrl(userDetail.getAvatar_url());
//                        user.setLanguage();
                        users.add(user);

                        getUserRepos(userDetail.getLogin(), user, i);
                    }
                }

                adapter.setUsers(users);
            }

            @Override
            public void onFailure(int response_status) {
//                mLog("onFailure,response_status=" + response_status);
            }
        });
    }

    //获取每个用户的仓库列表
    private void getUserRepos(String userName, final User user, final int position) {
        String url = Constants.BASE_URL + "users/" + userName + "/repos";
        mLog("url:" + url);
        DemoHttpClient.get(mActivity, url, null, new DemoHttpHandler() {
            @Override
            public void onSuccess(String responseString) {
                mLog("responseString:" + responseString);

                ParserJson<EachRepos> parserJson = new ParserJson<>();
                List<EachRepos> reposList = parserJson.getBeanList(responseString, EachRepos.class);
                mLog("reposList.size=" + reposList.size());

                //todo 获取用户最常用语言

                String language = "c++,java";
                user.setLanguage(language);
                users.set(position, user);
            }

            @Override
            public void onFailure(int response_status) {
                mLog("onFailure,response_status=" + response_status);
            }
        });
    }

    //获取用户最常用语言
    private String getLanguage(List<EachRepos> reposList) {
        String[] languageArr = new String[reposList.size()];
        for (int i = 0; i < reposList.size(); i++) {
            EachRepos eachRepos = reposList.get(i);
            languageArr[i] = eachRepos.getLanguage();
        }

        String[] languages = findMaxString(languageArr);
        mLog("languages:" + Arrays.toString(languages));

        return Arrays.toString(languages);
    }

    //todo: 统计出现频率最高的语言
    public static String[] findMaxString(String[] arr) {

        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < arr.length - 1; i++) {
            int count = 0;
            String temp = null;
            for (int j = i + 1; j < arr.length; j++) {

                if (arr[i].equals(arr[j])) {

                    count++;
                    temp = arr[i];
                }


            }
//从第一个字符开始比较，每次比较完成后，将相同字符的数目和字符储存在map中
            map.put(count + 1, temp);


        }

        int[] countArr = new int[map.size()];
        for (int c : map.keySet()) {

            for (int i = 0; i < map.size(); i++) {
//将map集合的键存储在数组中
                countArr[i] = c;
            }
        }

        Arrays.sort(countArr);
        int MAXCOUNT = countArr[countArr.length - 1];
        String maxvalue = map.get(MAXCOUNT);
        String[] maxString = new String[MAXCOUNT];
        for (int i = 0; i < MAXCOUNT; i++) {
            maxString[i] = maxvalue;
        }
        return maxString;
    }

}
