package com.qinzi123.dto.push.message;

import com.qinzi123.dto.push.AbstractPushHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @title: CooperateMessageHelper
 * @package: com.qinzi123.dto.push.message
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class CooperateMessageHelper extends AbstractPushHelper {

    abstract String templateId();

    abstract String page(Object object);

    abstract Object data(Object object);

    public MessageSendObject generateSendObject(String toUser, Object pageData, Object showData) {
        MessageSendObject sendObject = new MessageSendObject();
        sendObject.setTouser(toUser);
        sendObject.setTemplate_id(templateId());
        if (!StringUtils.isEmpty(page(pageData))) sendObject.setPage(page(pageData));
        sendObject.setData(data(showData));
        return sendObject;
    }

}
