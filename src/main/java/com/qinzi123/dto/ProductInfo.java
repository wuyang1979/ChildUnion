package com.qinzi123.dto;

import lombok.Data;

import java.util.Date;

/**
 * @title: ProductInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:37
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class ProductInfo {

    private int id;
    private int status;
    private int type;
    private String cardId;
    private String name;
    private String mainImage;
    private String originalPrice;
    private String presentPrice;
    private String inventory;
    private int repeatPurchase;
    private int onceMaxPurchaseCount;
    private String phone;
    private String introduce;
    private String videoPath;
    private String instruction;
    private Date createTime;
    private String deadlineTime;
}
