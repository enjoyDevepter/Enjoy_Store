package cn.ehanmy.hospital.mvp.model.entity.hospital;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * Created by guomin on 2018/7/25.
 */

public class SimpleRequest extends BaseRequest {
    private int cmd;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "SimpleRequest{" +
                "cmd=" + cmd +
                '}';
    }
}
