package demo.com.lxy.demo.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

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
import demo.com.lxy.demo.util.Utils;
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserList(userNameEt.getText().toString().trim());
                Utils.hideSoftKeyBoard(mActivity,userNameEt);
            }
        });

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
        mLog("url:" + url);
        DemoHttpClient.get(mActivity, url, params, new DemoHttpHandler() {
            @Override
            public void onSuccess(String responseString) {
                mLog("responseString:" + responseString);
                GithubUser githubUser = mActivity.decodeJson(GithubUser.class, responseString);

                users.clear();
                List<GithubUserDetail> userDetails = githubUser.get_userDetails();
                if (Utils.isListNotNull(userDetails)) {
                    for (int i = 0; i < userDetails.size(); i++) {
                        GithubUserDetail userDetail = userDetails.get(i);

                        User user = new User();
                        user.setUserName(userDetail.getLogin());
                        user.setUserAvatarUrl(userDetail.getAvatar_url());
                        users.add(user);

                        getUserRepos(userDetail.getLogin(), user, i);
                    }
                }

                adapter.setUsers(users);
            }

            @Override
            public void onFailure(int response_status) {
                mLog("onFailure,response_status=" + response_status);
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
                mLog("responseString:onSuccess");

                //解析数组
                ParserJson<EachRepos> parserJson = new ParserJson<>();
                List<EachRepos> reposList = parserJson.getBeanList(responseString, EachRepos.class);

                //获取用户使用频率最高的编程语言
                String language = getFrequencyLanguage(reposList);
                user.setLanguage(language);
                users.set(position, user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int response_status) {
                mLog("onFailure,response_status=" + response_status);
            }
        });
    }

    //获取用户使用频率最高的编程语言
    private String getFrequencyLanguage(List<EachRepos> reposList) {
        String[] languageArr = new String[reposList.size()];
        for (int i = 0; i < reposList.size(); i++) {
            EachRepos eachRepos = reposList.get(i);
            languageArr[i] = eachRepos.getLanguage();
        }

        return Utils.findMaxString(languageArr);
    }


}
