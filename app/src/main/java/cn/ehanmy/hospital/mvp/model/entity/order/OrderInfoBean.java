package cn.ehanmy.hospital.mvp.model.entity.order;

import java.io.Serializable;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;

/**订单详情*/
public class OrderInfoBean implements Serializable{
    private String reservationDate;
    private String reservationTime;
    private long coupon;
    private long deductionMoney;
    private long freight;
    private long money;
    private int nums;
    private String orderId;
    private String orderStatus;
    private String orderStatusDesc;
    private String payType;
    private String payTypeDesc;
    private String remark;
    private long orderTime;
    private long payMoney;
    private long payTime;
    private long price;
    private long totalPrice;
    private List<GoodsOrderBean> goodsList;
    private OrderRecipientInfoBean orderRecipientInfo;
    private HospitalBean hospital;
    private OrderMemberInfoBean member;

    public OrderMemberInfoBean getMember() {
        return member;
    }

    public void setMember(OrderMemberInfoBean member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "OrderInfoBean{" +
                ", coupon=" + coupon +
                ", deductionMoney=" + deductionMoney +
                ", freight=" + freight +
                ", money=" + money +
                ", nums=" + nums +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderStatusDesc='" + orderStatusDesc + '\'' +
                ", payType='" + payType + '\'' +
                ", payTypeDesc='" + payTypeDesc + '\'' +
                ", remark='" + remark + '\'' +
                ", orderTime=" + orderTime +
                ", payMoney=" + payMoney +
                ", payTime=" + payTime +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", goodsList=" + goodsList +
                ", orderRecipientInfo=" + orderRecipientInfo +
                ", hospital=" + hospital +
                '}';
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public long getCoupon() {
        return coupon;
    }

    public void setCoupon(long coupon) {
        this.coupon = coupon;
    }

    public long getDeductionMoney() {
        return deductionMoney;
    }

    public void setDeductionMoney(long deductionMoney) {
        this.deductionMoney = deductionMoney;
    }

    public long getFreight() {
        return freight;
    }

    public void setFreight(long freight) {
        this.freight = freight;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<GoodsOrderBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsOrderBean> goodsList) {
        this.goodsList = goodsList;
    }

    public OrderRecipientInfoBean getOrderRecipientInfo() {
        return orderRecipientInfo;
    }

    public void setOrderRecipientInfo(OrderRecipientInfoBean orderRecipientInfo) {
        this.orderRecipientInfo = orderRecipientInfo;
    }

    public HospitalBean getHospital() {
        return hospital;
    }

    public void setHospital(HospitalBean hospital) {
        this.hospital = hospital;
    }
}
