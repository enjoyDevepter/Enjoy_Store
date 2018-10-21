package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetMemberListResponse extends BaseResponse {
    private int nextPageIndex;
    private List memberList;

    @Override
    public String toString() {
        return "GetMemberListResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", memberList=" + memberList +
                '}';
    }

    public List getMemberList() {
        return memberList;
    }

    public void setMemberList(List memberList) {
        this.memberList = memberList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
