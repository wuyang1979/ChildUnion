package com.qinzi123.dto;

import com.qinzi123.exception.GlobalProcessException;

/**
 * @title: ScoreType
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public enum ScoreType {
    Invite(100, 1), Message(0, 2), MessageReply(0, 4), Sign(10, 3), ShowCard(100, 5);
    //Invite(100, 1) //奖邀请, Message(40, 2)//发帖, MessageReply(20, 4)//回复, Sign(10, 3)//签到, ShowCard(50, 5)//查看名片;

    int score;
    int type;

    ScoreType(int score, int type) {
        this.score = score;
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public int getType() {
        return type;
    }

    public static ScoreType getScoreType(int type) {
        for (ScoreType scoreType : values()) {
            if (scoreType.getType() == type)
                return scoreType;
        }
        throw new GlobalProcessException(String.format("%d 类型不匹配", type));
    }
}
