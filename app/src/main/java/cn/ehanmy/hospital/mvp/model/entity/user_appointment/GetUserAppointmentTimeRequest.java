package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetUserAppointmentTimeRequest extends BaseRequest {
    private final int cmd = 10356;
    private String token;
    private String projectId;

    @Override
    public String toString() {
        return "GetUserAppointmentTimeRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", projectId='" + projectId + '\'' +
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
