package cn.ehanmy.hospital.mvp.model.entity.placeOrder;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.3.3	医美项目下单
public class GoodsBuyRequest extends BaseRequest {
    private final int cmd = 5103;
    private String token;
    private GoodsConfirmBean goods;
    private String memberId;
    private long money;
    private long price;
    private long totalPrice;
    private long payMoney;
    private String remark;

    @Override
    public String toString() {
        return "GoodsBuyRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", goods=" + goods +
                ", memberId='" + memberId + '\'' +
                ", money=" + money +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", payMoney=" + payMoney +
                ", remark='" + remark + '\'' +
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

    public GoodsConfirmBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsConfirmBean goods) {
        this.goods = goods;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
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

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
