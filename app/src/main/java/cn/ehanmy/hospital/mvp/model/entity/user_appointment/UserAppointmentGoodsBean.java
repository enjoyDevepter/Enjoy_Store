package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;
import java.lang.reflect.Member;

import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;

public class UserAppointmentGoodsBean implements Serializable {
    private String goodsId;
    private String merchId;
    private String name;
    private String title;
    private double salePrice;
    private double costPrice;
    private double marketPrice;
    private String image;
    private String isRecom;

    @Override
    public String toString() {
        return "UserAppointmentGoodsBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", salePrice=" + salePrice +
                ", costPrice=" + costPrice +
                ", marketPrice=" + marketPrice +
                ", image='" + image + '\'' +
                ", isRecom='" + isRecom + '\'' +
                '}';
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
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

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsRecom() {
        return isRecom;
    }

    public void setIsRecom(String isRecom) {
        this.isRecom = isRecom;
    }
}
