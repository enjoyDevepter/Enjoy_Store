package cn.ehanmy.hospital.mvp.model.entity.goods_list;

public class GoodsConfirmWithSpecBean {
    private String goodsId;
    private String merchId;
    private int nums;
    private double salePrice;
    private String specValueId;

    @Override
    public String toString() {
        return "GoodsConfirmWithSpecBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", nums=" + nums +
                ", salePrice=" + salePrice +
                ", specValueId='" + specValueId + '\'' +
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

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }
}
