package com.qinzi123.dto;

import lombok.Data;

/**
 * @title: ActivityStandardInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/8 0008 14:06
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class ActivityStandardInfo {

    private int id;
    private String adultsNum;
    private String childrenNum;
    //分销佣金
    private String distribution;
    private String inventory;
    private String onceMaxPurchaseCount;
    private String onceMinPurchaseCount;
    private String price;
}
