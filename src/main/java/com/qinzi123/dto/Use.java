package com.qinzi123.dto;

/**
 * @title: Use
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public enum Use {
    USE(1), NOUSE(0);

    int isUse;

    private Use(int isUse) {
        this.isUse = isUse;
    }

    public int getIsUse() {
        return isUse;
    }
}
