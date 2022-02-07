package com.qinzi123.dto;

import java.util.List;

/**
 * @title: WxCitys
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class WxCitys {
    private String name;
    private List<WxOneCity> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WxOneCity> getList() {
        return list;
    }

    public void setList(List<WxOneCity> list) {
        this.list = list;
    }
}
