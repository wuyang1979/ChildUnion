package com.qinzi123.dto;

import java.util.Map;

/**
 * @title: BaseMessage
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/9 0009 10:43
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public class BaseMessage {
    private String toUser;
    private String templateId;
    private String page;
    private Map<String,TemplateData> data;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, TemplateData> getData() {
        return data;
    }

    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }
}
