package com.qinzi123.dto;

/**
 * @title: TemplateData
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/15 0015 17:39
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public class TemplateData {
    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
