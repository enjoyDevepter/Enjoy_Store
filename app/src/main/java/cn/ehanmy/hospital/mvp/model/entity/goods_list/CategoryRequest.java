package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * Created by guomin on 2018/7/25.
 */

// 2.3.4	获取医美商品分类列表
public class CategoryRequest extends BaseRequest {
    private int cmd = 10201;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "cmd=" + cmd +
                '}';
    }
}
