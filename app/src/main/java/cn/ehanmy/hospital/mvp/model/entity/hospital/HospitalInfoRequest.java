package cn.ehanmy.hospital.mvp.model.entity.hospital;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.2.3	获取医院信息
public class HospitalInfoRequest extends BaseRequest {
    private final int cmd = 5051;
    private String token;

    @Override
    public String toString() {
        return "HospitalInfoRequest{" +
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
