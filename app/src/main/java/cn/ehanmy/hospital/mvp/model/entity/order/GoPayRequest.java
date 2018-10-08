package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GoPayRequest extends BaseRequest {
    private final int cmd = 5106;
    private String token;
    private String orderId;

    @Override
    public String toString() {
        return "GoPayRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
