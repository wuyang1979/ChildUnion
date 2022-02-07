package com.qinzi123.service.impl;

import com.fasterxml.jackson.core.util.InternCache;
import com.qinzi123.dao.CooperateDao;
import com.qinzi123.dto.CardInfo;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @title: ServiceHelper
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class ServiceHelper {

    /**
     * 微信支付的比率
     */
    private static final int MICRO_PERCENT = 100;
    @Autowired
    CooperateDao cooperateDao;


    private static int mapNullNumber(Map map, String key) {
        if (map.get(key) == null) return 0;
        return Integer.parseInt(map.get(key).toString());
    }

    public static CardMessage convertMap2CardMessage(Map map, CardInfo cardInfo, String message) {
        CardMessage cardMessage = new CardMessage();
        cardMessage.setId(Integer.parseInt(map.get("id").toString()));
        cardMessage.setCardId(Integer.parseInt(map.get("card_id").toString()));
        cardMessage.setCardInfo(cardInfo);
        cardMessage.setGiveLike(mapNullNumber(map, "give_like"));
        cardMessage.setReadCount(mapNullNumber(map, "read_count"));
        //设置通知消息标题
        //cardMessage.setTitle(message);
        cardMessage.setTitle(map.get("title").toString());
        cardMessage.setMessage(map.get("message").toString());
        cardMessage.setSourcePath(map.get("source_path").toString());
        cardMessage.setMessageType(Integer.parseInt(map.get("message_type").toString()));
        cardMessage.setSourceType(Integer.parseInt(map.get("source_type").toString()));
        cardMessage.setLast(Long.parseLong(map.get("last").toString()));

        return cardMessage;
    }

    public static CardMessageReply convertMap2CardMessageReply(Map map, String message) {
        CardMessageReply cardMessageReply = new CardMessageReply();
        cardMessageReply.setId(Integer.parseInt(map.get("id").toString()));
        cardMessageReply.setCardId(Integer.parseInt(map.get("card_id").toString()));
        cardMessageReply.setMessageId(Integer.parseInt(map.get("message_id").toString()));
        //设置通知消息标题
        //cardMessageReply.setTitle(message);
        cardMessageReply.setTitle(map.get("reply_message").toString());
        //cardMessageReply.setReplyId(Integer.parseInt(map.get("reply_id").toString()));
        // 固定通知作者
        cardMessageReply.setReplyId(0);
        cardMessageReply.setReplyMessage(map.get("reply_message").toString());

        return cardMessageReply;
    }

    /**
     * 构造微信支付数额
     *
     * @param fee 充值金额
     * @return
     */
    public static String convertFee(String fee) {
        BigDecimal bigDecimal = new BigDecimal(fee);
        BigDecimal result = bigDecimal.multiply(new BigDecimal(MICRO_PERCENT)).setScale(0);
        return String.valueOf(result.toString());
    }

    /**
     * 支付金额转换成积分
     *
     * @param payment
     * @param rate
     * @return
     */
    public static int scoreByRate(int payment, int rate) {
        int ratio = rate == 0 ? MICRO_PERCENT : rate;
        return (int) Math.ceil((payment * ratio * 1.0) / MICRO_PERCENT);
    }
}
