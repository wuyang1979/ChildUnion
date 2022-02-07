package com.qinzi123.happiness.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @title: BussinessMessage
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BussinessMessage implements Serializable {
    private static final long serialVersionUID = 872914911354150895L;

    private long id;

    private String title;

    private String content;

    private int readNum = 0;

    private int status = 0;

    private Timestamp createTime;

    private long bussinessId;

    private BussinessInfo bussinessInfo;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public BussinessInfo getBussinessInfo() {
        return bussinessInfo;
    }

    public void setBussinessInfo(BussinessInfo bussinessInfo) {
        this.bussinessInfo = bussinessInfo;
    }

}
