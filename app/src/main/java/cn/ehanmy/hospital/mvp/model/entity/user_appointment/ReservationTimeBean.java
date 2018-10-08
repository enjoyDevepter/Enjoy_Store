package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;

public class ReservationTimeBean implements Serializable {
    private String full;  // 1:是0:否
    private String time;
    private boolean isChoose;

    @Override
    public String toString() {
        return "ReservationTimeBean{" +
                "full='" + full + '\'' +
                ", time='" + time + '\'' +
                ", isChoose=" + isChoose +
                '}';
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
