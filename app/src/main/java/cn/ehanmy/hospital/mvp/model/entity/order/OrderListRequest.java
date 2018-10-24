package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.4.1	获取医美项目订单列表
public class OrderListRequest extends BaseRequest {
    private final int cmd = 10501;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String token;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private String search;
    private String orderStatus;  // 1:待付款3:待发货4:待收货31:待预约;5:已完成

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrderListRequest{" +
                "cmd=" + cmd +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
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
