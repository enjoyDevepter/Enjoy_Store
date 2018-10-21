package cn.ehanmy.hospital.mvp.model.entity.goods_list;

public class GoodsConfirmBean {
    private String goodsId;
    private String merchId;
    private String specValueId;
    private int nums;
    private long money;
    private double salePrice;  // 单位元

    @Override
    public String toString() {
        return "GoodsConfirmBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", specValueId='" + specValueId + '\'' +
                ", nums=" + nums +
                ", money=" + money +
                ", salePrice=" + salePrice +
                '}';
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}
