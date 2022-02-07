package com.qinzi123.dto;

import java.util.Optional;

/**
 * @title: CardMessageReply
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CardMessageReply extends BaseParam {
    int id;
    int messageId;
    int replyId;
    int replyCard;

    String replyMessage;
    String createTime;
    String title;

    CardMessageReply replyInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getReplyId() {
        return replyId;
    }

    public int getReplyCard() {
        return replyCard;
    }

    public void setReplyCard(int replyCard) {
        this.replyCard = replyCard;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public CardMessageReply getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(CardMessageReply replyInfo) {
        this.replyInfo = replyInfo;
    }

    public String getTitle() {
        return Optional.ofNullable(title).orElse("有新回复消息");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("用户 %d 回复消息 %s", cardId, title);
    }
}
