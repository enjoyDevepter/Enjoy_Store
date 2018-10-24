package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class OrderPayResponse extends BaseResponse {
    private String params;
    private String payStatus;  // 1:已支付0:未支付

    @Override
    public String toString() {
        return "OrderPayResponse{" +
                "params='" + params + '\'' +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
