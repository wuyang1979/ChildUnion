package com.qinzi123.dao;

import com.qinzi123.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CooperateDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface CooperateDao {
    /**
     * 商户合作等功能
     **/
    int addMessage(CardMessage cardMessage);

    int getCardMessageId(@Param("title") String title);

    int addMessagePictures(Map<String, Object> map);

    List<CardMessage> getAllCardMessage(@Param("start") int start, @Param("num") int num);

    List<String> getFansUser2Push(@Param("followerId") int followerId);

    List<CardMessage> getCardMessageByCardId(@Param("cardId") int cardId, @Param("start") int start, @Param("num") int num);

    CardMessage getCardMessageById(@Param("messageId") int messageId);

    int addCardMessageReply(CardMessageReply cardMessageReply);

    List<CardMessageReply> getAllCardMessageReplyByMessageId(@Param("messageId") int messageId);

    List<CardMessageReply> getActiveCooperateListByMessage(@Param("message") String message);

    CardMessageReply getCardMessageReplyById(@Param("replyId") int replyId);

    int updateMessageRead(@Param("id") int id);

    int updateMessageLike(@Param("id") int id);

    List<LinkedHashMap> getMessage(@Param("messageId") int messageId);

    List<LinkedHashMap> pictureList(int messageId);

    List<LinkedHashMap> getFollowerAuthListByCard(@Param("card") int card);

    List<LinkedHashMap> getCardMessageAuthListByCard(@Param("card") int card);

    int addCardMessageReplyMessage(Map map);

    List<LinkedHashMap> getFollower(Map map);

    int addAuthAcceptRecord(Map map);

    int updateFollowerAuthStatusByCard(@Param("card") int card);

    int updateCardMessageAuthStatusByCard(@Param("card") int card);

    List<LinkedHashMap> getUnAuthRecordList(Map map);

    String getCardMessageTypeNameById(@Param("typeId") int typeId);

    int getVisitCount();

    int getBusinessCount();

    int getEstablishmentCount();
}
