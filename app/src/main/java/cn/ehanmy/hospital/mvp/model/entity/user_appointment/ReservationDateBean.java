package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;
import java.util.List;

public class ReservationDateBean implements Serializable {
    private String date;
    private List<ReservationTimeBean> reservationTimeList;
    private boolean isChoose;

    @Override
    public String toString() {
        return "ReservationDateBean{" +
                "date='" + date + '\'' +
                ", reservationTimeList=" + reservationTimeList +
                ", isChoose=" + isChoose +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ReservationTimeBean> getReservationTimeList() {
        return reservationTimeList;
    }

    public void setReservationTimeList(List<ReservationTimeBean> reservationTimeList) {
        this.reservationTimeList = reservationTimeList;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
