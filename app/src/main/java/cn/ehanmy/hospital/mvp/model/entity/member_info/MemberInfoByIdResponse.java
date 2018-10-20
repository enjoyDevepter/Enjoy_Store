package cn.ehanmy.hospital.mvp.model.entity.member_info;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class MemberInfoByIdResponse extends BaseResponse {
    private MemberBean member;

    @Override
    public String toString() {
        return "MemberInfoResponse{" +
                "member=" + member +
                '}';
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }
}
