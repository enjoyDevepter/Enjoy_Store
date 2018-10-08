package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import java.io.Serializable;

public class GoodsSpecValueBean implements Serializable{
    private String specValueId;
    private String specValueName;

    @Override
    public String toString() {
        return "GoodsSpecValue{" +
                "specValueId='" + specValueId + '\'' +
                ", specValueName='" + specValueName + '\'' +
                '}';
    }

    public String getSpecValueName() {
        return specValueName;
    }

    public void setSpecValueName(String specValueName) {
        this.specValueName = specValueName;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }
}
