package com.qinzi123.dto;

/**
 * @title: OrderStatusType
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子GO
 * @author: jie.yuan
 * @date: 2022/02/16 0025 12:10
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public enum OrderStatusType {
    PendingPayment(0), PendingConfirm(1), Completed(2), Invalid(3);

    int type;

    OrderStatusType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
