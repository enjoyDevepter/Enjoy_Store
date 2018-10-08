package cn.ehanmy.hospital.mvp.model.entity.goods_list;

public class OrderBy {
    private boolean asc;
    private String field;

    @Override
    public String toString() {
        return "OrderBy{" +
                "asc=" + asc +
                ", field='" + field + '\'' +
                '}';
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
