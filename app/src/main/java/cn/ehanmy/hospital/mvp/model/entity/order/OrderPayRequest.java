package cn.ehanmy.hospital.mvp.model.entity.order;

import java.util.Map;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class OrderPayRequest extends BaseRequest {
    private final int cmd = 10504;
    private String payId;
    private long amount;
    private String orderId;
    private String token;
    private Map<String,String> params;

    @Override
    public String toString() {
        return "OrderPayRequest{" +
                "cmd=" + cmd +
                ", payId='" + payId + '\'' +
                ", amount=" + amount +
                ", orderId='" + orderId + '\'' +
                ", token='" + token + '\'' +
                ", params=" + params +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
