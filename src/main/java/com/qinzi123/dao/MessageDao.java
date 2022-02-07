package com.qinzi123.dao;

import com.qinzi123.dto.CardMessage;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: MessageDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/31 0031 13:23
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface MessageDao {

    List<LinkedHashMap> getMessageList(Map map);

    List<CardMessage> getCardMessageInfoById(Map map);

    int getUnreadMessageCountByCard(Map map);

    int updateReadStatus(@Param("messageId") int messageId);
}
