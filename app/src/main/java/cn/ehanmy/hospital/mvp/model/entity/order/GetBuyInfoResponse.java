package cn.ehanmy.hospital.mvp.model.entity.order;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetBuyInfoResponse extends BaseResponse {
    private int nextPageIndex;
    private List<GoodsOrderBean> goodsList;

    @Override
    public String toString() {
        return "GetBuyInfoResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", goodsList=" + goodsList +
                '}';
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<GoodsOrderBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsOrderBean> goodsList) {
        this.goodsList = goodsList;
    }
}
