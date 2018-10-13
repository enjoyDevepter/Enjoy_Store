package cn.ehanmy.hospital.mvp.model.entity.store;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetStroeInfoResponse extends BaseResponse {
    private StoreBean store;

    @Override
    public String toString() {
        return "GetStroeInfoResponse{" +
                "store=" + store +
                '}';
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }
}
