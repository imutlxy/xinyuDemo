package demo.com.lxy.demo.moduel;

/**
 * Created by lxy on 16-12-6.
 */
public class User {
    private String userName;//用户名,login
    private String userAvatarUrl;//用户头像地址
    private String language;//用户最常用编程语言

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
