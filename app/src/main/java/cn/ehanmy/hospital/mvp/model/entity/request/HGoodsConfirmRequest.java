package cn.ehanmy.hospital.mvp.model.entity.request;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;

// 2.3.2	获取医美项目确认信息
public class HGoodsConfirmRequest extends BaseRequest {
    private String token;
    private GoodsConfirmBean goods;
    private String hospitalId;
    private String reservationDate;
    private String reservationTime;
    private String memberId;
    private int cmd = 10105;

    @Override
    public String toString() {
        return "HGoodsConfirmRequest{" +
                "token='" + token + '\'' +
                ", goods=" + goods +
                ", hospitalId='" + hospitalId + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", memberId='" + memberId + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GoodsConfirmBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsConfirmBean goods) {
        this.goods = goods;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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
}
