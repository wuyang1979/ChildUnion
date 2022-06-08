package com.qinzi123.dto;

import lombok.Data;

import java.util.Date;

/**
 * @title: ShopInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/3/24 0024 16:21
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class ShopInfo {

    private int id;
    private int cardId;
    private String name;
    private String canWithdrawalMoney;
    private String alreadyWithdrawalMoney;
    private String progressWithdrawalMoney;
    private int shopType;
    private String createTime;
}
