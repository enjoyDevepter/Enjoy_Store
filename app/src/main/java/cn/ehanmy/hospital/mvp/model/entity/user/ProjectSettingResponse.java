package cn.ehanmy.hospital.mvp.model.entity.user;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class ProjectSettingResponse extends BaseResponse {
    private List<String> categoryList;

    @Override
    public String toString() {
        return "ProjectSettingResponse{" +
                "categoryList=" + categoryList +
                '}';
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }
}
