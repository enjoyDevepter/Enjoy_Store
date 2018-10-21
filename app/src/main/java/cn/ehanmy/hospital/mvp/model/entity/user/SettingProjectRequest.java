package cn.ehanmy.hospital.mvp.model.entity.user;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class SettingProjectRequest extends BaseRequest {
    private final int cmd = 10203;
    private String token;
    private List<String> merchIdList;

    @Override
    public String toString() {
        return "SettingProjectRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", merchIdList=" + merchIdList +
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

    public List<String> getMerchIdList() {
        return merchIdList;
    }

    public void setMerchIdList(List<String> merchIdList) {
        this.merchIdList = merchIdList;
    }
}
