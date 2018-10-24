package cn.ehanmy.hospital.mvp.model.entity.hospital;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.Hospital;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class HospitalListResponse extends BaseResponse {
    private List<Hospital> hospitalList;
    private int nextPageIndex;

    @Override
    public String toString() {
        return "HospitalListResponse{" +
                "hospitalList=" + hospitalList +
                ", nextPageIndex=" + nextPageIndex +
                '}';
    }

    public List<Hospital> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
