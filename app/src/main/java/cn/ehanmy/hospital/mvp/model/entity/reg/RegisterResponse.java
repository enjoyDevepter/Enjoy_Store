package cn.ehanmy.hospital.mvp.model.entity.reg;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

/**
 * Created by guomin on 2018/7/25.
 */

public class RegisterResponse extends BaseResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
