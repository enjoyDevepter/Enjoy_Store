package cn.ehanmy.hospital.mvp.model.entity.activity;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetActivityInfoResponse extends BaseResponse {
    private int nextPageIndex;
    private List<ActivityInfoBean> activityInfoList;

    @Override
    public String toString() {
        return "GetActivityInfoResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", activityInfoList=" + activityInfoList +
                '}';
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<ActivityInfoBean> getActivityInfoList() {
        return activityInfoList;
    }

    public void setActivityInfoList(List<ActivityInfoBean> activityInfoList) {
        this.activityInfoList = activityInfoList;
    }
}
