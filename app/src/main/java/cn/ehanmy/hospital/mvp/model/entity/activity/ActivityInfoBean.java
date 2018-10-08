package cn.ehanmy.hospital.mvp.model.entity.activity;

import java.io.Serializable;

public class ActivityInfoBean implements Serializable {
    private String title;
    private String content;
    private String image;
    private String activityId;

    @Override
    public String toString() {
        return "ActivityInfoBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
