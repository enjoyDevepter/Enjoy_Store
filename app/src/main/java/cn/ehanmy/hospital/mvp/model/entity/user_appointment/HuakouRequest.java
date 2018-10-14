package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * 用户预约划扣请求
 * 划扣表示这项预约已经执行
 * */
public class HuakouRequest extends BaseRequest {
    private final int cmd = 10357;
    private String token;
    private String reservationId;
    private String orderId;

    @Override
    public String toString() {
        return "HuakouRequest{" +
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
