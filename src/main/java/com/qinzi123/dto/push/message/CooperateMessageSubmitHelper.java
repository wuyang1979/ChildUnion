package com.qinzi123.dto.push.message;

import com.qinzi123.dao.CooperateDao;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.push.MessageTools;
import com.qinzi123.service.CooperateWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @title: CooperateMessageSubmitHelper
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class CooperateMessageSubmitHelper extends CooperateMessageHelper {

    @Autowired
    CooperateDao cooperateDao;

    @Override
    String templateId() {
        return "uwhiRcacULuqIySAIE8salPOKd3muX8GIAorF4_b8kk";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage) object);
    }

    @Override
    Object data(Object object) {
        CardMessage cardMessage = (CardMessage) object;
        CooperateMessage cooperateMessage = new CooperateMessage();
        cooperateMessage.setThing1(getKeyword(cardMessage.getTitle()));
        cooperateMessage.setThing2(getKeyword(cardMessage.getCardInfo().getRealname()));
        cooperateMessage.setThing4(getKeyword(cooperateDao.getCardMessageTypeNameById(cardMessage.getMessageType())));
        cooperateMessage.setTime3(getKeyword(cardMessage.getUpdateTime()));
        return cooperateMessage;
    }
}
