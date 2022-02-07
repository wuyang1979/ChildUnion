package com.qinzi123.service.impl;

import com.qinzi123.dto.*;
import com.qinzi123.service.*;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.aspectj.weaver.Lint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @title: CooperateWeixinServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
//@EnableAsync
public class CooperateWeixinServiceImpl extends AbstractWechatMiniProgramService implements CooperateWeixinService {

    @Autowired
    PushMiniProgramService pushService;

    @Autowired
    PushOfficialAccountService pushOfficialAccountService;

    @Autowired
    ScoreService scoreService;

    private Logger logger = LoggerFactory.getLogger(CooperateWeixinServiceImpl.class);

    /**
     * 更新时间字段
     *
     * @param last
     */
    private void refreshOneLast(BaseParam last) {
        last.setLastString(Utils.getDateLast(last.getLast()));
    }

    /**
     * 更新时间字段
     *
     * @param lastList
     */
    private void refreshLast(List<? extends BaseParam> lastList) {
        for (BaseParam last : lastList) refreshOneLast(last);
    }

    WxSmallFormId getOneWxSmallFormId(int cardId, String formId) {
        WxSmallFormId wxSmallFormId = new WxSmallFormId();
        wxSmallFormId.setCardId(cardId);
        wxSmallFormId.setIsUse(Use.NOUSE.getIsUse());
        wxSmallFormId.setFormId(formId);
        return wxSmallFormId;
    }

    List<WxSmallFormId> generateWxSmallFormId(BaseParam cardMessage) {
        List<WxSmallFormId> wxSmallFormIdList = new ArrayList<WxSmallFormId>() {{
            if (cardMessage.getFormIdList() != null)
                for (String fromId : cardMessage.getFormIdList()) {
                    add(getOneWxSmallFormId(cardMessage.getCardId(), fromId));
                }
            else
                add(getOneWxSmallFormId(cardMessage.getCardId(), cardMessage.getFormId()));
        }};
        return wxSmallFormIdList;

    }

    //@Async
    void pushMessage(CardMessageReply cardMessageReply) {
        logger.info("异步准备发送消息");
        pushService.pushMessageReply2OneUser(cardMessageReply);
        pushOfficialAccountService.pushMessageReply2OneUser(cardMessageReply);
    }

    //@Async
    void pushMessage(CardMessage cardMessage) {
        logger.info("异步准备发送消息");
        pushOfficialAccountService.pushMessage2OneUser(cardMessage);
    }

    private String fillMessage(String message) {
        logger.info("过滤非法字符");
        return message.replaceAll("=", "等于").replaceAll("\\?", " ").replaceAll("&", "和");
    }

    /**
     * 把消息发保存到数据库
     *
     * @param cardMessage
     * @return
     */
    @Override
    public int addMessage(CardMessage cardMessage) {
        checkMsg(Utils.getWxAccessToken(), cardMessage.getTitle() + cardMessage.getMessage());
        cardMessage.setMessage(fillMessage(cardMessage.getMessage()));
        cardMessage.setTitle(fillMessage(cardMessage.getTitle()));
        int result;
        if (0 == cardMessage.getSourceType()) {
            cardMessage.setSourcePath(cardMessage.getSourcePathList().size() > 0 ? cardMessage.getSourcePathList().get(0) : "");
            result = cooperateDao.addMessage(cardMessage);
            int cardMessageId = cooperateDao.getCardMessageId(cardMessage.getTitle());

            if (cardMessage.getSourcePathList().size() > 0) {
                cardMessage.setSourcePath(cardMessage.getSourcePathList().get(0));
                List<String> sourcePathList = cardMessage.getSourcePathList();
                sourcePathList.forEach(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("cardMessageId", cardMessageId);
                    map.put("url", item);
                    int rows = cooperateDao.addMessagePictures(map);
                });
            } else {
                cardMessage.setSourcePath("");
            }
        } else {
            result = cooperateDao.addMessage(cardMessage);
        }
        logger.info("插入消息数据 {} 成功", cardMessage.toString());
        scoreService.addScore(cardMessage.getCardId(), ScoreType.Message);
        pushMessage(cardMessage);
        return result;
    }

    /**
     * 阅读数
     *
     * @param id
     * @return
     */
    public int updateMessageRead(int id) {
        logger.info("增加消息 {} 阅读数", id);
        return cooperateDao.updateMessageRead(id);
    }

    /**
     * 点赞数
     *
     * @param id
     * @return
     */
    public int updateMessageLike(int id) {
        logger.info("增加消息 {} 喜欢数", id);
        return cooperateDao.updateMessageLike(id);
    }

    /**
     * 获取所有数据
     *
     * @param start
     * @param num
     * @return
     */
    public List<CardMessage> getAllCardMessage(int start, int num) {
        List<CardMessage> cardMessageList = cooperateDao.getAllCardMessage(start, num);
        refreshLast(cardMessageList);
        return cardMessageList;
    }

    /**
     * 获取某个用户的 发布消息
     *
     * @param cardId
     * @return
     */
    @Override
    public List<CardMessage> getCardMessageByCardId(int cardId, int start, int num) {
        List<CardMessage> cardMessageList = cooperateDao.getCardMessageByCardId(cardId, start, num);
        refreshLast(cardMessageList);
        return cardMessageList;
    }

    /**
     * 新增回复
     *
     * @param cardMessageReply
     * @return
     */
    public int addCardMessageReply(CardMessageReply cardMessageReply) {
        checkMsg(Utils.getWxAccessToken(), cardMessageReply.getReplyMessage());
        cardMessageReply.setReplyMessage(fillMessage(cardMessageReply.getReplyMessage()));
        int result = cooperateDao.addCardMessageReply(cardMessageReply);
        logger.info("回复消息 {} 成功", cardMessageReply.toString());
        scoreService.addScore(cardMessageReply.getCardId(), ScoreType.MessageReply);

        //新增回复消息
        Map<String, Object> messageParamsMap = new HashMap<>();
        //1:互动消息
        messageParamsMap.put("message_type", 1);
        //1:关注行为
        messageParamsMap.put("message_behavior", 2);
        messageParamsMap.put("send_person_card", cardMessageReply.getCardId());
        messageParamsMap.put("receive_person_card", cardMessageReply.getReplyCard());
        messageParamsMap.put("create_time", DateUtils.getAccurateDate());
        messageParamsMap.put("card_message_id", cardMessageReply.getMessageId());
        cooperateDao.addCardMessageReplyMessage(messageParamsMap);

        //推送消息
        pushMessage(cardMessageReply);
        return result;
    }

    /**
     * 返回当前消息的 回复数据
     *
     * @param messageId
     * @return
     */
    @Override
    public List<CardMessageReply> getAllCardMessageReplyByMessageId(int messageId) {
        List<CardMessageReply> cardMessageReplyList = cooperateDao.getAllCardMessageReplyByMessageId(messageId);
        Map<Integer, CardMessageReply> map = new HashMap<>();
        for (CardMessageReply cardMessageReply : cardMessageReplyList) {
            refreshOneLast(cardMessageReply);
            map.put(cardMessageReply.getId(), cardMessageReply);
            if (cardMessageReply.getReplyId() != 0)
                cardMessageReply.setReplyInfo(map.get(cardMessageReply.getReplyId()));
        }
        Collections.reverse(cardMessageReplyList);
        return cardMessageReplyList;
    }

    /**
     * 返回当前消息的 回复数据
     *
     * @param message
     * @return
     */
    public List<CardMessageReply> getActiveCooperateListByMessage(String message) {
        List<CardMessageReply> cardMessageReplyList = cooperateDao.getActiveCooperateListByMessage(message);
        Map<Integer, CardMessageReply> map = new HashMap<>();
        for (CardMessageReply cardMessageReply : cardMessageReplyList) {
            refreshOneLast(cardMessageReply);
            map.put(cardMessageReply.getId(), cardMessageReply);
            if (cardMessageReply.getReplyId() != 0)
                cardMessageReply.setReplyInfo(map.get(cardMessageReply.getReplyId()));
        }
        Collections.reverse(cardMessageReplyList);
        return cardMessageReplyList;
    }

    /**
     * 返回当前消息的 回复数据
     *
     * @param messageId
     * @return
     */
    public List<LinkedHashMap> getMessage(int messageId) {
        return cooperateDao.getMessage(messageId);
    }

    @Override
    public List<LinkedHashMap> pictureList(int messageId) {
        return cooperateDao.pictureList(messageId);
    }


    @Override
    public List<LinkedHashMap> getFollower(Map map) {
        return cooperateDao.getFollower(map);
    }

    @Override
    public int addOrUpdateFollowerAuthAcceptRecord(Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        List<LinkedHashMap> followerAuthList = cooperateDao.getFollowerAuthListByCard(card);
        int rows;
        if (followerAuthList.size() == 0) {
            Map paramsMap = new HashMap();
            paramsMap.put("card", card);
            paramsMap.put("type", 1);
            paramsMap.put("authStatus", 1);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            rows = cooperateDao.addAuthAcceptRecord(paramsMap);
        } else {
            rows = cooperateDao.updateFollowerAuthStatusByCard(card);
        }
        return rows;
    }

    @Override
    public int addOrUpdateCardMessageAuthAcceptRecord(Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        List<LinkedHashMap> cardMessageAuthList = cooperateDao.getCardMessageAuthListByCard(card);
        int rows;
        if (cardMessageAuthList.size() == 0) {
            Map paramsMap = new HashMap();
            paramsMap.put("card", card);
            paramsMap.put("type", 2);
            paramsMap.put("authStatus", 1);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            rows = cooperateDao.addAuthAcceptRecord(paramsMap);
        } else {
            rows = cooperateDao.updateCardMessageAuthStatusByCard(card);
        }
        return rows;
    }

    @Override
    public List<LinkedHashMap> getUnAuthRecordList(Map map) {
        return cooperateDao.getUnAuthRecordList(map);
    }

    @Override
    public Map<String, Object> getShowDataCount() {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("visitCount",cooperateDao.getVisitCount());
        resultMap.put("businessCount",cooperateDao.getBusinessCount());
        resultMap.put("establishmentCount",cooperateDao.getEstablishmentCount());
        return resultMap;
    }
}
