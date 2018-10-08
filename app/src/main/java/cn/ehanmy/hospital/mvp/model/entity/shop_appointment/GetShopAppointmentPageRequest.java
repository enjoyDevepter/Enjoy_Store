package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetShopAppointmentPageRequest extends BaseRequest {
    private final int cmd = 5401;
    private String token;
    private int pageIndex;
    private int pageSize;
    private String status;  // 0:预约中;2:已完成;3:已取消 为空时，查询全部
    private String search;

    @Override
    public String toString() {
        return "GetShopAppointmentPageRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", status='" + status + '\'' +
                ", search='" + search + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
