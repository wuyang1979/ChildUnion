package com.qinzi123.dto.push.template.miniProgram;

import com.qinzi123.dto.CardMessageReply;
import org.springframework.stereotype.Component;

/**
 * @title: CooperateMessageReplyTemplateHelper
 * @package: com.qinzi123.dto.push.template.miniProgram
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class CooperateMessageReplyTemplateHelper extends CooperateMessageTemplateHelper {

    @Override
    Object data(Object object) {
        CardMessageReply cardMessageReply = (CardMessageReply) object;
        CooperateTemplate cooperateTemplate = new CooperateTemplate();
        //cooperateTemplate.setKeyword1(getKeyword("有新回复消息"));
        cooperateTemplate.setKeyword1(getKeyword(cardMessageReply.getTitle()));
        cooperateTemplate.setKeyword2(getKeyword(cardMessageReply.getReplyMessage()));
        cooperateTemplate.setKeyword3(getKeyword(cardMessageReply.getCardInfo().getRealname()));
        cooperateTemplate.setKeyword4(getKeyword(cardMessageReply.getCreateTime()));
        cooperateTemplate.setKeyword5(getKeyword(cardMessageReply.getCardInfo().getPhone()));
        return cooperateTemplate;
    }
}
