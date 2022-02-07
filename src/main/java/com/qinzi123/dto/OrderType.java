package com.qinzi123.dto;

/**
 * @title: OrderType
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public enum OrderType {
    SCORE(0), PAY(1), LEAGUE(2);

    int type;

    OrderType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}


