package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class OrderInfoResponse extends BaseResponse {
    private OrderInfoBean order;

    @Override
    public String toString() {
        return "OrderInfoResponse{" +
                "order=" + order +
                '}';
    }

    public OrderInfoBean getOrder() {
        return order;
    }

    public void setOrder(OrderInfoBean order) {
        this.order = order;
    }
}
