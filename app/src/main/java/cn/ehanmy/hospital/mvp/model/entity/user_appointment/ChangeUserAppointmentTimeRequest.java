package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ChangeUserAppointmentTimeRequest extends BaseRequest {
    private int cmd = 10354;
    private String token;
    private String reservationId;
    private String reservationDate;
    private String reservationTime;
    private String projectId;

    @Override
    public String toString() {
        return "ChangeUserAppointmentTimeRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
