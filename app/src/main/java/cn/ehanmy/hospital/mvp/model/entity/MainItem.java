package cn.ehanmy.hospital.mvp.model.entity;

/**
 * Created by guomin on 2018/8/1.
 */

public class MainItem {

    private int imageId;
    private String name;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MainItem{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                '}';
    }
}
