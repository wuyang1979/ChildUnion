package com.qinzi123.dto.push.template.officialAccount;

import com.qinzi123.dto.push.template.OfficialAccountSendObject;
import com.qinzi123.dto.push.AbstractPushHelper;

/**
 * @title: OATemplateHelper
 * @package: com.qinzi123.dto.push.template.officialAccount
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class OATemplateHelper extends AbstractPushHelper {

    abstract String templateId();

    abstract String url();

    abstract Object data(Object object);

    protected First getFirst(String value) {
        return new First(value);
    }

    protected Remark getRemark(String value) {
        return new Remark(value);
    }

    public OfficialAccountSendObject generateSendObject(String toUser, Object showData) {
        OfficialAccountSendObject sendObject = new OfficialAccountSendObject();
        sendObject.setTouser(toUser);
        sendObject.setTemplate_id(templateId());
        sendObject.setUrl(url());
        sendObject.setData(data(showData));
        return sendObject;
    }
}
