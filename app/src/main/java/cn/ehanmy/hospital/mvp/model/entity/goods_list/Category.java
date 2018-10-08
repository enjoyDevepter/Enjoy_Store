package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.util.List;

/**
 * Created by guomin on 2018/7/28.
 */

public class Category {
    private String categoryId;
    private List<Category> goodsCategoryList;
    private String name;
    private String busType;  // 3 医美
    private boolean choice;

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", goodsCategoryList=" + goodsCategoryList +
                ", name='" + name + '\'' +
                ", busType='" + busType + '\'' +
                ", choice=" + choice +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<Category> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<Category> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }
}
