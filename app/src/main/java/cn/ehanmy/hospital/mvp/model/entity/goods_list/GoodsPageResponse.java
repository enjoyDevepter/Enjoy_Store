package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GoodsPageResponse extends BaseResponse {
    private List<Goods> goodsList;
    private int nextPageIndex;

    @Override
    public String toString() {
        return "GoodsPageResponse{" +
                "goodsList=" + goodsList +
                ", nextPageIndex=" + nextPageIndex +
                '}';
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
