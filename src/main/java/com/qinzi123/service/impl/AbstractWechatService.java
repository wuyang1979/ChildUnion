package com.qinzi123.service.impl;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.CooperateDao;
import com.qinzi123.dao.IndexDao;
import com.qinzi123.dao.PushDao;
import com.qinzi123.dto.TokenType;
import com.qinzi123.dto.WxSmallToken;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: AbstractWechatService
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class AbstractWechatService {

    private Logger logger = LoggerFactory.getLogger(AbstractWechatService.class);

    // 请求token的URL
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    // 校验消息体的 URL
    protected static final String MSG_CHECK_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=%s";

    @Resource
    protected RestTemplate restTemplate;

    @Resource
    CardDao cardDao;

    @Resource
    IndexDao indexDao;

    @Resource
    CooperateDao cooperateDao;

    @Resource
    PushDao pushDao;

    @Resource
    TokenService tokenService;

    protected abstract String getAppId();

    protected abstract String getSecret();

    protected String getTokenUrl() {
        return String.format(TOKEN_URL, getAppId(), getSecret());
    }

    /**
     * 获取发送微信需要的token
     *
     * @return token结果
     */
    String getAccessToken() {
        String getUrl = getTokenUrl();
        Map map = getJsonFromWeixin(getUrl);
        String token = map.get("access_token") == null ? "" : map.get("access_token").toString();
        return token;
    }

    protected abstract TokenType getTokenType();

    String getToken() {
        WxSmallToken wxSmallToken = tokenService.getToken(getTokenType());
        if (wxSmallToken == null) {
            logger.info("表里没有token，需要取微信请求token");
            tokenService.addCurrentToken(getTokenType(), getAccessToken());
            wxSmallToken = tokenService.getToken(getTokenType());
        }
        return wxSmallToken.getToken();
    }

    /**
     * 从微信API接口调用获取返回的json数据
     *
     * @param url 发送到微信的URL
     * @return 返回MAP结构体
     */
    Map getJsonFromWeixin(String url) {
        logger.info(String.format("获取JSON 从url %s", url));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        Map map = restTemplate.getForObject(url, Map.class);
        logger.info(map.toString());
        return map;
    }

    /**
     * 推送Json数据到微信API接口
     *
     * @param url    发送到微信的URL
     * @param object 发送的结构体
     * @return 微信返回的Map结构体
     */
    Map sendTemplate(String url, Object object) {
        logger.info(String.format("发送对象 %s, 到 url %s ", object.toString(), url));
        Map result = restTemplate.postForObject(url, object, Map.class);
        logger.info(result.toString());
        return result;
    }

    protected abstract String getSendUrl();

    /**
     * 发送消息
     *
     * @param token
     * @param sendObject
     * @return
     */
    protected boolean push2OneUser(String token, Object sendObject) {
        logger.info(String.format("发送消息{ %s }", sendObject.toString()));
        Map result = sendTemplate(String.format(getSendUrl(), token), sendObject);
        logger.info(String.format("发送结果:%s", result.toString()));
        if (result.get("errcode") != null) {
            try {
                int errcode = Integer.parseInt(result.get("errcode").toString());
                if (errcode == 0) {
                    logger.info("发送成功");
                    return true;
                }
            } catch (Exception e) {
                // 发送一个失败, 不影响其他发送
                logger.error("异常消息", e);
            }
        }
        logger.info("发送失败");
        return false;
    }

    /**
     * 发送模板检查到微信
     *
     * @param token   微信需要的token
     * @param content 文本检查内容
     * @return 检查结果
     */
    void checkMsg(String token, String content) {
        Map map = sendTemplate(String.format(MSG_CHECK_URL, token),
                new HashMap<String, String>() {{
                    put("content", content);
                }}
        );
        logger.info("微信消息检测结果, {}", map.toString());
        if (map == null || map.size() == 0) throw new GlobalProcessException("微信消息检测失败");
        if (!"0".equalsIgnoreCase(map.get("errcode").toString()))
            //throw new GlobalProcessException(map.get("errMsg").toString());
            throw new GlobalProcessException("内容含有违法违规内容");


    }
}
