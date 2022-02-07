package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: MessageService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/31 0031 13:22
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface MessageService {

    List<LinkedHashMap> getMessageList(Map map);

    List<CardMessage> getCardMessageInfoById(Map map);

    int updateReadStatus(int messageId);

    int getUnreadMessageCountByCard(Map map);
}
