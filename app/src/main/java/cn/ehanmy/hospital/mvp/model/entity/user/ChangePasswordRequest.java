package cn.ehanmy.hospital.mvp.model.entity.user;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class ChangePasswordRequest extends BaseRequest {
    private String oldUserPwd;
    private String newUserPwd;
    private String confirmUserPwd;
    private String token;

    public int getCmd() {
        return cmd;
    }

    private final int cmd = 10002;

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "oldUserPwd='" + oldUserPwd + '\'' +
                ", newUserPwd='" + newUserPwd + '\'' +
                ", confirmUserPwd='" + confirmUserPwd + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getOldUserPwd() {
        return oldUserPwd;
    }

    public void setOldUserPwd(String oldUserPwd) {
        this.oldUserPwd = oldUserPwd;
    }

    public String getNewUserPwd() {
        return newUserPwd;
    }

    public void setNewUserPwd(String newUserPwd) {
        this.newUserPwd = newUserPwd;
    }

    public String getConfirmUserPwd() {
        return confirmUserPwd;
    }

    public void setConfirmUserPwd(String confirmUserPwd) {
        this.confirmUserPwd = confirmUserPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
