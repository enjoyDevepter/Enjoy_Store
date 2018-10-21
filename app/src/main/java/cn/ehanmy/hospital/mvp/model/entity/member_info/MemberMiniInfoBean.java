package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.io.Serializable;

public class MemberMiniInfoBean implements Serializable {
    private String memberId;
    private String userName;
    private String nickName;
    private String mobile;
    private String headImage;

    @Override
    public String toString() {
        return "MemberMiniInfoBean{" +
                "memberId='" + memberId + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", headImage='" + headImage + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
