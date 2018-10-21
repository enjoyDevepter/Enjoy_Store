package cn.ehanmy.hospital.mvp.model.entity.user;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetCategoryGoodsListRequest extends BaseRequest {
    private final int cmd = 10204;
    private String secondCategoryId;
    private String categoryId;

    @Override
    public String toString() {
        return "GetCategoryGoodsListRequest{" +
                "cmd=" + cmd +
                ", secondCategoryId='" + secondCategoryId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
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
}
