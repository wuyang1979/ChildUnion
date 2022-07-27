package com.qinzi123;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.service.CooperateWeixinService;
import com.qinzi123.service.PushMiniProgramService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @title: PushServiceTest
 * @projectName: trunk
 * @description: 亲子企服
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PushServiceTest {

    @Resource
    CardDao weixinDao;

    @Resource
    PushMiniProgramService pushService;

    @Resource
    CooperateWeixinService cooperateWeixinService;

    CardMessage getCardMessage() {
        List<CardMessage> cardMessageList = cooperateWeixinService.getAllCardMessage(0, 1);
        if (cardMessageList == null || cardMessageList.size() == 0) return null;
        return cardMessageList.get(0);
    }

    @Ignore
    @Test
    public void testPushOneMessage() {
        CardMessage cardMessage = getCardMessage();
        if (cardMessage != null) {
            Map map = weixinDao.getCardInfoById("479");
            String openid = map.get("openid").toString();
            boolean result = pushService.pushMessage2OneUser(cardMessage);
            //Assert.assertTrue(result);
        }

    }

    @Ignore
    @Test
    public void testPushReplyMessage() {
        CardMessageReply cardMessageReply = new CardMessageReply();
        cardMessageReply.setMessageId(22);
        cardMessageReply.setReplyId(32);
        cardMessageReply.setCardId(479);
        cardMessageReply.setId(33);
        pushService.pushMessageReply2OneUser(cardMessageReply);
    }

}
