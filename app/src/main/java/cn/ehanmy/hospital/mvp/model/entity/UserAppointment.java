package cn.ehanmy.hospital.mvp.model.entity;

import java.io.Serializable;

public class UserAppointment implements Serializable {
    private String orderId;
    private String phone;
    private String project;
    private String status;
    private String time;

    public UserAppointment(){}

    public UserAppointment(String orderId, String phone, String project, String status, String time) {
        this.orderId = orderId;
        this.phone = phone;
        this.project = project;
        this.status = status;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", phone='" + phone + '\'' +
                ", project='" + project + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
