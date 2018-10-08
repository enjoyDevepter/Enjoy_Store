package cn.ehanmy.hospital.mvp.model.entity.member_info;

import cn.ehanmy.hospital.mvp.model.entity.member_info.City;
import cn.ehanmy.hospital.mvp.model.entity.member_info.County;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.Province;
import cn.ehanmy.hospital.mvp.model.entity.member_info.Rank;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class MemberInfoResponse extends BaseResponse {
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
