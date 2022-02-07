package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @title: BussLeagueCooperate
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BussLeagueCooperate implements Serializable {
    private static final long serialVersionUID = 872914911354150894L;

    private long id;

    private long bussLeagueId;

    private Timestamp datetime;

    private String purposeOpenId;

    private int agreed = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBussLeagueId() {
        return bussLeagueId;
    }

    public void setBussLeagueId(long bussLeagueId) {
        this.bussLeagueId = bussLeagueId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getPurposeOpenId() {
        return purposeOpenId;
    }

    public void setPurposeOpenId(String purposeOpenId) {
        this.purposeOpenId = purposeOpenId;
    }

    public int getAgreed() {
        return agreed;
    }

    public void setAgreed(int agreed) {
        this.agreed = agreed;
    }

}
