package cn.ehanmy.hospital.mvp.model.entity;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;

public class GetAppointmentTimeResponse extends BaseResponse {
    private int nextPageIndex;
    private boolean isChoose;
    private List<ReservationDateBean> reservationDateList;


    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<ReservationDateBean> getReservationDateList() {
        return reservationDateList;
    }

    public void setReservationDateList(List<ReservationDateBean> reservationDateList) {
        this.reservationDateList = reservationDateList;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    @Override
    public String toString() {
        return "GetAppointmentTimeResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", isChoose=" + isChoose +
                ", reservationDateList=" + reservationDateList +
                '}';
    }
}
