package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.io.Serializable;
import java.util.List;

public class GoodsOrderBean implements Serializable{
    private String goodsId;
    private String merchId;
    private String code;
    private String image;
    private double marketPrice;
    private double costPrice;
    private String name;
    private double salePrice;
    private String title;
    private int nums;
    private GoodsSpecValueBean goodsSpecValue;

    @Override
    public String toString() {
        return "GoodsOrderBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", code='" + code + '\'' +
                ", image='" + image + '\'' +
                ", marketPrice=" + marketPrice +
                ", costPrice=" + costPrice +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", title='" + title + '\'' +
                ", nums=" + nums +
                ", goodsSpecValue=" + goodsSpecValue +
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public GoodsSpecValueBean getGoodsSpecValue() {
        return goodsSpecValue;
    }

    public void setGoodsSpecValue(GoodsSpecValueBean goodsSpecValue) {
        this.goodsSpecValue = goodsSpecValue;
    }
}
