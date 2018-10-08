package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

/**
 * Created by guomin on 2018/7/28.
 */

public class CategoryResponse extends BaseResponse {

    private List<Category> goodsCategoryList;

    public List<Category> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<Category> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "goodsCategoryList=" + goodsCategoryList +
                '}';
    }
}
