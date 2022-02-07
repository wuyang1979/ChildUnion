package com.qinzi123.dto.push.template.miniProgram;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.push.MessageTools;
import org.springframework.stereotype.Component;

/**
 * @title: CooperateMessageTemplateHelper
 * @package: com.qinzi123.dto.push.template.miniProgram
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class CooperateMessageTemplateHelper extends MiniProgramTemplateHelper {
    @Override
    String templateId() {
        return "OwhU78cHxk0TpyOR3N4ciOr8ciZ41ewmSOIbktvLGfc";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage) object);
    }

    @Override
    String emphasis() {
        return "keyword1.DATA";
    }

    @Override
    Object data(Object object) {
        CardMessage cardMessage = (CardMessage) object;
        CooperateTemplate cooperateTemplate = new CooperateTemplate();
        //cooperateTemplate.setKeyword1(getKeyword(""));
        cooperateTemplate.setKeyword1(getKeyword(cardMessage.getTitle()));
        cooperateTemplate.setKeyword2(getKeyword(cardMessage.getMessage()));
        cooperateTemplate.setKeyword3(getKeyword(cardMessage.getCardInfo().getRealname()));
        cooperateTemplate.setKeyword4(getKeyword(cardMessage.getUpdateTime()));
        cooperateTemplate.setKeyword5(getKeyword(cardMessage.getCardInfo().getPhone()));
        return cooperateTemplate;
    }

}
