package cn.ehanmy.hospital.mvp.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserBean implements Serializable {
    private String userName;
    private String token;
    private String signkey;

    public UserBean(String userName, String token, String signkey) {
        this.userName = userName;
        this.token = token;
        this.signkey = signkey;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", signkey='" + signkey + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public String getSignkey() {
        return signkey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }
}
