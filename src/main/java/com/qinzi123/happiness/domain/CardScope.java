package com.qinzi123.happiness.domain;

import java.io.Serializable;

/**
 * @title: CardScope
 * @package: com.qinzi123.happiness.domain
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CardScope implements Serializable {
    public static final String[] CARD_FIRST_SCOPE = {"亲子教育", "-1", "亲子活动", "亲子基地", "亲子其它", "亲子产品"};
    public static final String[][] CARD_SECOND_SCOPE = {{"其它", "早教(0-3岁)", "才艺", "运动", "英语", "益智", "学科", "父母课堂", "幼儿园", "小饭桌"},
            {"其它", "绘本馆", "图书馆"},
            {"其它", "亲子游", "社会实践", "科学亲子", "艺术人文", "公益活动", "演出展览"},
            {"其它", "景区", "农庄", "酒店", "度假村"}, {"其它"}, {"其它"}};

    private static final long serialVersionUID = 8766629686172397600L;

    private long cardId;

    private int firstScope;

    private int secondScope;


    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public int getFirstScope() {
        return firstScope;
    }

    public void setFirstScope(int firstScope) {
        this.firstScope = firstScope;
    }

    public int getSecondScope() {
        return secondScope;
    }

    public void setSecondScope(int secondScope) {
        this.secondScope = secondScope;
    }

    public String getFSDesc() {
        if (getFirstScope() >= CARD_FIRST_SCOPE.length || getFirstScope() < 0) {
            return CARD_FIRST_SCOPE[CARD_FIRST_SCOPE.length - 1];
        }
        return CARD_FIRST_SCOPE[getFirstScope()];
    }

    public String getSSDesc() {
        int first = getFirstScope();
        int second = getSecondScope();
        if (getFirstScope() >= CARD_SECOND_SCOPE.length || getFirstScope() < 0) {
            first = CARD_SECOND_SCOPE.length - 1;
        }
        if (getSecondScope() >= CARD_SECOND_SCOPE[first].length || getSecondScope() < 0) {
            second = CARD_SECOND_SCOPE[first].length - 1;
        }
        return CARD_SECOND_SCOPE[first][second];
    }

}
