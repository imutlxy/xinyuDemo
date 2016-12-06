package demo.com.lxy.demo.moduel;

import java.util.List;

/**
 * Created by gaohuang on 16-12-5.
 */
public class GithubUser {

    /**
     * total_count : 1
     * incomplete_results : false
     * user_details : [{"login":"imutlxy","id":7992171,"avatar_url":"https://avatars.githubusercontent.com/u/7992171?v=3","gravatar_id":"","url":"https://api.github.com/users/imutlxy","html_url":"https://github.com/imutlxy","followers_url":"https://api.github.com/users/imutlxy/followers","following_url":"https://api.github.com/users/imutlxy/following{/other_user}","gists_url":"https://api.github.com/users/imutlxy/gists{/gist_id}","starred_url":"https://api.github.com/users/imutlxy/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/imutlxy/subscriptions","organizations_url":"https://api.github.com/users/imutlxy/orgs","repos_url":"https://api.github.com/users/imutlxy/repos","events_url":"https://api.github.com/users/imutlxy/events{/privacy}","received_events_url":"https://api.github.com/users/imutlxy/received_events","type":"User","site_admin":false,"score":36.387165}]
     */

    private int total_count;
    private boolean incomplete_results;
    private List<GithubUserDetail> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<GithubUserDetail> get_userDetails() {
        return items;
    }

    public void set_userDetails(List<GithubUserDetail> user_details) {
        this.items = user_details;
    }

}
