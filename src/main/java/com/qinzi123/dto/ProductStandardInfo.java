package com.qinzi123.dto;

import lombok.Data;

/**
 * @title: ProductStandardInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/8 0008 17:43
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class ProductStandardInfo {

    private int id;
    private String name;
    private String price;
    //分销佣金
    private String distribution;
    private String inventory;
    private String onceMaxPurchaseCount;
    private String onceMinPurchaseCount;
}
