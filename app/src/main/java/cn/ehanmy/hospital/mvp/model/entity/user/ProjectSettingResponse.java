package cn.ehanmy.hospital.mvp.model.entity.user;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class ProjectSettingResponse extends BaseResponse {
    private List<String> merchIdList;

    @Override
    public String toString() {
        return "ProjectSettingResponse{" +
                "merchIdList=" + merchIdList +
                '}';
    }

    public List<String> getMerchIdList() {
        return merchIdList;
    }

    public void setMerchIdList(List<String> merchIdList) {
        this.merchIdList = merchIdList;
    }
}
