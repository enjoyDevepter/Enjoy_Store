package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ConfirmShopAppointmentRequest extends BaseRequest {
    private final int cmd = 5403;
    private String token;
    private String reservationId;
    private String orderId;

    @Override
    public String toString() {
        return "ConfirmShopAppointmentRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", orderId='" + orderId + '\'' +
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
