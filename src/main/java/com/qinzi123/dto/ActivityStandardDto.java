package com.qinzi123.dto;

import lombok.Data;

/**
 * @title: ProductInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/7/26 0022 11:37
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class ActivityStandardDto {

    private int productId;
    private String productName;
    private String adultsNum;
    private String childrenNum;
    private String price;
    private String distribution;
    private String inventory;
    private String onceMaxPurchaseCount;
    private String onceMinPurchaseCount;
    private String createTime;
}
