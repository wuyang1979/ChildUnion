package com.qinzi123.happiness.domain;

/**
 * @title: CampaignPcshowInfo
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CampaignPcshowInfo {
    private long id;

    private String content;

    private String imagepath;

    private int peopleNumber = 0;

    private int amendSignup = 0;

    private int agemin = 0;

    private int agemax = 0;

    private double cost = 0;

    private double childCost = 0;

    private String address = "";

    private String phone = "";

    private long datetime = 0;

    private long endDatetime = 0;

    private long signupStartTime = 0;

    private long signupEndTime = 0;

    private String organizationName;

    private String organizationPhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public int getAmendSignup() {
        return amendSignup;
    }

    public void setAmendSignup(int amendSignup) {
        this.amendSignup = amendSignup;
    }

    public int getAgemin() {
        return agemin;
    }

    public void setAgemin(int agemin) {
        this.agemin = agemin;
    }

    public int getAgemax() {
        return agemax;
    }

    public void setAgemax(int agemax) {
        this.agemax = agemax;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getChildCost() {
        return childCost;
    }

    public void setChildCost(double childCost) {
        this.childCost = childCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public long getSignupStartTime() {
        return signupStartTime;
    }

    public void setSignupStartTime(long signupStartTime) {
        this.signupStartTime = signupStartTime;
    }

    public long getSignupEndTime() {
        return signupEndTime;
    }

    public void setSignupEndTime(long signupEndTime) {
        this.signupEndTime = signupEndTime;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationPhone() {
        return organizationPhone;
    }

    public void setOrganizationPhone(String organizationPhone) {
        this.organizationPhone = organizationPhone;
    }

}
