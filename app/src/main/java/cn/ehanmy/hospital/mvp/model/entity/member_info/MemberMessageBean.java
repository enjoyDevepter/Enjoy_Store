package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.io.Serializable;

public class MemberMessageBean implements Serializable {
    private String memberId;
    private String content;
    private String replyContent;
    private String sendTime;
    private String replyTime;

    @Override
    public String toString() {
        return "MemberMessageBean{" +
                "memberId='" + memberId + '\'' +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", replyTime='" + replyTime + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}
