package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.io.Serializable;

/**账号绑定的医院的详情信息。在登录后立刻请求*/
public class MemberBean implements Serializable{
    private String userName;
    private Rank rank;
    private String headImage;
    private String sex;  // 0:保密,1:男,2:女
    private String realName;
    private String mobile;
    private Province province;
    private City city;
    private County county;
    private String address;
    private String memberId;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String nickName;

    @Override
    public String toString() {
        return "MemberBean{" +
                "userName='" + userName + '\'' +
                ", rank=" + rank +
                ", headImage='" + headImage + '\'' +
                ", sex='" + sex + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", county=" + county +
                ", address='" + address + '\'' +
                ", memberId='" + memberId + '\'' +
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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
