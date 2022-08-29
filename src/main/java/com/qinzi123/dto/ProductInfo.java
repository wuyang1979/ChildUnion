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
    private int isHot;
    private String cardId;
    private int shopId;
    private String name;
    private String address;
    private String addressName;
    private String longitude;
    private String latitude;
    private int productType;
    private int activityType1;
    private int activityType2;
    private int type;
    private int productStyle;
    private String wuyuType;
    private String mainImage;
    private int isAllowDistribution;
    private int repeatPurchase;
    private String phone;
    private String introduce;
    private String videoPath;
    private String instruction;
    private String buyCount;
    private Date createTime;
    private String deadlineTime;
    private String qrImage;
}
