package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetRelatedListResponse extends BaseResponse {
    private int nextPageIndex;
    private List<RelatedOrderBean> orderList;

    @Override
    public String toString() {
        return "GetRelatedListResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", orderList=" + orderList +
                '}';
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<RelatedOrderBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<RelatedOrderBean> orderList) {
        this.orderList = orderList;
    }
}
