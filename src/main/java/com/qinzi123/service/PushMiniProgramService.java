package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.WxSmallFormId;

import java.util.Map;

/**
 * @title: PushMiniProgramService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface PushMiniProgramService {

    boolean pushCard2AllUser(Map card);

    boolean pushMessage2OneUser(CardMessage cardMessage);

    boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply);

    int addFormId(WxSmallFormId wxSmallFormId);

    int batchAddFormId(int cardId, String[] formIdList);

    int updateFormId(WxSmallFormId wxSmallFormId);

}
