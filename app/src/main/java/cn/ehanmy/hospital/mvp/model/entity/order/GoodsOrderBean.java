package cn.ehanmy.hospital.mvp.model.entity.order;

import java.io.Serializable;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;

/**用于订单接口中表示商品的类*/
public class GoodsOrderBean implements Serializable{
    private String goodsId;
    private String merchId;
    private String image;
    private String name;
    private double salePrice;  // 销售价 单位元
    private double deposit;
    private double tailMoney;  // 尾款，单位元
    private String title;
    private int nums;
    private GoodsSpecValueBean goodsSpecValue;
    private List<SetMealGoodsBean> setMealGoodsList;

    @Override
    public String toString() {
        return "GoodsOrderBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", deposit=" + deposit +
                ", tailMoney=" + tailMoney +
                ", title='" + title + '\'' +
                ", nums=" + nums +
                ", goodsSpecValue=" + goodsSpecValue +
                ", setMealGoodsList=" + setMealGoodsList +
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getTailMoney() {
        return tailMoney;
    }

    public void setTailMoney(double tailMoney) {
        this.tailMoney = tailMoney;
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

    public List<SetMealGoodsBean> getSetMealGoodsList() {
        return setMealGoodsList;
    }

    public void setSetMealGoodsList(List<SetMealGoodsBean> setMealGoodsList) {
        this.setMealGoodsList = setMealGoodsList;
    }
}
