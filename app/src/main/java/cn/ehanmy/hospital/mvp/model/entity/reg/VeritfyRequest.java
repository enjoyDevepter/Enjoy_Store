package cn.ehanmy.hospital.mvp.model.entity.reg;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * Created by guomin on 2018/7/25.
 */

public class VeritfyRequest extends BaseRequest {
    private String mobile;
    private int cmd = 10058;
    private String token;
    private String verifyCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
