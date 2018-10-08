package cn.ehanmy.hospital.mvp.model.entity.order;

import java.io.Serializable;

/**订单中套餐商品集合*/
public class SetMealGoodsBean implements Serializable{
    private String setMealId;
    private String image;
    private String name;
    private double salePrice;
    private double totalPrice;
    private String title;
    private int nums;

    @Override
    public String toString() {
        return "SetMealGoodsBean{" +
                "setMealId='" + setMealId + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", totalPrice=" + totalPrice +
                ", title='" + title + '\'' +
                ", nums=" + nums +
                '}';
    }

    public String getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(String setMealId) {
        this.setMealId = setMealId;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
}
