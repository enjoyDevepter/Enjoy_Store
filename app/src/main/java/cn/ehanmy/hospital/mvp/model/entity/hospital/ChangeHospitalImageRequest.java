package cn.ehanmy.hospital.mvp.model.entity.hospital;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ChangeHospitalImageRequest extends BaseRequest {
    private final int cmd = 10302;
    private String token;
    private String image;

    @Override
    public String toString() {
        return "ChangeHospitalImageRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
