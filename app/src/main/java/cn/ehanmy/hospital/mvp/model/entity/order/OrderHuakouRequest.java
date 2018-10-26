package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * 订单页面中的划扣接口
 * */
public class OrderHuakouRequest extends BaseRequest {
    private final int cmd = 10508;
    private String token;
    private String reservationId;
    private String orderId;
    private int num;

    @Override
    public String toString() {
        return "OrderHuakouRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", num=" + num +
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
