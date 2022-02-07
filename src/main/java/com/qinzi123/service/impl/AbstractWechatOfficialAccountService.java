package com.qinzi123.service.impl;

import com.qinzi123.dto.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title: AbstractWechatOfficialAccountService
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class AbstractWechatOfficialAccountService extends AbstractWechatService {

    // 公众号发送模板消息 URL
    private static final String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    private Logger logger = LoggerFactory.getLogger(AbstractWechatOfficialAccountService.class);

    protected String getAppId() {
        return "wx6bd7410f6baee66b";
    }

    protected String getSecret() {
        return "4293807c738232d3c9dec27f01119674";
    }

    protected String getSendUrl() {
        return SEND_URL;
    }

    protected TokenType getTokenType() {
        return TokenType.OfficialAccount;
    }
}
