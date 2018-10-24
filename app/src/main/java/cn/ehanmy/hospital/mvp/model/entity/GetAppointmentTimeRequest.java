package cn.ehanmy.hospital.mvp.model.entity;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetAppointmentTimeRequest extends BaseRequest {
    private int cmd;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String token;
    private String goodsId;
    private String merchId;
    private String projectId;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "GetAppointmentTimeRequest{" +
                "cmd=" + cmd +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", token='" + token + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
