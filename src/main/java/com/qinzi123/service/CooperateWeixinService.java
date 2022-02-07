package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CooperateWeixinService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface CooperateWeixinService {

    int addMessage(CardMessage cardMessage);

    int updateMessageRead(int id);

    int updateMessageLike(int id);

    List<CardMessage> getAllCardMessage(int start, int num);

    List<CardMessage> getCardMessageByCardId(int cardId, int start, int num);

    int addCardMessageReply(CardMessageReply cardMessageReply);

    List<CardMessageReply> getAllCardMessageReplyByMessageId(int messageId);

    List<CardMessageReply> getActiveCooperateListByMessage(String message);

    List<LinkedHashMap> getMessage(int messageId);

    List<LinkedHashMap> pictureList(int messageId);

    List<LinkedHashMap> getFollower(Map map);

    int addOrUpdateFollowerAuthAcceptRecord(Map map);

    int addOrUpdateCardMessageAuthAcceptRecord(Map map);

    List<LinkedHashMap> getUnAuthRecordList(Map map);

    Map<String, Object> getShowDataCount();
}

