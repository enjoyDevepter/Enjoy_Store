package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetRelatedListRequest extends BaseRequest {
    private final int cmd = 5405;
    private String token;
    private int pageIndex;
    private int pageSize;
    private String memberId;

    @Override
    public String toString() {
        return "GetRelatedListRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", memberId='" + memberId + '\'' +
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
