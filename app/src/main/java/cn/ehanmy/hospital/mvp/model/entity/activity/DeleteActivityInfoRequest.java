package cn.ehanmy.hospital.mvp.model.entity.activity;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class DeleteActivityInfoRequest extends BaseRequest {
    private final int cmd = 5254;
    private String activityId;
    private String token;

    @Override
    public String toString() {
        return "DeleteActivityInfoRequest{" +
                "cmd=" + cmd +
                ", activityId='" + activityId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
