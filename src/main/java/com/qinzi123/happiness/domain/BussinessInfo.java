package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @title: BussinessInfo
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BussinessInfo implements Serializable {
    private static final long serialVersionUID = 872914911354150894L;

    private long id;

    private String bussinessName;

    private String password;

    private String nickname = "";

    private String jobnumber = "";

    private int sex = 0;

    private String province = "";

    private String city = "";

    private String country = "";

    private String headImgUrl = "";

    private int loginType = 0;

    private String phone = "";

    private String openId = "";

    private String alipay = "";

    private String wxId = "";

    private String bankAccount = "";

    private String bankUser = "";

    private String bankAddress = "";

    private String personName = "";

    private int permission = 0;

    private Timestamp datetime;

    public BussinessInfo copy() {
        BussinessInfo newInfo = new BussinessInfo();
        newInfo.setPassword(this.getPassword());
        newInfo.setId(this.getId());
        newInfo.setBussinessName(this.getBussinessName());
        newInfo.setBankAddress(this.getBankAddress());
        newInfo.setHeadImgUrl(this.getHeadImgUrl());
        newInfo.setNickname(this.getNickname());
        newInfo.setOpenId(this.getOpenId());
        newInfo.setBankAccount(this.getBankAccount());
        newInfo.setBankUser(this.getBankUser());
        newInfo.setPhone(this.getPhone());
        newInfo.setWxId(this.getWxId());
        return newInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

}
