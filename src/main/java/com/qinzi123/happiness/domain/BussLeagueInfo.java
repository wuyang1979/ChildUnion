package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @title: BussLeagueInfo
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BussLeagueInfo implements Serializable {
    private static final long serialVersionUID = 872914911354150894L;

    private long id;

    private int leagueType;

    private String companyDesc;

    private String company;

    private String logoPic;

    private String foundTime;

    private String members;

    private String companyAddr;

    private String companyTel;

    private String companyWeb;

    private String industry;

    private String mainBussiness;

    private String mainDemand;

    private String licensePic;

    private String contactName;

    private String contactDuty;

    private String contactTel;

    private String contactWX;

    private Timestamp submitTime;

    private Timestamp updateTime;

    private String contactOpenId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(int leagueType) {
        this.leagueType = leagueType;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMainBussiness() {
        return mainBussiness;
    }

    public void setMainBussiness(String mainBussiness) {
        this.mainBussiness = mainBussiness;
    }

    public String getMainDemand() {
        return mainDemand;
    }

    public void setMainDemand(String mainDemand) {
        this.mainDemand = mainDemand;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDuty() {
        return contactDuty;
    }

    public void setContactDuty(String contactDuty) {
        this.contactDuty = contactDuty;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactWX() {
        return contactWX;
    }

    public void setContactWX(String contactWX) {
        this.contactWX = contactWX;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getContactOpenId() {
        return contactOpenId;
    }

    public void setContactOpenId(String contactOpenId) {
        this.contactOpenId = contactOpenId;
    }

}
