package com.qinzi123.dto.push.message;

/**
 * @title: MessageSendObject
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class MessageSendObject {
    String touser;
    String template_id;
    String page;
    Object data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString(){
        return String.format("touser=%s,template_id=%s,page=%s,data=%s",
                touser, template_id, page, data);
    }
}
