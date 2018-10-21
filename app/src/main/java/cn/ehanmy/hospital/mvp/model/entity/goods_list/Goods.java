package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.io.Serializable;

/**
 * 下单中心，在列表中展示的商品的实体类
 */
public class Goods implements Serializable {
    private int attention;
    private int cnt;
    private double costPrice;
    private int favorite;
    private String goodsId;
    private GoodsSpecValueBean goodsSpecValue;
    private String image;
    private double marketPrice;
    private String merchId;
    private String specValueId;
    private String name;
    private double salePrice;
    private int sales;
    private String title;
    private int nums = 1;
    private String code;

    @Override
    public String toString() {
        return "Goods{" +
                "attention=" + attention +
                ", cnt=" + cnt +
                ", costPrice=" + costPrice +
                ", favorite=" + favorite +
                ", goodsId='" + goodsId + '\'' +
                ", goodsSpecValue=" + goodsSpecValue +
                ", image='" + image + '\'' +
                ", marketPrice=" + marketPrice +
                ", merchId='" + merchId + '\'' +
                ", specValueId='" + specValueId + '\'' +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", sales=" + sales +
                ", title='" + title + '\'' +
                ", nums=" + nums +
                ", code='" + code + '\'' +
                '}';
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsSpecValueBean getGoodsSpecValue() {
        return goodsSpecValue;
    }

    public void setGoodsSpecValue(GoodsSpecValueBean goodsSpecValue) {
        this.goodsSpecValue = goodsSpecValue;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
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

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
