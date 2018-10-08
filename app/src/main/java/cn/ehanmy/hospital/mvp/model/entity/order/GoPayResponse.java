package cn.ehanmy.hospital.mvp.model.entity.order;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GoPayResponse extends BaseResponse {
    private String orderId;
    private long orderTime;
    private long payMoney;  // 分
    private long totalPrice;  // 分
    private String payType;
    private String payTypeDesc;
    private String orderType;
    private String payStatus;  // 0:待支付1:已支付
    private List<PayEntry> payEntryList;

    @Override
    public String toString() {
        return "GoPayResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderTime=" + orderTime +
                ", payMoney=" + payMoney +
                ", totalPrice=" + totalPrice +
                ", payType='" + payType + '\'' +
                ", payTypeDesc='" + payTypeDesc + '\'' +
                ", orderType='" + orderType + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", payEntryList=" + payEntryList +
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

    public List<PayEntry> getPayEntryList() {
        return payEntryList;
    }

    public void setPayEntryList(List<PayEntry> payEntryList) {
        this.payEntryList = payEntryList;
    }
}
