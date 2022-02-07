package com.qinzi123.dto;

import java.util.List;

/**
 * @title: BaseParam
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class BaseParam {
    long last;
    String lastString;
    String formId;
    List<String> formIdList;

    int cardId;
    CardInfo cardInfo;

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public String getLastString() {
        return lastString;
    }

    public void setLastString(String lastString) {
        this.lastString = lastString;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<String> getFormIdList() {
        return formIdList;
    }

    public void setFormIdList(List<String> formIdList) {
        this.formIdList = formIdList;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
}
