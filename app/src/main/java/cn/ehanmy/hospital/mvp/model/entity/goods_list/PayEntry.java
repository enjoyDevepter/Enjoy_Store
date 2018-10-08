package cn.ehanmy.hospital.mvp.model.entity.goods_list;

public class PayEntry {
    private String payId;
    private String type;
    private String name;
    private String image;
    private String extendParams;

    @Override
    public String toString() {
        return "PayEntry{" +
                "payId='" + payId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", extendParams='" + extendParams + '\'' +
                '}';
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }
}
