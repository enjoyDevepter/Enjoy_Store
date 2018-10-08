package cn.ehanmy.hospital.mvp.model.entity.placeOrder;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GoodsBuyResponse extends BaseResponse {
    private String orderId;
    private long orderTime;
    private long payMoney;
    private String payStatus;
    private GoodsOrderBean goods;
    private List<PayEntry> payEntryList;

    @Override
    public String toString() {
        return "GoodsBuyResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderTime=" + orderTime +
                ", payMoney=" + payMoney +
                ", payStatus='" + payStatus + '\'' +
                ", goods=" + goods +
                ", payEntryList=" + payEntryList +
                '}';
    }

    public GoodsOrderBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsOrderBean goods) {
        this.goods = goods;
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
