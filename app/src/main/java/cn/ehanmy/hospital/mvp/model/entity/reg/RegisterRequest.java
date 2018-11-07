package cn.ehanmy.hospital.mvp.model.entity.reg;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * Created by guomin on 2018/7/25.
 */

public class RegisterRequest extends BaseRequest {
    private String step1Token;
    private String mobile;
    private String nickName;
    private String sex;
    private String birthday;
    private String type;
    private String code;
    private int cmd = 10060;
    private String token;
    private String realName;

    public String getStep1Token() {
        return step1Token;
    }

    public void setStep1Token(String step1Token) {
        this.step1Token = step1Token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "step1Token='" + step1Token + '\'' +
                ", mobile='" + mobile + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", cmd=" + cmd +
                ", token='" + token + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
