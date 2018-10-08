package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.3.1	获取医美项目列表
public class GoodsPageRequest extends BaseRequest {
    private String secondCategoryId;
    private String categoryId;
    private int pageIndex = 1;  // 从1开始
    private int pageSize = 10;
    private OrderBy orderBy;
    private String token;
    private int cmd = 5101;

    @Override
    public String toString() {
        return "GoodsPageRequest{" +
                "secondCategoryId='" + secondCategoryId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", orderBy=" + orderBy +
                ", token='" + token + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(String secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
