package com.qinzi123.service.impl;

import com.qinzi123.dao.MessageDao;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: MessageServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/31 0031 13:22
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class MessageServiceImpl extends AbstractWechatMiniProgramService implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Override
    public List<LinkedHashMap> getMessageList(Map map) {
        map.put("messageType", 1);
        return messageDao.getMessageList(map);
    }

    @Override
    public List<CardMessage> getCardMessageInfoById(Map map) {
        return messageDao.getCardMessageInfoById(map);
    }

    @Override
    public int getUnreadMessageCountByCard(Map map) {
        return messageDao.getUnreadMessageCountByCard(map);
    }

    @Override
    public int updateReadStatus(int messageId) {
        return messageDao.updateReadStatus(messageId);
    }
}
