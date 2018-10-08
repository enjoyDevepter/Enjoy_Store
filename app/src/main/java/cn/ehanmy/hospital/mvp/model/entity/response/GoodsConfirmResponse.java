package cn.ehanmy.hospital.mvp.model.entity.response;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;

public class GoodsConfirmResponse extends BaseResponse {
    private long balance;
    private long money;
    private long price;
    private long totalPrice;
    private long payMoney;
    private GoodsListBean goods;
    private List<GoodsSpecValueBean> goodsSpecValueList;

    @Override
    public String toString() {
        return "GoodsConfirmResponse{" +
                "balance=" + balance +
                ", money=" + money +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", payMoney=" + payMoney +
                ", goods=" + goods +
                ", goodsSpecValueList=" + goodsSpecValueList +
                '}';
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
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

    public GoodsListBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsListBean goods) {
        this.goods = goods;
    }

    public List<GoodsSpecValueBean> getGoodsSpecValueList() {
        return goodsSpecValueList;
    }

    public void setGoodsSpecValueList(List<GoodsSpecValueBean> goodsSpecValueList) {
        this.goodsSpecValueList = goodsSpecValueList;
    }
}
