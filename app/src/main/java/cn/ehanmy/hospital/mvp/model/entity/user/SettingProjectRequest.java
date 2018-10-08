package cn.ehanmy.hospital.mvp.model.entity.user;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class SettingProjectRequest extends BaseRequest {
    private final int cmd = 5203;
    private String token;
    private List<String> categoryList;

    @Override
    public String toString() {
        return "SettingProjectRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", categoryList=" + categoryList +
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

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }
}
