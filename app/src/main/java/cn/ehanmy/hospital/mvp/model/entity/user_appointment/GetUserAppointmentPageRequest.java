package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetUserAppointmentPageRequest extends BaseRequest {
    private int cmd = 10351;
    private String token;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String status;  // 0:新预约;1:已确认;2:已完成 为空时，查询全部
    private String search;

    @Override
    public String toString() {
        return "GetUserAppointmentPageRequest{" +
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

    public void setCmd(int cmd) {
        this.cmd = cmd;
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
