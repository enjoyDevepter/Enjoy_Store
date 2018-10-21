package cn.ehanmy.hospital.mvp.model.entity.user;

import java.io.Serializable;
import java.util.Objects;

public class MerchBean implements Serializable {
    private String merchId;
    private String goodsId;
    private String name;
    private String title;
    private double salePrice;
    private double marketPrice;
    private double costPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchBean merchBean = (MerchBean) o;
        return Objects.equals(merchId, merchBean.merchId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchId);
    }

    @Override
    public String toString() {
        return "MerchBean{" +
                "merchId='" + merchId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", salePrice=" + salePrice +
                ", marketPrice=" + marketPrice +
                ", costPrice=" + costPrice +
                '}';
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
}
