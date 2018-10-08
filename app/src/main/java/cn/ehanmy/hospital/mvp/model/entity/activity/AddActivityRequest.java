package cn.ehanmy.hospital.mvp.model.entity.activity;

import java.util.List;

public class AddActivityRequest {
    private final int cmd = 5251;
    private String title;
    private String content;
    private List<String> imageList;
    private String token;

    @Override
    public String toString() {
        return "AddActivityRequest{" +
                "cmd=" + cmd +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageList=" + imageList +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
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
