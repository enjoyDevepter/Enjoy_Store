package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;

public class UserAppointmentMember implements Serializable {
    private String memberId;
    private String mobile;

    @Override
    public String toString() {
        return "UserAppointmentMember{" +
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
