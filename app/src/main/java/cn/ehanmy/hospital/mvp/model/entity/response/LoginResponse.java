package cn.ehanmy.hospital.mvp.model.entity.response;

/**
 * Created by guomin on 2018/7/25.
 */

public class LoginResponse extends BaseResponse {

    private String token;
    private String signkey;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", signkey='" + signkey + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }
}
