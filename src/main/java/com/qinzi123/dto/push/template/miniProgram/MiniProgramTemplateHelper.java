package com.qinzi123.dto.push.template.miniProgram;

import com.qinzi123.dto.push.template.MiniProgramSendObject;
import com.qinzi123.dto.push.AbstractPushHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @title: MiniProgramTemplateHelper
 * @package: com.qinzi123.dto.push.template.miniProgram
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class MiniProgramTemplateHelper extends AbstractPushHelper {

    abstract String templateId();

    abstract String page(Object object);

    abstract String emphasis();

    abstract Object data(Object object);

    public MiniProgramSendObject generateSendObject(String toUser, String formId,
                                                    Object pageData, Object showData) {
        MiniProgramSendObject sendObject = new MiniProgramSendObject();
        sendObject.setTouser(toUser);
        sendObject.setTemplate_id(templateId());
        if (!StringUtils.isEmpty(page(pageData))) sendObject.setPage(page(pageData));
        sendObject.setForm_id(formId);
        sendObject.setData(data(showData));
        if (!StringUtils.isEmpty(emphasis())) sendObject.setEmphasis_keyword(emphasis());
        return sendObject;
    }

}
