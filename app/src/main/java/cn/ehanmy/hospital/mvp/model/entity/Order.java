package cn.ehanmy.hospital.mvp.model.entity;

import java.io.Serializable;

/**
 * Created by guomin on 2018/8/3.
 */

public class Order implements Serializable{
    private String orderId;
    private String phone;
    private String price;
    private String project;
    private String status;
    private String time;

    public Order(){}

    public Order(String orderId, String phone, String price, String project, String status, String time) {
        this.orderId = orderId;
        this.phone = phone;
        this.price = price;
        this.project = project;
        this.status = status;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", phone='" + phone + '\'' +
                ", price='" + price + '\'' +
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
