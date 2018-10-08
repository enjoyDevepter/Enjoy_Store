package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import java.io.Serializable;

public class RelatedOrderGoodsBean implements Serializable{
    private String goodsId;
    private String merchId;
    private long deposit;  // 预定金额
    private long tailMoney;  // 尾款
    private String name;
    private String title;
    private double salePrice;
    private double costPrice;
    private double marketPrice;
    private String image;
    private String isRecom;
    private String status;

    @Override
    public String toString() {
        return "RelatedOrderGoodsBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", deposit=" + deposit +
                ", tailMoney=" + tailMoney +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", salePrice=" + salePrice +
                ", costPrice=" + costPrice +
                ", marketPrice=" + marketPrice +
                ", image='" + image + '\'' +
                ", isRecom='" + isRecom + '\'' +
                ", status='" + status + '\'' +
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

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public long getTailMoney() {
        return tailMoney;
    }

    public void setTailMoney(long tailMoney) {
        this.tailMoney = tailMoney;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
