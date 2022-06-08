package com.qinzi123.dto;

import lombok.Data;

import java.util.Date;

/**
 * @title: EstablishmentInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/6 0006 15:48
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class EstablishmentInfo {
    private int id;
    private String cardId;
    private String mainImage;
    private String company;
    private String abbreviation;
    private String industry;
    private String scope;
    private String address;
    private String companyTel;
    private String companyWeb;
    private String email;
    private String introduce;
    private String mainBusiness;
    private String mainDemand;
    private int leaguetype;
    private String licensepic;
    private String contactname;
    private String contactduty;
    private String contacttel;
    private String contactwx;
    private Date submittime;
    private Date updatetime;
    private String contactopenid;
}
