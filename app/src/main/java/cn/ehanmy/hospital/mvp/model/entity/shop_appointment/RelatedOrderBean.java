package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import java.io.Serializable;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderMemberInfoBean;

public class RelatedOrderBean implements Serializable {
    private String orderId;
    private long orderTime;
    private String orderType;
    private String orderTypeDesc;
    private String orderStatus;
    private String orderStatusDesc;
    private long price;
    private int num;
    private long totalPrice;
    private long payMoney;
    private RelatedOrderGoodsBean goods;

    public OrderMemberInfoBean getMember() {
        return member;
    }

    public void setMember(OrderMemberInfoBean member) {
        this.member = member;
    }

    private OrderMemberInfoBean member;

    @Override
    public String toString() {
        return "RelatedOrderBean{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderTypeDesc='" + orderTypeDesc + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderStatusDesc='" + orderStatusDesc + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", totalPrice=" + totalPrice +
                ", payMoney=" + payMoney +
                ", goods=" + goods +
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public RelatedOrderGoodsBean getGoods() {
        return goods;
    }

    public void setGoods(RelatedOrderGoodsBean goods) {
        this.goods = goods;
    }
}
