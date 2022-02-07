package com.qinzi123.dto.push.template.officialAccount;

import com.qinzi123.dto.CardMessageReply;
import org.springframework.stereotype.Component;

/**
 * @title: OACooperateMessageReplyTemplateHelper
 * @package: com.qinzi123.dto.push.template.officialAccount
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component(value = "OACooperateReply")
public class OACooperateMessageReplyTemplateHelper extends OACooperateMessageTemplateHelper {

    @Override
    Object data(Object object) {
        OACooperateTemplate cooperateTemplate = new OACooperateTemplate();
        CardMessageReply cardMessageReply = (CardMessageReply) object;
        cooperateTemplate.setFirst(getFirst(cardMessageReply.getTitle()));
        cooperateTemplate.setKeyword1(getKeyword(cardMessageReply.getReplyMessage()));
        cooperateTemplate.setKeyword2(getKeyword(STATUS));
        cooperateTemplate.setRemark(getRemark(""));
        return cooperateTemplate;
    }
}
