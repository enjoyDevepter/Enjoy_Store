package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GoodsPageResponse extends BaseResponse {
    private List<GoodsListBean> goodsList;
    private int nextPageIndex;

    @Override
    public String toString() {
        return "GoodsPageResponse{" +
                "goodsList=" + goodsList +
                ", nextPageIndex=" + nextPageIndex +
                '}';
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
