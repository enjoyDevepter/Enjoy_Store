package cn.ehanmy.hospital.mvp.model.entity.hospital;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.AreaAddress;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;


/**
 * Created by guomin on 2018/7/28.
 */

public class AllAddressResponse extends BaseResponse {

    private List<AreaAddress> areaList;

    public List<AreaAddress> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaAddress> areaList) {
        this.areaList = areaList;
    }

    @Override
    public String toString() {
        return "AllAddressResponse{" +
                "areaList=" + areaList +
                '}';
    }
}
