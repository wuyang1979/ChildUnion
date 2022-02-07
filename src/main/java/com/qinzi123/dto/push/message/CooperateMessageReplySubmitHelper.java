package com.qinzi123.dto.push.message;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.push.MessageTools;
import org.springframework.stereotype.Component;

/**
 * @title: CooperateMessageReplySubmitHelper
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class CooperateMessageReplySubmitHelper extends CooperateMessageHelper {
    @Override
    String templateId() {
        return "9mY0ssY4O1iSnG9c3hPID4BQxVBHA_GWWjBZ43-HEl4";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage) object);
    }

    @Override
    Object data(Object object) {
        CardMessageReply cardMessageReply = (CardMessageReply) object;
        CooperateReplyMessage cooperateReplyMessage = new CooperateReplyMessage();
        cooperateReplyMessage.setName2(getKeyword(cardMessageReply.getCardInfo().getRealname()));
        cooperateReplyMessage.setThing3(getKeyword(cardMessageReply.getReplyMessage()));
        cooperateReplyMessage.setDate4(getKeyword(cardMessageReply.getCreateTime()));
        return cooperateReplyMessage;
    }
}
