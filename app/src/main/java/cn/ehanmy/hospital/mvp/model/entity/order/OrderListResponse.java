package cn.ehanmy.hospital.mvp.model.entity.order;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class OrderListResponse extends BaseResponse {
    private int nextPageIndex;
    private List<OrderBean> orderList;

    @Override
    public String toString() {
        return "OrderListResponse{" +
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

    public List<OrderBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderBean> orderList) {
        this.orderList = orderList;
    }
}
