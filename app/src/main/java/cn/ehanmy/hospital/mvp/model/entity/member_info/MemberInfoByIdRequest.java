package cn.ehanmy.hospital.mvp.model.entity.member_info;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

// 2.2.2	获取APP会员信息
public class MemberInfoByIdRequest extends BaseRequest {
    private String token;
    private String memberId;
    private final int cmd = 10052;

    @Override
    public String toString() {
        return "MemberInfoByIdRequest{" +
                "token='" + token + '\'' +
                ", memberId='" + memberId + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getCmd() {
        return cmd;
    }
}
