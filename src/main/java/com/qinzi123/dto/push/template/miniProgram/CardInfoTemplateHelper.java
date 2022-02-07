package com.qinzi123.dto.push.template.miniProgram;

import com.qinzi123.util.Utils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: CardInfoTemplateHelper
 * @package: com.qinzi123.dto.push.template.miniProgram
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class CardInfoTemplateHelper extends MiniProgramTemplateHelper {

    @Override
    String templateId() {
        return "ItOTSJYKqVjynN9sWJo_z1_F5XH1YXw2LaaqXAlpwgk";
    }

    @Override
    String page(Object object) {
        Map card = (Map) object;
        Map<String, Object> map = new HashMap() {{
            put("id", Integer.parseInt(card.get("id").toString()));
            put("isFollowed", 0);
        }};
        return Utils.fillUrlParams("pages/business/oneBusiness", map);
    }

    @Override
    String emphasis() {
        return null;
    }

    @Override
    Object data(Object object) {
        Map card = (Map) object;
        CardInfoTemplate cardInfoTemplate = new CardInfoTemplate();
        cardInfoTemplate.setKeyword1(getKeyword(card.get("realname").toString()));
        cardInfoTemplate.setKeyword2(getKeyword(card.get("job").toString()));
        cardInfoTemplate.setKeyword3(getKeyword(card.get("company").toString()));
        cardInfoTemplate.setKeyword4(getKeyword(card.get("workaddress").toString()));
        return cardInfoTemplate;
    }
}
