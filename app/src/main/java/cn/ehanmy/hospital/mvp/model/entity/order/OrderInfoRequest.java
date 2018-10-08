package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.4.2	获取医美项目订单详情
public class OrderInfoRequest extends BaseRequest {
    private String orderId;
    private String token;
    private final int cmd = 5152;

    @Override
    public String toString() {
        return "OrderInfoRequest{" +
                "orderId='" + orderId + '\'' +
                ", token='" + token + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCmd() {
        return cmd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
