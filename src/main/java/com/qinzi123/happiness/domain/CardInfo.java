package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @title: CardInfo
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CardInfo implements Serializable {
    private static final long serialVersionUID = 872914911354150894L;

    private long id;

    private long bussinessId = -1;

    private String phone = "";

    private String realName = "";

    private String headImgUrl = "";

    private String openId = "";

    private String workAddress;

    private String introduce;

    private int permission = 0;

    private String wxCard;

    private Timestamp datetime;

    private String job = "";

    private String company = "";

    private long readNum;

    private int leaguer = 0;

    public int getLeaguer() {
        return leaguer;
    }

    public void setLeaguer(int leaguer) {
        this.leaguer = leaguer;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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

    public String getWxCard() {
        return wxCard;
    }

    public void setWxCard(String wxCard) {
        this.wxCard = wxCard;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    private Set<CardScope> cardScope;

    public Set<CardScope> getCardScope() {
        return cardScope;
    }

    public void setCardScope(Set<CardScope> cardScope) {
        this.cardScope = cardScope;
    }

    public long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public long getReadNum() {
        return readNum;
    }

    public void setReadNum(long readNum) {
        this.readNum = readNum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CardInfo other = (CardInfo) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public boolean isMatch(String content) {
        if (phone != null && phone.contains(content)) {
            return true;
        }
        if (realName != null && realName.contains(content)) {
            return true;
        }
        if (workAddress != null && workAddress.contains(content)) {
            return true;
        }
        if (job != null && job.contains(content)) {
            return true;
        }
        if (company != null && company.contains(content)) {
            return true;
        }
        if (introduce != null && introduce.contains(content)) {
            return true;
        }
        return false;
    }

}