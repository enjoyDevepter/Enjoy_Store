package cn.ehanmy.hospital.mvp.model.entity.request;

/**
 * Created by guomin on 2018/7/25.
 */

// 2.2.1	APP用户名登录
public class LoginRequest extends BaseRequest {
    private String username;
    private String password;
    private final int cmd = 10000;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCmd() {
        return cmd;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cmd=" + cmd +
                '}';
    }
}
