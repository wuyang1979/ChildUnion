package com.qinzi123.service.impl;

import com.qinzi123.dto.BaseMessage;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.FollowMessage;
import com.qinzi123.dto.push.template.OfficialAccountSendObject;
import com.qinzi123.dto.push.template.officialAccount.OACooperateMessageReplyTemplateHelper;
import com.qinzi123.dto.push.template.officialAccount.OACooperateMessageTemplateHelper;
import com.qinzi123.service.PushOfficialAccountService;
import com.qinzi123.service.TokenService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @title: PushOfficialAccountServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class PushOfficialAccountServiceImpl extends AbstractWechatOfficialAccountService implements PushOfficialAccountService {

    private Logger logger = LoggerFactory.getLogger(PushOfficialAccountServiceImpl.class);

    // 暂时只发一个人的
    private String openid = "oT37RviPpsAf-eg2lEZ6YQobkuG0";

    private String yjOpenId = "otofq0LHoEZJ_loNGbZL2fu8dN4c";

    @Autowired
    TokenService tokenService;

    @Resource(name = "OACooperateMessage")
    OACooperateMessageTemplateHelper messageTemplateHelper;

    @Resource(name = "OACooperateReply")
    OACooperateMessageReplyTemplateHelper replyTemplateHelper;

    /**
     * 发送消息
     *
     * @param sendObject
     * @return
     */
    private boolean pushSendObject2OneUser(OfficialAccountSendObject sendObject) {
        return push2OneUser(Utils.getWxAccessToken(), sendObject);
    }

    public boolean pushMessage2OneUser(CardMessage cardMessage) {
        logger.info("发送活动消息到公众号的个人");
        pushSendObject2OneUser(messageTemplateHelper.generateSendObject(
                openid, cardMessage));
        return true;
    }

    public boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply) {
        logger.info("发送活动审批消息到公众号的个人");
        pushSendObject2OneUser(replyTemplateHelper.generateSendObject(
                openid, cardMessageReply));
        return true;
    }

    @Override
    public boolean pushBaseMessageToAdmin(BaseMessage baseMessage) {
        logger.info("发送活动基地咨询消息到管理员");
        pushSendObject2OneUser(replyTemplateHelper.generateSendObject(
                yjOpenId, baseMessage));
        return true;
    }
}
