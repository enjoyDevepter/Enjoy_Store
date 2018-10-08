package cn.ehanmy.hospital.mvp.model.entity.hospital;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ChangeHospitalInfoRequest extends BaseRequest {
    private final int cmd = 5301;
    private String tellphone;
    private String startTime;
    private String endTime;
    private String token;

    @Override
    public String toString() {
        return "ChangeHospitalInfoRequest{" +
                "cmd=" + cmd +
                ", tellphone='" + tellphone + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getTellphone() {
        return tellphone;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
