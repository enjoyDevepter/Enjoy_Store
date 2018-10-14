package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class CancelAppointmentRequest extends BaseRequest {
    private final int cmd = 10355;
    private String token;
    private String reservationId;

    @Override
    public String toString() {
        return "CancelAppointmentRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", reservationId='" + reservationId + '\'' +
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

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}
