package com.qinzi123.service.impl;

import com.qinzi123.dto.ScoreType;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: ScoreServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class ScoreServiceImpl extends AbstractWechatMiniProgramService implements ScoreService {

    private Logger logger = LoggerFactory.getLogger(ScoreServiceImpl.class);
    private static final int PAY_TYPE = 4;

    private void addScoreHistory(int cardId, int type, int score) {
        int id = cardDao.addScoreHistory(new HashMap() {{
            put("card_id", cardId);
            put("score_type", type);
            put("score", score);
        }});
        logger.info("记录{} 用户 {} 积分历史", id, cardId);
    }

    public int addScore(int cardId, ScoreType scoreType) {
        addScoreHistory(cardId, scoreType.getType(), scoreType.getScore());
        int id = cardDao.addScore(new HashMap() {{
            put("id", cardId);
            put("score", scoreType.getScore());
        }});
        logger.info("用户 {} 积分类型 {}, 增加 {} 积分", cardId, scoreType.getType(), scoreType.getScore());
        return id;
    }

    public int payAddScore(int cardId, int score) {
        addScoreHistory(cardId, PAY_TYPE, score);
        int id = cardDao.addScore(new HashMap() {{
            put("id", cardId);
            put("score", score);
        }});
        logger.info("用户 {} 支付现金, 增加 {} 积分", cardId, score);
        return id;
    }

    @Override
    public int minusScore(int cardId, int score) {
        Map cardMap = cardDao.getCardInfoById(String.valueOf(cardId));
        if (cardMap == null || cardMap.size() == 0) throw new GlobalProcessException("用户不存在");
        logger.info("用户积分信息, {}", cardMap.toString());
        if (cardMap.get("score") == null) throw new GlobalProcessException("用户没有积分");
        Integer currentScore = Integer.parseInt(cardMap.get("score").toString());
        if (currentScore < score) throw new GlobalProcessException("积分不够兑换");
        int id = cardDao.minusScore(new HashMap() {{
            put("id", cardId);
            put("score", score);
        }});
        logger.info("用户 {} 减少 {} 积分", cardId, score);
        return id;
    }
}
