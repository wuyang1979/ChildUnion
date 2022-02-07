package com.qinzi123.service.impl;

import com.qinzi123.dao.TokenDao;
import com.qinzi123.dto.TokenType;
import com.qinzi123.dto.WxSmallToken;
import com.qinzi123.service.TokenService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: TokenServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
@EnableScheduling
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenDao tokenDao;

    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final long WX_EXPIRE = 7200L;

    private Map<TokenType, WxSmallToken> currentToken = new HashMap<>();

    @Scheduled(cron = "0 0 */2 * * ?")
    void refreshToken() {
        //log.info("更新token");
        //current_token = getAccessToken();
    }

    /**
     * 保存新的token
     *
     * @param token
     * @return
     */
    public boolean addCurrentToken(TokenType tokenType, String token) {
        log.info("增加新的token");
        WxSmallToken wxSmallToken = new WxSmallToken();
        wxSmallToken.setToken(token);
        wxSmallToken.setType(tokenType.getType());
        tokenDao.addCurrentToken(wxSmallToken);
        currentToken.put(tokenType, getCurrentToken(tokenType));
        return true;
    }

    /**
     * 获取当前的token
     *
     * @return
     */
    private WxSmallToken getCurrentToken(TokenType tokenType) {
        synchronized (currentToken) {
            log.info("获取token");
            WxSmallToken wxSmallToken = currentToken.get(tokenType);
            if (wxSmallToken == null) {
                log.info("当前类型 {} 没有token", tokenType.getType());
                wxSmallToken = tokenDao.getCurrentToken(tokenType.getType());
                if (wxSmallToken == null) {
                    return null;
                }
            }
            log.info(wxSmallToken.toString());
            long last = Utils.dateLast(wxSmallToken.getCreateTime());
            if (last >= WX_EXPIRE) {
                log.info("token失效");
                return null;
            }
            return wxSmallToken;
        }
    }

    public WxSmallToken getToken(TokenType tokenType) {
        return getCurrentToken(tokenType);
    }

}
