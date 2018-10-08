package cn.ehanmy.hospital.mvp.model.entity.activity;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ChangeActivityInfoRequest extends BaseRequest {
    private final int cmd = 5253;
    private String activityId;
    private String title;
    private String content;
    private List<String> imageList;
    private String token;

    @Override
    public String toString() {
        return "ChangeActivityInfoRequest{" +
                "cmd=" + cmd +
                ", activityId='" + activityId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageList=" + imageList +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
