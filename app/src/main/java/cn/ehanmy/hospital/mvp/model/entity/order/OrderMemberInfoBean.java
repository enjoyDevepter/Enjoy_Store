package cn.ehanmy.hospital.mvp.model.entity.order;

import java.io.Serializable;

public class OrderMemberInfoBean implements Serializable {
    private String memberId;
    private String mobile;

    @Override
    public String toString() {
        return "OrderMemberInfoBean{" +
                "memberId='" + memberId + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
