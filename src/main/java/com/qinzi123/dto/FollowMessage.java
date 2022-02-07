package com.qinzi123.dto;

/**
 * @title: FollowMessage
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/4 0004 12:12
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class FollowMessage extends BaseParam {
    int id;
    String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
