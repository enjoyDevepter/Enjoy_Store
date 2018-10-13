package cn.ehanmy.hospital.mvp.model.entity.request;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmWithSpecBean;

// 2.3.2	获取医美项目确认信息
public class GoodsConfirmWithSpecRequest extends BaseRequest {
    private final int cmd = 10154;
    private String token;
    private GoodsConfirmWithSpecBean goods;
    private String memberId;
    private long money;

    @Override
    public String toString() {
        return "GoodsConfirmWithSpecRequest{" +
                "token='" + token + '\'' +
                ", goods=" + goods +
                ", memberId='" + memberId + '\'' +
                ", money=" + money +
                ", cmd=" + cmd +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GoodsConfirmWithSpecBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsConfirmWithSpecBean goods) {
        this.goods = goods;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getCmd() {
        return cmd;
    }
}
