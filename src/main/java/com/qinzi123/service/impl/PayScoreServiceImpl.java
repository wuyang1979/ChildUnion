package com.qinzi123.service.impl;

import com.qinzi123.dao.CampaignDao;
import com.qinzi123.dto.ScoreType;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.PayScoreService;
import com.qinzi123.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: PayScoreServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class PayScoreServiceImpl extends AbstractWechatMiniProgramService implements PayScoreService {

    private Logger log = LoggerFactory.getLogger(PayScoreServiceImpl.class);

    private static final String message = "用户 {}, 订单 {}, 扣除积分{}, 兑换 {}";

    @Autowired
    CampaignDao campaignDao;

    @Autowired
    ScoreService scoreService;

    @Override
    @Transactional
    public Map payScore(Map map) {
        log.info("积分兑换开始" + map.toString());
        checkUser(map);
        int card = Integer.parseInt(map.get("card").toString());
        int payment = Integer.parseInt(map.get("payment").toString());
        String orderId = map.get("id").toString();
        Map scoreMap = new HashMap<>();
        scoreMap.put("orderId", orderId);
        scoreMap.put("orderNo", map.get("order").toString());
        scoreMap.put("payment", payment);
        scoreMap.put("message", map.get("body").toString());
        int pay = campaignDao.addPaymentScore(scoreMap);
        if (pay >= 0) {
            campaignDao.updateOrder(map);
            scoreService.minusScore(card, payment);
            log.info(message, card, payment, orderId, "成功");
        } else {
            log.info(message, card, payment, orderId, "失败");
            throw new GlobalProcessException("兑换失败");
        }
        return map;
    }

    @Override
    @Transactional
    public Map payShowCardScore(int card, int showCard) {
        log.info("只是支付查看权限, {} 要看 {}", card, showCard);
        scoreService.minusScore(card, ScoreType.ShowCard.getScore());
        log.info("支付成功, 记录兑付日志");
        Map scoreMap = new HashMap() {{
            put("card_id", card);
            put("show_card_id", showCard);
            put("score_type", ScoreType.ShowCard.getType());
            put("score", ScoreType.ShowCard.getScore());
        }};
        cardDao.addShowScoreHistory(scoreMap);
        return scoreMap;
    }
}
