package cn.ehanmy.hospital.mvp.model.entity.store;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetStoreInfoRequest extends BaseRequest {
    private final int cmd = 10051;
    private String token;

    @Override
    public String toString() {
        return "GetStoreInfoRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
