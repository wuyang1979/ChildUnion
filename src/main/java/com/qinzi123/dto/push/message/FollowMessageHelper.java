package com.qinzi123.dto.push.message;

import com.qinzi123.dto.FollowMessage;
import com.qinzi123.dto.push.MessageTools;
import org.springframework.stereotype.Component;

/**
 * @title: FollowMessageHelper
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/4 0004 11:18
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class FollowMessageHelper extends CooperateMessageHelper{

    @Override
    String templateId() {
        return "01EMtrNhQzLgkppeVZf0PtmoOk812HevdmwKDZQqkUE";
    }

    @Override
    String page(Object object) {
        return MessageTools.followMessage2Page((FollowMessage) object);
    }

    @Override
    Object data(Object object) {
        FollowMessage followMessage = (FollowMessage) object;
        CooperateMessage cooperateMessage = new CooperateMessage();
        return cooperateMessage;
    }
}
