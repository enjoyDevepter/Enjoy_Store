package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetBuyInfoRequest extends BaseRequest {
    private final int cmd = 10503;
    private String orderId;
    private int pageIndex;
    private int pageSize;
    private String token;

    @Override
    public String toString() {
        return "GetBuyInfoRequest{" +
                "cmd=" + cmd +
                ", orderId='" + orderId + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
