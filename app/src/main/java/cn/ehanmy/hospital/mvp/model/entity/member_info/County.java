package cn.ehanmy.hospital.mvp.model.entity.member_info;
/*
* 		“county”: {
			“id”: “”,
			“parentId”: “0”,
			“name”: null,
			“code”: null,
			“type”: null
		},
* */
public class County {
    private String id;
    private String parentId;
    private String name;
    private String code;
    private String type;

    @Override
    public String toString() {
        return "County{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
