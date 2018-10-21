package cn.ehanmy.hospital.mvp.model.entity.user;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetCategoryGoodsListResponse extends BaseResponse {
    private List<MerchBean> merchList;

    @Override
    public String toString() {
        return "GetCategoryGoodsListResponse{" +
                "merchList=" + merchList +
                '}';
    }

    public List<MerchBean> getMerchList() {
        return merchList;
    }

    public void setMerchList(List<MerchBean> merchList) {
        this.merchList = merchList;
    }
}
