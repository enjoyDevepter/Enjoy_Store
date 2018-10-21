package cn.ehanmy.hospital.mvp.model.entity.member_info;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetMessageListRequest extends BaseRequest {
    private final int cmd = 10056;
    private int pageIndex;
    private int pageSize;
    private String memberId;
    private String token;

    @Override
    public String toString() {
        return "GetMessageListRequest{" +
                "cmd=" + cmd +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", memberId='" + memberId + '\'' +
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
