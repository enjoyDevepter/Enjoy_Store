package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;

public class OrderProjectDetailBean implements Serializable {
    private String projectId;
    private int surplusNum;
    private int times;
    private String status;
    private String relateStatus;
    private String relateStatusDesc;
    private String statusDesc;
    private String createDate;
    private String reservationDate;
    private String reservationTime;
    private String reservationId;
    private String reservationStatus;
    private String reservationStatusDesc;
    private String code;
    private String isExperience;  // 是否为体验卡
    private String confirmTime;
    private String deductTime;  // 划扣时间
    private String phone;
    private String address;
    private UserAppointmentGoodsBean goods;
    private UserAppointmentMember member;

    public String getReservationStatusDesc() {
        return reservationStatusDesc;
    }

    public void setReservationStatusDesc(String reservationStatusDesc) {
        this.reservationStatusDesc = reservationStatusDesc;
    }


    public String getRelateStatus() {
        return relateStatus;
    }

    public void setRelateStatus(String relateStatus) {
        this.relateStatus = relateStatus;
    }

    public String getRelateStatusDesc() {
        return relateStatusDesc;
    }

    public void setRelateStatusDesc(String relateStatusDesc) {
        this.relateStatusDesc = relateStatusDesc;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "OrderProjectDetailBean{" +
                "projectId='" + projectId + '\'' +
                ", surplusNum=" + surplusNum +
                ", status='" + status + '\'' +
                ", relateStatus='" + relateStatus + '\'' +
                ", relateStatusDesc='" + relateStatusDesc + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", createDate='" + createDate + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", reservationStatusDesc='" + reservationStatusDesc + '\'' +
                ", code='" + code + '\'' +
                ", isExperience='" + isExperience + '\'' +
                ", confirmTime='" + confirmTime + '\'' +
                ", deductTime='" + deductTime + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", goods=" + goods +
                ", member=" + member +
                '}';
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsExperience() {
        return isExperience;
    }

    public void setIsExperience(String isExperience) {
        this.isExperience = isExperience;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getDeductTime() {
        return deductTime;
    }

    public void setDeductTime(String deductTime) {
        this.deductTime = deductTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserAppointmentGoodsBean getGoods() {
        return goods;
    }

    public void setGoods(UserAppointmentGoodsBean goods) {
        this.goods = goods;
    }

    public UserAppointmentMember getMember() {
        return member;
    }

    public void setMember(UserAppointmentMember member) {
        this.member = member;
    }
}
