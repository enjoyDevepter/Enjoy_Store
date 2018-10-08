package cn.ehanmy.hospital.mvp.model.entity.activity;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetActivityInfoRequest extends BaseRequest {
    private final int cmd = 5252;
    private String token;
    private int pageIndex;
    private int pageSize;
    private String status;  // 0:待审核；1:已审核

    @Override
    public String toString() {
        return "GetActivityInfoRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", status='" + status + '\'' +
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
}
