package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**用于预约确认请求*/
public class ConfirmAppointmentRequest extends BaseRequest {
    private final int cmd = 10353;
    private String token;
    private String reservationId;

    @Override
    public String toString() {
        return "ConfirmAppointmentRequest{" +
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
