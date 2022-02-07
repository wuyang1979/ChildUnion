package com.qinzi123.happiness.domain;

import java.io.Serializable;

/**
 * @title: BussinessPcShowCampaigns
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BussinessPcShowCampaigns implements Serializable {

    private static final long serialVersionUID = -4406711049711660597L;

    private long id;

    private String title;

    private String imagePath;

    private String url;

    private long datetime;

    private long endDatetime;

    private int cost;

    private int childCost;

    private int agemin = 0;

    private int agemax = 0;

    private String address = "";

    private int peopleNumber = 0;

    private int amendSignup = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getChildCost() {
        return childCost;
    }

    public void setChildCost(int childCost) {
        this.childCost = childCost;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


}