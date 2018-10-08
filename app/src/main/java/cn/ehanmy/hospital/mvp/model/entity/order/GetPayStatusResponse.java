package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetPayStatusResponse extends BaseResponse {
    private String orderId;
    private long orderTime;
    private long payMoney;
    private long totalPrice;
    private String payType;
    private String payTypeDesc;
    private String orderType;
    private String payStatus;  // 0:待支付1:已支付
    private String remind;

    @Override
    public String toString() {
        return "GetPayStatusResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderTime=" + orderTime +
                ", payMoney=" + payMoney +
                ", totalPrice=" + totalPrice +
                ", payType='" + payType + '\'' +
                ", payTypeDesc='" + payTypeDesc + '\'' +
                ", orderType='" + orderType + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", remind='" + remind + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }
}
