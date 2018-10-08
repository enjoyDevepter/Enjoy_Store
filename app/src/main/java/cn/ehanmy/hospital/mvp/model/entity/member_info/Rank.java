package cn.ehanmy.hospital.mvp.model.entity.member_info;

import java.io.Serializable;

/*
* 		“rank”: {
			“pointLevelId”: “1”,
			“pointLevelName”: “普通会员”
		},
* */
public class Rank implements Serializable{
    private String pointLevelId;
    private String pointLevelName;

    @Override
    public String toString() {
        return "Rank{" +
                "pointLevelId='" + pointLevelId + '\'' +
                ", pointLevelName='" + pointLevelName + '\'' +
                '}';
    }

    public String getPointLevelId() {
        return pointLevelId;
    }

    public void setPointLevelId(String pointLevelId) {
        this.pointLevelId = pointLevelId;
    }

    public String getPointLevelName() {
        return pointLevelName;
    }

    public void setPointLevelName(String pointLevelName) {
        this.pointLevelName = pointLevelName;
    }
}
