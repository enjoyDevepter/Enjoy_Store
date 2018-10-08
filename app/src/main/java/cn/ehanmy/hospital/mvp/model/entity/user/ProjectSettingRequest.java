package cn.ehanmy.hospital.mvp.model.entity.user;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ProjectSettingRequest extends BaseRequest {
    private final int cmd = 5202;
    private String token;

    @Override
    public String toString() {
        return "ProjectSettingRequest{" +
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
