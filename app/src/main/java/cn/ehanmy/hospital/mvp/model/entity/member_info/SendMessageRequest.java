package cn.ehanmy.hospital.mvp.model.entity.member_info;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class SendMessageRequest extends BaseRequest {
    private final int cmd = 10057;
    private String content;
    private String memberId;
    private String token;

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "cmd=" + cmd +
                ", content='" + content + '\'' +
                ", memberId='" + memberId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
