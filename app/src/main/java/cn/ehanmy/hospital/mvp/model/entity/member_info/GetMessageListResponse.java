package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetMessageListResponse extends BaseResponse {
    private List<MemberMessageBean> privateMessageList;
    private int nextPageIndex;

    @Override
    public String toString() {
        return "GetMessageListResponse{" +
                "privateMessageList=" + privateMessageList +
                ", nextPageIndex=" + nextPageIndex +
                '}';
    }

    public List<MemberMessageBean> getPrivateMessageList() {
        return privateMessageList;
    }

    public void setPrivateMessageList(List<MemberMessageBean> privateMessageList) {
        this.privateMessageList = privateMessageList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
