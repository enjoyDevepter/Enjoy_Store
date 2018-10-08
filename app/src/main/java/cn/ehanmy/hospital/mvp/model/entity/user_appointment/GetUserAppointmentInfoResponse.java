package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetUserAppointmentInfoResponse extends BaseResponse {
    private OrderProjectDetailBean orderProjectDetail;

    @Override
    public String toString() {
        return "GetUserAppointmentInfoResponse{" +
                "orderProjectDetail=" + orderProjectDetail +
                '}';
    }

    public OrderProjectDetailBean getOrderProjectDetail() {
        return orderProjectDetail;
    }

    public void setOrderProjectDetail(OrderProjectDetailBean orderProjectDetail) {
        this.orderProjectDetail = orderProjectDetail;
    }
}
